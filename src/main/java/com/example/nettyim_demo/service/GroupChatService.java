package com.example.nettyim_demo.service;

public interface GroupChatService {

    /**
     * 创建群聊, 短连接存到数据库
     * 
     * @param groupName 群名称
     * @return 是否创建成功
     */
    boolean createGroup(String groupName, String creator);

    /**
     * 创建群聊并返回群ID
     * 
     * @param groupName 群名称
     * @param creator   创建者
     * @return 群ID
     */
    Long createGroupAndGetGid(String groupName, String creator);


}
