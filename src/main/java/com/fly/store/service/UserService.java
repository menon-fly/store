package com.fly.store.service;

import com.fly.store.pojo.User;

import java.util.Date;

public interface UserService {
    /**
     * 用户注册
     */
    Integer addUser(User user);

    /**
     * 用户登录查询
     * @param username
     * @return
     */
    User queryByUsername(String username,String password);

    /**
     * 修改密码
     * @param uid
     * @return
     */
    void editPasswordByUid(Integer uid, String username,
                           String oldPassword,String newPassword);

    /**
     * 查询用户信息
     * @param uid
     * @return
     */
    User queryByUid(Integer uid);

    /**
     * 修改资料
     * @param user
     */
    void editInfo(User user);

    /**
     * 修改头像
     * @param uid
     * @param avatar
     * @param username
     */
    void editAvatar(Integer uid,String avatar,String username);
}
