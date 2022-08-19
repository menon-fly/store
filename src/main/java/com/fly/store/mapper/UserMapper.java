package com.fly.store.mapper;

import com.fly.store.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface UserMapper {

    /**
     * 插入用户信息
     * @param user
     * @return
     */
    Integer insertUser(User user);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    User selectByUsername(String username);

    /**
     * 根据用户id修改密码
     * @param uid
     * @return
     */
    Integer updatePasswordByUid(Integer uid, String password,
                                String modifiedUser, Date modifiedTime);

    /**
     * 根据用户id查询信息
     * @param uid
     * @return
     */
    User selectByUid(Integer uid);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    Integer updateInfoByUid(User user);

    /**
     * 根据uid修改头像
     * @param uid
     * @param avatar
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateAvatarByUid(@Param("uid") Integer uid,
                              @Param("avatar") String avatar,
                              @Param("modifiedUser") String modifiedUser,
                              @Param("modifiedTime") Date modifiedTime);
}
