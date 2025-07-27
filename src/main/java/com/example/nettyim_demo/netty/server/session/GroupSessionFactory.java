package com.example.nettyim_demo.netty.server.session;

import com.example.nettyim_demo.netty.server.session.impl.GroupSessionMemoryImpl;

public class GroupSessionFactory {

    private static final GroupSession groupSession = new GroupSessionMemoryImpl();

    public static GroupSession getGroupSession() {
        return groupSession;
    }

}
