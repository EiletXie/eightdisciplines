package com.suntak.eightdisciplines.service;

import com.suntak.eightdisciplines.entity.User;

public interface UserService {

     User loginUser(User user);

     User getUserInfoByUsername(String username);
}
