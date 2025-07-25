package com.example.nettyim_demo.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("chat_message")
public class ChatMessage {
    @TableId(type = IdType.AUTO)
    private Long cid;

    @TableField("sender")
    private String sender;  // 发送者名字 是唯一键

    @TableField("receiver")
    private String receiver;  // 接收者名字

    private String content;

    @TableField("send_time")
    private LocalDateTime sendTime;
}

