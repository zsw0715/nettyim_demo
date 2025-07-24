package com.example.nettyim_demo.netty.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestMessage extends Message {
    // 这两个字段因为是demo，所以暂时不使用，之后结合jwt再解封
    // private long uid;
    private String username;
    private String password;
    private String timestamp; // 登录时间戳

    @Override
    public int getMessageType() {
        return MessageType.LOGIN_REQUEST_MESSAGE;
    }

    @Override
    public String toString() {
        return "LoginRequestMessage{username='" + username + "', password=***}";
    }

}
