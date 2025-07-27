package com.example.nettyim_demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.nettyim_demo.entity.GroupChatMessage;

@Mapper
public interface GroupChatMessageMapper extends BaseMapper<GroupChatMessage> {
    
    
}
