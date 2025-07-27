package com.example.nettyim_demo.netty.server.session.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.example.nettyim_demo.netty.server.session.GroupSession;
import com.example.nettyim_demo.netty.server.session.SessionFactory;

import io.netty.channel.Channel;

public class GroupSessionMemoryImpl implements GroupSession {

    private static final Map<String, Set<String>> groupMembersMap = new ConcurrentHashMap<>();

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

}
