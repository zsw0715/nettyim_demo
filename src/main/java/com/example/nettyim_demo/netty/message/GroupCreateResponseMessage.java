package com.example.nettyim_demo.netty.message;

import java.util.Set;

import lombok.Data;

@Data
public class GroupCreateResponseMessage extends AbstractResponseMessage {
    private String groupName; // 群组名称
    private String creator;   // 创建者用户名
    private Set<String> members; // 群组成员列表
    private String timestamp; // 创建时间戳

    public GroupCreateResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public GroupCreateResponseMessage(boolean success, String reason, String groupName, String creator, String timestamp, Set<String> members) {
        super(success, reason);
        this.groupName = groupName;
        this.creator = creator;
        this.members = members;
        this.timestamp = timestamp;
    }

    @Override
    public int getMessageType() {
        return MessageType.GROUP_CREATE_RESPONSE_MESSAGE;
    }

    @Override
    public String toString() {
        return "GroupCreateResponseMessage{" +
                "success=" + isSuccess() +
                ", reason='" + getReason() + '\'' +
                ", groupName='" + groupName + '\'' +
                ", creator='" + creator + '\'' +
                ", members=" + members +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

}
