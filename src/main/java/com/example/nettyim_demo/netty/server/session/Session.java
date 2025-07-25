package com.example.nettyim_demo.netty.server.session;

import java.nio.channels.Channel;

/**
 * 接口 Session 定义了会话的基本操作
 * 用于管理用户会话信息，包括绑定、解绑、获取用户名、获取通道等操作。
 * 该接口的实现类可以用于存储和管理用户会话数据
 * 例如在内存中或使用数据库等持久化存储。
 */
public interface Session {

    /**
     * 绑定用户会话到指定的通道
     *
     * @param channel   通道
     * @param username  用户名
     */
    void bind(Channel channel, String username);

    /**
     * 解绑用户会话
     *
     * @param channel   通道
     */
    void unbind(Channel channel);

    /**
     * 获取用户会话的用户名
     *
     * @param channel   通道
     * @return 用户名
     */
    String getUsername(Channel channel);

    /**
     * 获取指定用户名的通道
     *
     * @param username  用户名
     * @return 通道
     */
    Channel getChannel(String username);

    /**
     * 设置会话属性
     *
     * @param channel   通道
     * @param key       属性名
     * @param value     属性值
     */
    void setAttribute(Channel channel, String key, Object value);

    /**
     * 获取会话属性
     *
     * @param channel   通道
     * @param key       属性名
     * @return          属性值
     */
    Object getAttribute(Channel channel, String key);

    /**
     * 检查用户是否已登录
     *
     * @param channel   通道
     * @return          是否已登录
     */
    boolean isLogin(Channel channel);

}
