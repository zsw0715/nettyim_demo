package com.example.nettyim_demo.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyClient {
    
    public static void main(String[] args) {
        new NettyClient().start();
    }

    void start() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(LOGGING_HANDLER);
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8080).sync();
            log.debug("Netty Client connected to server on port 8080...");
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.debug("Error With Netty Client: {}", e.getMessage());
        } finally {
            group.shutdownGracefully();
        }
    }

}
