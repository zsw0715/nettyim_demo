package com.example.nettyim_demo.netty.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class AbstractResponseMessage extends Message {
    private boolean success;
    private String reason;

    @Override
    public String toString() {
        return "AbstractResponseMessage{" +
                "success=" + success +
                ", reason='" + reason + '\'' +
                '}';
    }
}
