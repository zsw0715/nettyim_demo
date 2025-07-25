package com.example.nettyim_demo.netty.message;

import java.io.Serializable;

import lombok.Data;

@Data
public abstract class Message implements Serializable {
    private int sequenceId; // 请求序号，用于双向异步通信

    /**
     * 每个具体子类必须实现这个方法，返回自己的消息类型编号
     */
    public abstract int getMessageType();
}
