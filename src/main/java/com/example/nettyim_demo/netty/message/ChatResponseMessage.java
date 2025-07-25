package com.example.nettyim_demo.netty.message;

import lombok.Data;

@Data
public class ChatResponseMessage extends AbstractResponseMessage {
    private String senderId; // 发送者用户名
    private String receiverId;   // 接收者用户名
    private String message;  // 聊天内容
    private String timestamp; // 消息发送时间

    public ChatResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public ChatResponseMessage(boolean success, String reason, String fromUser, String toUser, String message, String timestamp) {
        super(success, reason);
        this.senderId = fromUser;
        this.receiverId = toUser;
        this.message = message;
        this.timestamp = timestamp;   // 这里传过来而不是使用 System.currentTimeMillis() 是 只是打印一下 log出来给 开发者看的
    }

    @Override
    public int getMessageType() {
        return MessageType.CHAT_RESPONSE_MESSAGE;
    }

    @Override
    public String toString() {
        return "ChatResponseMessage{senderId='" + senderId + "', receiverId='" + receiverId + "', message='" + message + "', timestamp='" + timestamp + "', success=" + isSuccess() + ", reason='" + getReason() + "'}";
    }

}
