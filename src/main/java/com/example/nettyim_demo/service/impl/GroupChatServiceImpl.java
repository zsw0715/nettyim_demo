package com.example.nettyim_demo.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nettyim_demo.entity.GroupInfo;
import com.example.nettyim_demo.mapper.GroupInfoMapper;
import com.example.nettyim_demo.service.GroupChatService;

@Service
public class GroupChatServiceImpl implements GroupChatService {

    @Autowired
    private GroupInfoMapper groupInfoMapper;

    @Override
    public boolean createGroup(String groupName, String creator) {
        if (groupName == null || groupName.isEmpty() || creator == null || creator.isEmpty()) {
            throw new IllegalArgumentException("群名称和创建者不能为空");
        }

        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setGroupName(groupName);
        groupInfo.setCreator(creator);
        groupInfo.setCreateTime(LocalDateTime.now());

        return groupInfoMapper.insert(groupInfo) > 0;
    }

    @Override
    public Long createGroupAndGetGid(String groupName, String creator) {
        if (groupName == null || groupName.isEmpty() || creator == null || creator.isEmpty()) {
            throw new IllegalArgumentException("群名称和创建者不能为空");
        }

        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setGroupName(groupName);
        groupInfo.setCreator(creator);
        groupInfo.setCreateTime(LocalDateTime.now());

        int result = groupInfoMapper.insert(groupInfo);
        if (result <= 0) {
            throw new RuntimeException("创建群聊失败");
        }
        
        return groupInfo.getGid();
    }

    
    
}
