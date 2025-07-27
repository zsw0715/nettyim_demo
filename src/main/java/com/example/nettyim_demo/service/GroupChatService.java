package com.example.nettyim_demo.service;

import java.util.Set;

public interface GroupChatService {

    /**
     * 创建群聊, 短连接存到数据库
     * 
     * @param groupName 群名称
     * @return 是否创建成功
     */
    boolean createGroup(String groupName, String creator, Set<String> members);

    /**
     * 创建群聊并返回群ID
     * 
     * @param groupName 群名称
     * @param creator   创建者
     * @return 群ID
     */
    Long createGroupAndGetGid(String groupName, String creator, Set<String> members);

    /**
     * 保存群聊消息
     * 
     * @param sender   发送者
     * @param groupName 群名称
     * @param content   消息内容
     * @return 是否保存成功
     */
    boolean saveGroupMessage(String sender, String groupName, String content);

    /**
     * 添加成员到群聊
     * 
     * @param groupName 群名称
     * @param member    成员用户名
     * @return 是否添加成功
     */
    boolean addMemberToGroup(String groupName, String member);


}
