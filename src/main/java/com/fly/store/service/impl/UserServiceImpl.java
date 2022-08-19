package com.fly.store.service.impl;

import com.fly.store.commons.utils.MD5Utils;
import com.fly.store.commons.utils.UUIDUtils;
import com.fly.store.mapper.UserMapper;
import com.fly.store.pojo.User;
import com.fly.store.service.UserService;
import com.fly.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer addUser(User user) {
        String username = user.getUsername();
        User u = userMapper.selectByUsername(username);
        if (u != null){
            throw new UsernameDuplicatedException("用户名被占用");
        }
        String oldPwd = user.getPassword();
        String salt = UUIDUtils.getUUID().toUpperCase();
        String md5Password = MD5Utils.getMD5Password(oldPwd,salt);
        user.setPassword(md5Password);
        user.setSalt(salt);
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setCreatedTime(new Date());
        user.setModifiedTime(new Date());
        Integer ret = userMapper.insertUser(user);
        if (ret != 1){
            throw new InsertException("注册过程中出现异常");
        }
        return ret;
    }

    @Override
    public User queryByUsername(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null){
            throw new UserNotFoundException("用户不存在");
        }
        if (user.getIsDelete() == 1){ //1表示用户被删除
            throw new UserNotFoundException("用户不存在");
        }
        String oldPassword = user.getPassword();
        String salt = user.getSalt();
        String newPassword = MD5Utils.getMD5Password(password,salt);
        if (!oldPassword.equals(newPassword)){
            throw new PasswordNotMatchException("用户密码错误");
        }
        User ret = new User();
        ret.setUid(user.getUid());
        ret.setUsername(user.getUsername());
        ret.setAvatar(user.getAvatar());
        return ret;
    }

    @Override
    public void editPasswordByUid(Integer uid, String username, String oldPassword, String newPassword) {
        User user = userMapper.selectByUid(uid);
        if (user == null || user.getIsDelete() == 1){
            throw new UserNotFoundException("用户不存在");
        }
        String oldMd5Password = MD5Utils.getMD5Password(oldPassword,user.getSalt());
        if (!user.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("密码不正确");
        }
        String newMd5Password = MD5Utils.getMD5Password(newPassword,user.getSalt());
        Integer ret = userMapper.updatePasswordByUid(uid,newMd5Password,username,new Date());
        if (ret != 1){
            throw new UpdateException("更新发生异常");
        }
    }

    @Override
    public User queryByUid(Integer uid) {
        User user = userMapper.selectByUid(uid);
        if (user == null || user.getIsDelete() == 1){
            throw new UserNotFoundException("用户不存在");
        }
        User u = new User();
        u.setUsername(user.getUsername());
        u.setPhone(user.getPhone());
        u.setEmail(user.getEmail());
        u.setGender(user.getGender());
        return u;
    }

    @Override
    public void editInfo(User user) {
        User ret = userMapper.selectByUid(user.getUid());
        if (ret == null || ret.getIsDelete() == 1){
            throw new UserNotFoundException("用户不存在");
        }
        user.setModifiedUser(user.getUsername());
        user.setModifiedTime(new Date());
        Integer row = userMapper.updateInfoByUid(user);
        if (row != 1){
            throw new UpdateException("更新数据发生异常");
        }
    }

    @Override
    public void editAvatar(Integer uid, String avatar, String username) {
        User ret = userMapper.selectByUid(uid);
        if (ret == null || ret.getIsDelete().equals(1)){
            throw new UserNotFoundException("用户不存在");
        }
        Integer row = userMapper.updateAvatarByUid(uid,avatar,username,new Date());
        if (row != 1){
            throw new UpdateException("修改头像发生异常");
        }
    }
}
