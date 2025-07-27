package com.example.nettyim_demo.netty.server.handler;

import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Component
@ChannelHandler.Sharable
@Slf4j
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent idleEvent) {
            switch (idleEvent.state()) {
                case READER_IDLE -> {
                    log.debug("💔 15秒内未收到客户端数据，关闭连接: {}", ctx.channel().remoteAddress());
                    ctx.close();
                }
                default -> super.userEventTriggered(ctx, evt);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    
}
