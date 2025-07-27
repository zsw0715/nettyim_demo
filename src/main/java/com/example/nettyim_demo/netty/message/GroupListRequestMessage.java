package com.example.nettyim_demo.netty.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupListRequestMessage extends Message {

    private String username;

    @Override
    public int getMessageType() {
        return MessageType.GROUP_LIST_REQUEST_MESSAGE;
    }
    
}
