package com.example.springdemo.mapper;

import com.example.springdemo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAll();

    boolean addSinger(User user);

    boolean deleteById(int id);

    boolean updateSinger(User user);
}
