package com.suntak.eightdisciplines.db8d.service;

import com.suntak.eightdisciplines.entity.User;

public interface UserService {

     User loginUser(User user);

     User getUserInfoByUsername(String username);
}
