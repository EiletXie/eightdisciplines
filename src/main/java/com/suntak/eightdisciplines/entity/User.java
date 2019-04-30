package com.suntak.eightdisciplines.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class User {

    private String username; // 用户名
    private String password;
    private String userId;  // 用户编号
    private String email;
    private String empId; // 工号
    private String alternateName; // 姓名
    private String active; // 是否存活

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getAlternateName() {
        return alternateName;
    }

    public void setAlternateName(String alternateName) {
        this.alternateName = alternateName;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", empId='" + empId + '\'' +
                ", alternateName='" + alternateName + '\'' +
                ", active='" + active + '\'' +
                '}';
    }
}
