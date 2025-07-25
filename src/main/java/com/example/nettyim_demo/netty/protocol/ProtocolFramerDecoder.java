package com.example.nettyim_demo.netty.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class ProtocolFramerDecoder extends LengthFieldBasedFrameDecoder {

    public ProtocolFramerDecoder() {
        this(1024, 12, 4, 0, 0);
    }

    public ProtocolFramerDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
            int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }
    
}
