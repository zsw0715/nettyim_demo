package com.example.nettyim_demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.nettyim_demo.entity.GroupMember;

@Mapper
public interface GroupMemberMapper extends BaseMapper<GroupMember> {

    @Select("SELECT * FROM group_member WHERE group_name = #{groupName}")
    List<GroupMember> selectByGroupName(String groupName);

    @Delete("DELETE FROM group_member WHERE group_name = #{groupName} AND username = #{username}")
    int deleteByGroupNameAndUsername(String groupName, String username);

}
