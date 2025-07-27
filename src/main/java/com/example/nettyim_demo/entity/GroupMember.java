package com.example.nettyim_demo.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("group_member")
public class GroupMember {
    private Long gmid;
    @TableField("group_name")
    private String groupName;
    private String username;
    @TableField("join_time")
    private LocalDateTime joinTime;
    
}
