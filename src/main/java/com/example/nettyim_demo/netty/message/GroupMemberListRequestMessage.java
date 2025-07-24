package com.example.nettyim_demo.netty.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupMemberListRequestMessage extends Message {
    private String groupName; // 群组名称

    @Override
    public int getMessageType() {
        return MessageType.GROUP_MEMBER_LIST_REQUEST_MESSAGE;
    }

}
