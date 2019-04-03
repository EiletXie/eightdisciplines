package com.suntak.eightdisciplines;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//spring4.0 之前是用 @ResponseBody + @Controller
@RestController
@RequestMapping("/hello")
public class Hello {

    @Autowired
    private  LimitConfig limitConfig;

    //@RequestMapping(value="/hello",method= RequestMethod.GET)
    @GetMapping("/hi")
    public String hello(){
        return "说明" + limitConfig.getDescription();
    }

    @GetMapping("/say/{id}")
    public String say(@PathVariable("id") Integer id){
        return "ID: " + id;
    }

    // required属性 设置是否必须传参
    @GetMapping("/say")
    public String sayParam(@RequestParam(value= "id" , required = false,defaultValue = "0") Integer myId){
        return "myId: " + myId;
    }
}
