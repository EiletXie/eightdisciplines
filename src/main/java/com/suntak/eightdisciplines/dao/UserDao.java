package com.suntak.eightdisciplines.dao;

import com.suntak.eightdisciplines.entity.User;

public interface UserDao {

    /**
     * 用户登录
     * @param user
     * @return
     */
    int loginUser(User user);

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    User getUserInfoByUsername(String username);
}