package com.example.nettyim_demo.netty.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupLeaveRequestMessage extends Message {
    private String groupName; // 群组名称
    private String username;  // 用户名（离群的用户）
    private String timestamp; // 离群时间戳

    @Override
    public int getMessageType() {
        return MessageType.GROUP_LEAVE_REQUEST_MESSAGE;
    }

}
