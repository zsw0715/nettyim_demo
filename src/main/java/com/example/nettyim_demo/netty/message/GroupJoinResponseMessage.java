package com.example.nettyim_demo.netty.message;

import lombok.Data;

@Data
public class GroupJoinResponseMessage extends AbstractResponseMessage {
    private String groupName; // 群组名称
    private String username;  // 用户名
    private String timestamp; // 加入时间

    public GroupJoinResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public GroupJoinResponseMessage(boolean success, String reason, String groupName, String username, String timestamp) {
        super(success, reason);
        this.groupName = groupName;
        this.username = username;
        this.timestamp = timestamp;
    }

    @Override
    public int getMessageType() {
        return MessageType.GROUP_JOIN_RESPONSE_MESSAGE;
    }

}
