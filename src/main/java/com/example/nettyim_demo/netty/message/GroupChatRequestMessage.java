package com.example.nettyim_demo.netty.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupChatRequestMessage extends Message {
    private Long groupId; // 群组ID, 用于标识群组, 唯一
    private String sender; // 发送者
    private String groupName; // 群组名称
    private String content; // 消息内容
    private String timestamp; // 消息时间戳

    @Override
    public int getMessageType() {
        return MessageType.GROUP_CHAT_REQUEST_MESSAGE;
    }

}
