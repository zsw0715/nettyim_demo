package com.example.nettyim_demo.netty.message;

import lombok.Data;

@Data
public class RegisterResponseMessage extends AbstractResponseMessage {
    private String username;
    private String message; // 注册结果信息
    private String timestamp; // 注册时间戳

    public RegisterResponseMessage(String username, String message, String timestamp) {
        super(true, "Registration successful");
        this.username = username;
        this.message = message;
        this.timestamp = timestamp;
    }

    @Override
    public int getMessageType() {
        return MessageType.REGISTER_RESPONSE_MESSAGE;
    }

    @Override
    public String toString() {
        return "RegisterResponseMessage{username='" + username + "', message='" + message + "'}";
    }

}
