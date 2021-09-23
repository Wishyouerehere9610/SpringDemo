package com.example.springdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ziming Dong
 * @version 1.0
 * @date 2021/9/18
 */

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String sayHi(){
        return "Hello ZedDee";
    }
}
