package com.suntak.eightdisciplines.entity;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
public class User {

    private String username; // 用户名
    private String password;
    private String userId;  // 用户编号
    private String email;
    private String empId; // 工号
    private String alternateName; // 姓名
    private String active; // 是否存活
    private String role; // 角色

}
