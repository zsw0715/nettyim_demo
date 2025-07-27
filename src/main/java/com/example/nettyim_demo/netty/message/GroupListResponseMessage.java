package com.example.nettyim_demo.netty.message;

import java.util.Map;
import java.util.Set;

import lombok.Data;

@Data
public class GroupListResponseMessage extends AbstractResponseMessage {
    
    private Map<String, Set<String>> groups;

    public GroupListResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public GroupListResponseMessage(boolean success, String reason, Map<String, Set<String>> groups) {
        super(success, reason);
        this.groups = groups;
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
                ", groups=" + groups +
                '}';
    }

}
