package com.suntak.eightdisciplines.controller;

import com.suntak.eightdisciplines.entity.CustomerComplaint;
import com.suntak.eightdisciplines.service.impl.CustomerComplaintService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//注入的时候一定要是Controller 不要是RestController 因为它是rest接口（json格式） 是解析不到html
@Controller
@RequestMapping("/test")
public class CustomerComplaintController {

    @Resource
    private CustomerComplaintService customerComplaintService;

    @GetMapping("/car")
    public String getComplaintsByCar(){
        List<CustomerComplaint> list = customerComplaintService.getComplaintsByCar("CAR.NO-11123172");
        System.out.println(list.get(1));
        return list.get(1).toString();
    }

    @GetMapping("/test")
    public String goHtml(){
        return "index";
    }
}
