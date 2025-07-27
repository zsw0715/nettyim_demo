package com.example.nettyim_demo.netty.server.session.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.nettyim_demo.entity.GroupInfo;
import com.example.nettyim_demo.entity.GroupMember;
import com.example.nettyim_demo.mapper.GroupInfoMapper;
import com.example.nettyim_demo.mapper.GroupMemberMapper;
import com.example.nettyim_demo.netty.server.session.GroupSession;
import com.example.nettyim_demo.netty.server.session.SessionFactory;

import io.netty.channel.Channel;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * 群聊会话实现类，使用内存存储群聊信息，后来改为了让SpringBoot自动注入，在demo2中需要把session从工厂类也改为SpringBoot自动注入，并结合redis实现分布式会话和缓存
 */
@Slf4j
@Component
public class GroupSessionMemoryImpl implements GroupSession {

    private static final Map<String, Set<String>> groupMembersMap = new ConcurrentHashMap<>();

    @Autowired
    private GroupInfoMapper groupInfoMapper;

    @Autowired
    private GroupMemberMapper groupMemberMapper;

    /**
     * 服务器启动时自动执行，把数据库里的 group -> member 加载进来
     */
    @PostConstruct
    public void init() {
        List<GroupInfo> groups = groupInfoMapper.selectList(null);
        for (GroupInfo group : groups) {
            String groupName = group.getGroupName();
            List<GroupMember> members = groupMemberMapper.selectByGroupName(groupName);
            Set<String> usernames = members.stream()
                    .map(GroupMember::getUsername)
                    .collect(Collectors.toSet());
            groupMembersMap.put(groupName, usernames);
        }
        log.debug("群组成员初始化完成，共加载：{} 个群组", groupMembersMap.size());
    }

    @Override
    public boolean createGroup(String groupName, Set<String> members) {
        if (groupName == null || groupName.isEmpty() || members == null || members.isEmpty()) {
            throw new IllegalArgumentException("Group name and members must not be null or empty");
        }
        if (groupMembersMap.containsKey(groupName)) {
            return false; // 群已存在，不能重复建群
        }
        groupMembersMap.put(groupName, members);
        return true;
    }

    @Override
    public Set<String> getMembers(String groupName) {
        if (groupName == null || groupName.isEmpty()) {
            throw new IllegalArgumentException("Group name must not be null or empty");
        }
        return groupMembersMap.getOrDefault(groupName, Set.of());
    }

    @Override
    public Set<Channel> getMemberChannels(String groupName) {
        Set<String> members = getMembers(groupName);
        Set<Channel> channels = ConcurrentHashMap.newKeySet();
        for (String username : members) {
            Channel channel = SessionFactory.getSession().getChannel(username);
            if (channel != null) {
                channels.add(channel);
            }
        }
        return channels;
    }

    @Override
    public boolean joinGroup(String groupName, String username) {
        Set<String> members = groupMembersMap.get(groupName);
        if (members == null)
            return false;
        return members.add(username);
    }

    @Override
    public boolean leaveGroup(String groupName, String username) {
        Set<String> members = groupMembersMap.get(groupName);
        if (members == null)
            return false;
        return members.remove(username);
    }

    @Override
    public boolean destroyGroup(String groupName) {
        return groupMembersMap.remove(groupName) != null;
    }

    @Override
    public Map<String, Set<String>> getAllGroupNamesWithGroupMembers() {
        return Map.copyOf(groupMembersMap);
    }

    @Override
    public Map<String, Set<String>> getAllGroupNamesWithGroupMembersLimitedByUserName(String username) {
        Map<String, Set<String>> result = new HashMap<>();
        for (Map.Entry<String, Set<String>> entry : groupMembersMap.entrySet()) {
            String groupName = entry.getKey();
            Set<String> members = entry.getValue();
            if (members.contains(username)) {
                result.put(groupName, members);
            }
        }
        return result;
    }

}
