package com.example.nettyim_demo.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nettyim_demo.entity.User;
import com.example.nettyim_demo.mapper.UserMapper;
import com.example.nettyim_demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean register(String username, String password) {
        if (userMapper.findByUsername(username) != null) {
            return false;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setStatus(0);
        user.setCreate_time(LocalDateTime.now());
        return userMapper.insert(user) > 0;
    }

    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            user.setLast_login_time(LocalDateTime.now());
            user.setStatus(1); // 设置为在线状态
            userMapper.updateById(user);
            return user;
        }
        return null; 
    }

    @Override
    public boolean logout(String username) {
        User user = userMapper.findByUsername(username);
        if (user != null) {
            user.setStatus(0); // 设置为离线状态
            userMapper.updateById(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        return userMapper.updateById(user) > 0;
    }

    @Override
    public boolean deleteUser(Long uid) {
        return userMapper.deleteById(uid) > 0;
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public boolean deleteAllUsers() {
        return userMapper.deleteAll() > 0;
    }
    
}
