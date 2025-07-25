package com.example.nettyim_demo.netty.server.session.impl;

import java.nio.channels.Channel;

import com.example.nettyim_demo.netty.server.session.Session;

public class SessionMemoryImpl implements Session {

    @Override
    public void bind(Channel channel, String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'bind'");
    }

    @Override
    public void unbind(Channel channel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unbind'");
    }

    @Override
    public String getUsername(Channel channel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
    }

    @Override
    public Channel getChannel(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getChannel'");
    }

    @Override
    public void setAttribute(Channel channel, String key, Object value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setAttribute'");
    }

    @Override
    public Object getAttribute(Channel channel, String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAttribute'");
    }

    @Override
    public boolean isLogin(Channel channel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isLogin'");
    }
    
}
