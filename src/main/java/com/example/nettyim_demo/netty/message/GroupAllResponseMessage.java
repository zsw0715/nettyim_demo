package com.example.nettyim_demo.netty.message;

public class GroupAllResponseMessage extends AbstractResponseMessage {
    private String groupName; // 群组名称
    private String timestamp; // 消息时间戳

    public GroupAllResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public GroupAllResponseMessage(boolean success, String reason, String groupName, String timestamp) {
        super(success, reason);
        this.groupName = groupName;
        this.timestamp = timestamp;
    }

    @Override
    public int getMessageType() {
        return MessageType.GROUP_ALL_RESPONSE_MESSAGE;
    }

    @Override
    public String toString() {
        return "GroupAllResponseMessage{" +
                "success=" + isSuccess() +
                ", reason='" + getReason() + '\'' +
                ", groupName='" + groupName + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
