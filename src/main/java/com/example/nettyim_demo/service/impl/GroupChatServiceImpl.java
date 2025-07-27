package com.example.nettyim_demo.service.impl;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nettyim_demo.entity.GroupChatMessage;
import com.example.nettyim_demo.entity.GroupInfo;
import com.example.nettyim_demo.entity.GroupMember;
import com.example.nettyim_demo.mapper.GroupChatMessageMapper;
import com.example.nettyim_demo.mapper.GroupInfoMapper;
import com.example.nettyim_demo.mapper.GroupMemberMapper;
import com.example.nettyim_demo.service.GroupChatService;

@Service
public class GroupChatServiceImpl implements GroupChatService {

    @Autowired
    private GroupInfoMapper groupInfoMapper;
    @Autowired
    private GroupMemberMapper groupMemberMapper;
    @Autowired
    private GroupChatMessageMapper groupChatMessageMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createGroup(String groupName, String creator, Set<String> members) {
        if (groupName == null || groupName.isEmpty() || creator == null || creator.isEmpty()) {
            throw new IllegalArgumentException("群名称和创建者不能为空");
        }

        LocalDateTime now = LocalDateTime.now();

        // 添加 到 group_info 表
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setGroupName(groupName);
        groupInfo.setCreator(creator);
        groupInfo.setCreateTime(now);

        boolean groupCreated = groupInfoMapper.insert(groupInfo) > 0;

        if (!groupCreated) return false;

        // 添加到 group_member 表
        boolean allMembersAdded = true;
        for (String member : members) {
            GroupMember groupMember = new GroupMember();
            groupMember.setGroupName(groupName);
            groupMember.setUsername(member);
            groupMember.setJoinTime(now);
            int insertResult = groupMemberMapper.insert(groupMember);
            if (insertResult <= 0) {
                allMembersAdded = false;
            }
        }

        return groupCreated && allMembersAdded;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createGroupAndGetGid(String groupName, String creator, Set<String> members) {
        if (groupName == null || groupName.isEmpty() || creator == null || creator.isEmpty()) {
            throw new IllegalArgumentException("群名称和创建者不能为空");
        }

        // 添加到 group_info 表
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setGroupName(groupName);
        groupInfo.setCreator(creator);
        groupInfo.setCreateTime(LocalDateTime.now());

        int result = groupInfoMapper.insert(groupInfo);
        if (result <= 0) {
            throw new RuntimeException("创建群聊失败");
        }

        // 添加到 group_member 表
        for (String member : members) {
            GroupMember groupMember = new GroupMember();
            groupMember.setGroupName(groupName);
            groupMember.setUsername(member);
            groupMember.setJoinTime(LocalDateTime.now());
            int insertResult = groupMemberMapper.insert(groupMember);
            if (insertResult <= 0) {
                throw new RuntimeException("添加群成员失败: " + member);
            }
        }

        return groupInfo.getGid();
    }

    @Override
    public boolean saveGroupMessage(String sender, String groupName, String content) {
        if (sender == null || sender.isEmpty() || groupName == null || groupName.isEmpty() || content == null || content.isEmpty()) {
            throw new IllegalArgumentException("发送者、群名称和消息内容不能为空");
        }

        GroupChatMessage groupChatMessage = new GroupChatMessage();
        groupChatMessage.setSender(sender);
        groupChatMessage.setGroupName(groupName);
        groupChatMessage.setContent(content);
        groupChatMessage.setSendTime(LocalDateTime.now());

        return groupChatMessageMapper.insert(groupChatMessage) > 0;
    }

    @Override
    public boolean addMemberToGroup(String groupName, String member) {
        if (groupName == null || groupName.isEmpty() || member == null || member.isEmpty()) {
            throw new IllegalArgumentException("群名称和成员不能为空");
        }

        // 检查群是否存在
        GroupInfo groupInfo = groupInfoMapper.findByGroupName(groupName);
        if (groupInfo == null) {
            return false; // 群不存在
        }

        // 添加成员到 group_member 表
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupName(groupName);
        groupMember.setUsername(member);
        groupMember.setJoinTime(LocalDateTime.now());

        return groupMemberMapper.insert(groupMember) > 0;
    }

    
    
}
