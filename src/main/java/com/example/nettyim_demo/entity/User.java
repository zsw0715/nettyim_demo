package com.example.nettyim_demo.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("user")
public class User {
    @TableId(value = "uid", type = IdType.AUTO)
    private Long uid;
    private String username;
    private String password;
    private int status;
    private LocalDateTime create_time;
    private LocalDateTime last_login_time;
}
