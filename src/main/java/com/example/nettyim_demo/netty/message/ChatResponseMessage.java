package com.example.nettyim_demo.netty.message;

import lombok.Data;

@Data
public class ChatResponseMessage extends AbstractResponseMessage {
    private String sender; // 发送者用户名
    private String receiver;   // 接收者用户名
    private String message;  // 聊天内容
    private String timestamp; // 消息发送时间

    public ChatResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public ChatResponseMessage(boolean success, String reason, String fromUser, String toUser, String message, String timestamp) {
        super(success, reason);
        this.sender = fromUser;
        this.receiver = toUser;
        this.message = message;
        this.timestamp = timestamp;   // 这里传过来而不是使用 System.currentTimeMillis() 是为了保持与数据库的一致性
    }

    @Override
    public int getMessageType() {
        return MessageType.CHAT_RESPONSE_MESSAGE;
    }

    @Override
    public String toString() {
        return "ChatResponseMessage{sender='" + sender + "', receiver='" + receiver + "', message='" + message + "', timestamp='" + timestamp + "', success=" + isSuccess() + ", reason='" + getReason() + "'}";
    }

}
