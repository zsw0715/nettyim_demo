package com.example.nettyim_demo.netty.message;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class GroupJoinRequestMessage extends Message {
    private String groupName; // 群组名称
    private String username;  // 用户名
    // private String timestamp; // 加入时间戳

    @Override
    public int getMessageType() {
        return MessageType.GROUP_JOIN_REQUEST_MESSAGE;
    }

}
