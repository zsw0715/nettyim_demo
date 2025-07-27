package com.example.nettyim_demo.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("group_info")
public class GroupInfo {
    private Long gid;
    @TableField("group_name")
    private String groupName;
    private String creator;
    @TableField("create_time")
    private LocalDateTime createTime;
    
}
