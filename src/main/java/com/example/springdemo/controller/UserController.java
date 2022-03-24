package com.example.springdemo.controller;

import com.example.springdemo.Service.UserService;
import com.example.springdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findAll")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/addSinger")
    public boolean addSinger() {
        User user = new User();
        user.setName("Playboi Carti");
        user.setType("Trap");
        return userService.addSinger(user);
    }

    @GetMapping("/deleteById")
    public boolean deleteUserById() {
        int id = 3;
        return userService.deleteUserById(id);
    }

    @GetMapping("/updateSinger")
    public boolean updateSinger(){
        User user= new User();
        user.setId(2);
        user.setType("God Song");
        return userService.updateSinger(user);
    }


}
