package com.example.nettyim_demo.netty.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRequestMessage extends Message {
    // private String sender; // 发送者
    private String receiver;   // 接收者
    private String content; // 消息内容
    // private String timestamp;

    @Override
    public int getMessageType() {
        return MessageType.CHAT_REQUEST_MESSAGE;
    }

}
