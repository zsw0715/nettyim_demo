package com.example.nettyim_demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.nettyim_demo.entity.GroupInfo;

@Mapper
public interface GroupInfoMapper extends BaseMapper<GroupInfo> {

    /**
     * 根据群组名称查询群组信息
     *
     * @param groupName 群组名称
     * @return 群组信息
     */
    @Select("SELECT * FROM group_info WHERE group_name = #{groupName}")
    GroupInfo findByGroupName(String groupName);

}
