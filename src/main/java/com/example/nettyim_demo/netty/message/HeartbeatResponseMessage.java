package com.example.nettyim_demo.netty.message;

public class HeartbeatResponseMessage extends AbstractResponseMessage {

    public HeartbeatResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return MessageType.HEARTBEAT_RESPONSE_MESSAGE;
    }

    @Override
    public String toString() {
        return "HeartbeatResponseMessage{" +
                "success=" + isSuccess() +
                ", reason='" + getReason() + '\'' +
                '}';
    }

}
