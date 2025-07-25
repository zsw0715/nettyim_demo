package com.example.nettyim_demo.netty.server.session.impl;

import io.netty.channel.Channel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.example.nettyim_demo.netty.server.session.Session;

public class SessionMemoryImpl implements Session {

    private static final Map<Channel, String> channelUsernameMap = new ConcurrentHashMap<>();
    private static final Map<String, Channel> usernameChannelMap = new ConcurrentHashMap<>();
    private static final Map<Channel, Map<String, Object>> channelAttributesMap = new ConcurrentHashMap<>();

    @Override
    public void bind(Channel channel, String username) {
        if (channel == null || username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Channel and username must not be null or empty");
        }
        channelUsernameMap.put(channel, username);
        usernameChannelMap.put(username, channel);
        channelAttributesMap.put(channel, new ConcurrentHashMap<>());
    }

    @Override
    public void unbind(Channel channel) {
        if (channel == null) {
            throw new IllegalArgumentException("Channel must not be null");
        }
        String username = channelUsernameMap.remove(channel);
        if (username != null) {
            usernameChannelMap.remove(username);
        }
        channelAttributesMap.remove(channel);
    }


    @Override
    public String getUsername(Channel channel) {
        if (channel == null) {
            throw new IllegalArgumentException("Channel must not be null");
        }
        return channelUsernameMap.get(channel);
    }

    @Override
    public Channel getChannel(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username must not be null or empty");
        }
        return usernameChannelMap.get(username);
    }

    @Override
    public void setAttribute(Channel channel, String key, Object value) {
        if (channel == null || key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Channel and key must not be null or empty");
        }
        channelAttributesMap.computeIfAbsent(channel, k -> new ConcurrentHashMap<>()).put(key, value);
    }

    @Override
    public Object getAttribute(Channel channel, String key) {
        if (channel == null || key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Channel and key must not be null or empty");
        }
        return channelAttributesMap.getOrDefault(channel, new ConcurrentHashMap<>()).get(key);
    }

    @Override
    public boolean isLogin(Channel channel) {
        if (channel == null) {
            throw new IllegalArgumentException("Channel must not be null");
        }
        return channelUsernameMap.containsKey(channel);
    }
    
}
