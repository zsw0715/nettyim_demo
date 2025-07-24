package com.example.nettyim_demo.netty.message;

import lombok.Data;

@Data
public class GroupLeaveResponseMessage extends AbstractResponseMessage {
    private String groupName; // 群组名称
    private String username;  // 用户名
    private String timestamp; // 离群时间戳

    public GroupLeaveResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public GroupLeaveResponseMessage(boolean success, String reason, String groupName, String username, String timestamp) {
        super(success, reason);
        this.groupName = groupName;
        this.username = username;
        this.timestamp = timestamp;
    }

    @Override
    public int getMessageType() {
        return MessageType.GROUP_LEAVE_RESPONSE_MESSAGE;
    }

}
