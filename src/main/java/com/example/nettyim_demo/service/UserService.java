package com.example.nettyim_demo.service;

import com.example.nettyim_demo.entity.User;

public interface UserService {

    /**
     * 注册新用户
     * 
     * @param username 用户名
     * @param password 密码
     * @return 是否注册成功
     */
    boolean register(String username, String password);

    /**
     * 用户登录
     * 
     * @param username 用户名
     * @param password 密码
     * @return 用户信息，如果登录成功则返回用户信息，否则返回null
     */
    User login(String username, String password);

    /**
     * 更新用户信息
     * 
     * @param user 用户信息
     * @return 是否更新成功
     */
    boolean updateUser(User user);

    /**
     * 删除用户
     * 
     * @param uid 用户ID
     * @return 是否删除成功
     */
    boolean deleteUser(Long uid);

    /**
     * 根据用户名查询用户信息
     * 
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);

}
