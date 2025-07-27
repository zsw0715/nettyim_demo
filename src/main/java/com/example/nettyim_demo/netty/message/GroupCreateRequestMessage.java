package com.example.nettyim_demo.netty.message;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupCreateRequestMessage extends Message {
    // id 不用传，因为数据库会自动生成
    private String groupName; // 群组名称
    private Set<String> members; // 群组成员列表
    // private String timestamp; // 创建时间戳
    
    @Override
    public int getMessageType() {   
        return MessageType.GROUP_CREATE_REQUEST_MESSAGE;
    }

}