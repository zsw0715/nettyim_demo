package com.example.nettyim_demo.netty.server.session;

import com.example.nettyim_demo.netty.server.session.impl.SessionMemoryImpl;

/**
 * SessionFactory 用于创建和获取 Session 实例
 * 该工厂类可以用于获取单例的 Session 实现类，
 * 以便在整个应用程序中共享用户会话信息。
 * 
 * 后期拓展: 使用 Redis 等分布式缓存来存储会话信息，
 */
public class SessionFactory {
    private static final Session session = new SessionMemoryImpl();

    /**
     * 获取单例的 Session 实例
     *
     * @return Session 实例
     */
    public static Session getSession() {
        return session;
    }

}
