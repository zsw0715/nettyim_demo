package com.example.nettyim_demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("group_chat_message")
public class GroupChatMessage {
    private Long gcid;
    @TableField("group_name")
    private String groupName;
    private String sender;
    @TableField("send_time")
    private LocalDateTime sendTime;
    private String content;
}
