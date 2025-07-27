package com.example.nettyim_demo.netty.message;

import java.util.Set;

import lombok.Data;

@Data
public class GroupListResponseMessage extends AbstractResponseMessage {
    
    private Set<String> groupNames; // 群组名称列表

    public GroupListResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public GroupListResponseMessage(boolean success, String reason, Set<String> groupNames) {
        super(success, reason);
        this.groupNames = groupNames;
    }

    @Override
    public int getMessageType() {
        return MessageType.GROUP_LIST_RESPONSE_MESSAGE;
    }
    
    @Override
    public String toString() {
        return "GroupListResponseMessage{" +
                "success=" + isSuccess() +
                ", reason='" + getReason() + '\'' +
                ", groupNames=" + groupNames +
                '}';
    }

}
