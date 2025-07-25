package com.example.nettyim_demo.service;

public interface ChatService {

    /**
     * 发送聊天消息
     *
     * @param sender   发送者的用户名
     * @param receiver 接收者的用户名
     * @param message  聊天内容
     * 
     * @return 是否保存成功
     */
    boolean saveMessage(String sender, String receiver, String message);

}
