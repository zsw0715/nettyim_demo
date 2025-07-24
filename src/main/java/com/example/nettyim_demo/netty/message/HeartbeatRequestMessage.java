package com.example.nettyim_demo.netty.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HeartbeatRequestMessage extends Message {
    private String from;

    @Override
    public int getMessageType() {
        return MessageType.HEARTBEAT_REQUEST_MESSAGE;
    }

}
