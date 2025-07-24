package com.example.nettyim_demo.netty.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogoutRequestMessage extends Message {
    private String username;    // demo 需要用户写一个 logout [username] 的命令，所以需要这个字段, like "logout 张三"
    private String timestamp;   // 登出时间戳

    @Override
    public int getMessageType() {
        return MessageType.LOGOUT_REQUEST_MESSAGE;
    }

}
