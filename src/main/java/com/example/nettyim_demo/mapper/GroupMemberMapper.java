package com.example.nettyim_demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.nettyim_demo.entity.GroupMember;

@Mapper
public interface GroupMemberMapper extends BaseMapper<GroupMember> {
    
}
