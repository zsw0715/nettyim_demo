package com.example.nettyim_demo.netty.protocol;

import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * ProtocolFramerDecoder is a custom decoder that extends LengthFieldBasedFrameDecoder.
 * It is used to decode messages based on a specific protocol where the length of the message
 * is specified in the header.
 */
public class ProtocolFramerDecoder extends LengthFieldBasedFrameDecoder {

    public ProtocolFramerDecoder() {
        this(1024, 12, 4, 0, 0);
    }

    public ProtocolFramerDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
            int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }
    
}
