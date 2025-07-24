package com.example.nettyim_demo.mapper;

import com.example.nettyim_demo.entity.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    
    /**
     * 根据用户名查询用户信息
     * 
     * @param username 用户名
     * @return 用户信息
     */
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);
}
