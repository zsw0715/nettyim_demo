package com.example.nettyim_demo.netty.message;

public class PingMessage extends Message {

    @Override
    public int getMessageType() {
        return MessageType.PING_MESSAGE;
    }

    @Override
    public String toString() {
        return "PingMessage{}";
    }
}
