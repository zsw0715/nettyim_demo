package com.example.nettyim_demo.netty.message;
;
public class GroupAllRequestMessage extends Message {
    @Override
    public int getMessageType() {
        return MessageType.GROUP_ALL_REQUEST_MESSAGE;
    }
}
