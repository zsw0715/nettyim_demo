package com.example.nettyim_demo.netty.message;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 每个具体子类必须实现这个方法，返回自己的消息类型编号
     */
    public abstract int getMessageType();
}
