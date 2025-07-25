package com.example.nettyim_demo.netty.message;

import lombok.Data;

@Data
public class LogoutResponseMessage extends AbstractResponseMessage {
    private String username; // 用户名
    private String timestamp;     // 登出时间

    public LogoutResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public LogoutResponseMessage(boolean success, String reason, String username, String timestamp) {
        super(success, reason);
        this.username = username;
        this.timestamp = timestamp;
    }

    @Override
    public int getMessageType() {
        return MessageType.LOGOUT_RESPONSE_MESSAGE;
    }

    @Override
    public String toString() {
        return "LogoutResponseMessage{username='" + username + "', timestamp='" + timestamp + "', success=" + isSuccess() + ", reason='" + getReason() + "'}";
    }

}
