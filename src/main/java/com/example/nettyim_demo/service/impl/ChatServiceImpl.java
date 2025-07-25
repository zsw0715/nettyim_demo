package com.example.nettyim_demo.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nettyim_demo.entity.ChatMessage;
import com.example.nettyim_demo.mapper.ChatMessageMapper;
import com.example.nettyim_demo.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Override
    public boolean saveMessage(String sender, String receiver, String message) {
        if (sender == null || receiver == null || message == null || message.isEmpty()) {
            throw new IllegalArgumentException("发送者、接收者和消息内容不能为空");
        }
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(sender);
        chatMessage.setReceiver(receiver);
        chatMessage.setContent(message);
        chatMessage.setSendTime(LocalDateTime.now());
        int result = chatMessageMapper.insert(chatMessage);
        if (result <= 0) {
            throw new RuntimeException("保存聊天消息失败");
        }
        return true;
    }
    
}  
