package com.example.nettyim_demo.netty.message;

public class GroupListRequestMessage extends Message {

    @Override
    public int getMessageType() {
        return MessageType.GROUP_LIST_REQUEST_MESSAGE;
    }
    
}
