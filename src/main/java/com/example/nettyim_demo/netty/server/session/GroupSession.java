package com.example.nettyim_demo.netty.server.session;

import java.util.Map;
import java.util.Set;

import io.netty.channel.Channel;

public interface GroupSession {

    /**
     * 创建群聊
     *
     * @param groupName 群名称
     * @param members   群成员集合, 包括创建者，使用集合就是因为去重，creator也被包括在内
     * @return 是否创建成功
     */
    boolean createGroup(String groupName, Set<String> members);

    /**
     * 获取群成员
     *
     * @param groupName 群名称
     * @return 群成员集合
     */
    Set<String> getMembers(String groupName);

    /**
     * 获取群成员的通道
     *
     * @param groupName 群名称
     * @return 通道集合
     */
    Set<Channel> getMemberChannels(String groupName);

    /**
     * 加入群聊
     *
     * @param groupName 群名称
     * @param username  用户名
     * @return 是否加入成功
     */
    boolean joinGroup(String groupName, String username);

    /**
     * 退出群聊
     *
     * @param groupName 群名称
     * @param username  用户名
     * @return 是否退出成功
     */
    boolean leaveGroup(String groupName, String username);

    /**
     * 解散群聊
     *
     * @param groupName 群名称
     * @return 是否解散成功
     */
    boolean destroyGroup(String groupName);

    /**
     * 获取所有群聊名称及其成员 -- 这个是管理员获取所有群聊的，普通用户无法获取，所以 demo1 没用
     *
     * @return 群聊名称及其成员集合
     */
    Map<String, Set<String>> getAllGroupNamesWithGroupMembers();

    /**
     * 获取当前用户所在的所有群聊名称及其成员
     *
     * @param username 用户名
     * @return 群聊名称及其成员集合
     */
    Map<String, Set<String>> getAllGroupNamesWithGroupMembersLimitedByUserName(String username);



}