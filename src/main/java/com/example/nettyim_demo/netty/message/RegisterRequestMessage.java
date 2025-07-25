package com.example.nettyim_demo.netty.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequestMessage extends Message {
    private String username;
    private String password;
    private String timestamp; // 注册时间戳

    @Override
    public int getMessageType() {
        return MessageType.REGISTER_REQUEST_MESSAGE;
    }

    @Override
    public String toString() {
        return "RegisterRequestMessage{username='" + username + "', password=***, timestamp='" + timestamp + "'}";
    }

}
