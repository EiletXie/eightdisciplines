package com.suntak.eightdisciplines.db8d.dao;

import com.suntak.eightdisciplines.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;

@Qualifier("db8dSqlSessionTemplate")
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