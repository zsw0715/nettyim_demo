package com.example.nettyim_demo.netty.message;

import lombok.Data;

@Data
public class LoginResponseMessage extends AbstractResponseMessage {
    // 这个字段因为是demo，所以暂时不使用，之后结合jwt再解封
    // private String token;
    private String username;
    private String timestamp;

    public LoginResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public LoginResponseMessage(boolean success, String reason, String username, String timestamp) {
        super(success, reason);
        this.username = username;
        this.timestamp = timestamp;
    }

    @Override
    public int getMessageType() {
        return MessageType.LOGIN_RESPONSE_MESSAGE;
    }

}
