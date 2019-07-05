package com.suntak.eightdisciplines.controller;

import com.suntak.eightdisciplines.entity.CustomerComplaint;
import com.suntak.eightdisciplines.entity.User;
import com.suntak.eightdisciplines.db8d.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;

@Controller
public class UserController {

    @Resource
    UserService userService;


    @PostMapping("/loginUser")
    public String loginUser(@RequestParam("username") String username, @RequestParam("password") String password, ModelMap map, HttpSession session) {
        User user = new User();
        user.setUsername(username);
        String pwd = encodeInMD5(password);
        user.setPassword(pwd);
        System.out.println("User :" + user.getUsername() + " pwd  " + user.getPassword());
        User u = userService.loginUser(user);
        map.addAttribute("customerComplaint", new CustomerComplaint());
        if (u != null) {
            session.setAttribute("user", u);
            session.setAttribute("loginFlag", true);
            session.setMaxInactiveInterval(30 * 60);
            return "complaintChange";
        } else {
            map.addAttribute("user", user);
            session.setAttribute("loginFlag", false);
            return "index";
        }
    }

    @GetMapping("/userInfo")
    public String getUserInfo(@RequestParam("user_name") String username, Model model) {
        User u = userService.getUserInfoByUsername(username);
        if (u != null) {
            model.addAttribute("userFlag", true);
            model.addAttribute("user", u);
            return "complaintChange";
        } else {
            model.addAttribute("userFlag", false);
            return "index";
        }
    }

    @GetMapping("/")
    public String getUserInfo(ModelMap map) {
        // thymeleaf模板引擎当要进入一个表单提交对象的页面时，必须先提供一个对象给页面
        User user = new User();
        map.addAttribute("user", user);
        return "index";
    }

    // 密码 MD5加密
    public static String encodeInMD5(String text) {
        final StringBuffer buffer = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());

            byte[] digest = md.digest();
            for (int i = 0; i < digest.length; ++i) {
                final byte b = digest[i];
                final int value = (b & 0x7F) + (b < 0 ? 128 : 0);
                buffer.append(value < 16 ? "0" : "");
                buffer.append(Integer.toHexString(value));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
