package com.example.nettyim_demo.netty.message;

import lombok.Data;

@Data
public class GroupChatResponseMessage extends AbstractResponseMessage {
    // private String groupId; // 群组ID
    private String sender; // 发送者
    private String groupName; // 群组名称
    private String content; // 消息内容
    private String timestamp; // 消息时间戳

    public GroupChatResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public GroupChatResponseMessage(boolean success, String reason, String sender, String groupName, String content, String timestamp) {
        super(success, reason);
        // this.groupId = groupId;
        this.sender = sender;
        this.groupName = groupName;
        this.content = content;
        this.timestamp = timestamp;
    }

    @Override
    public int getMessageType() {
        return MessageType.GROUP_CHAT_RESPONSE_MESSAGE;
    }

    @Override
    public String toString() {
        return "GroupChatResponseMessage{" +
                "success=" + isSuccess() +
                ", reason='" + getReason() + '\'' +
                ", sender='" + sender + '\'' +
                ", groupName='" + groupName + '\'' +
                ", content='" + content + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

}