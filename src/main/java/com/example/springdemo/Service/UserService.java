package com.example.springdemo.Service;


import com.example.springdemo.entity.User;
import com.example.springdemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public boolean addSinger(User user) {
        return userMapper.addSinger(user);
    }


    public boolean deleteUserById(int id) {
        return userMapper.deleteById(id);
    }

    public boolean updateSinger(User user) {
        return userMapper.updateSinger(user);
    }
}
