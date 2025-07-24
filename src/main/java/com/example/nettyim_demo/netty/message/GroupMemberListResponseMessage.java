package com.example.nettyim_demo.netty.message;

import java.util.Set;

import lombok.Data;

@Data
public class GroupMemberListResponseMessage extends AbstractResponseMessage {
    private String groupName; // 群组名称
    private Set<String> members; // 群组成员列表

    public GroupMemberListResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public GroupMemberListResponseMessage(boolean success, String reason, String groupName, Set<String> members) {
        super(success, reason);
        this.groupName = groupName;
        this.members = members;
    }

    @Override
    public int getMessageType() {
        return MessageType.GROUP_MEMBER_LIST_RESPONSE_MESSAGE;
    }

}
