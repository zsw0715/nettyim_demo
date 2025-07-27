package com.example.nettyim_demo.netty.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.nettyim_demo.netty.protocol.MessageCodecSharable;
import com.example.nettyim_demo.netty.protocol.ProtocolFramerDecoder;
import com.example.nettyim_demo.netty.server.handler.ChatRequestMessageHandler;
import com.example.nettyim_demo.netty.server.handler.GroupCreateRequestMessageHandler;
import com.example.nettyim_demo.netty.server.handler.LoginRequestMessageHandler;
import com.example.nettyim_demo.netty.server.handler.LogoutRequestMessageHandler;
import com.example.nettyim_demo.netty.server.handler.RegisterRequestMessageHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

// @Slf4j
// @Component
// public class NettyServer {

//     public static void main(String[] args) {
//         new NettyServer().start();
//     }

//     public void start() {
//         NioEventLoopGroup bossGroup = new NioEventLoopGroup();
//         NioEventLoopGroup workerGroup = new NioEventLoopGroup();
//         // LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
//         MessageCodecSharable messageCodec = new MessageCodecSharable();
//         ProtocolFramerDecoder protocolFramerDecoder = new ProtocolFramerDecoder();
//         // 自定义处理器
        
//         try {
//             ServerBootstrap serverBootstrap = new ServerBootstrap()
//                     .group(bossGroup, workerGroup)
//                     .channel(NioServerSocketChannel.class)
//                     .childHandler(new ChannelInitializer<NioSocketChannel>() {
//                         @Override
//                         protected void initChannel(NioSocketChannel ch) throws Exception {
//                             ch.pipeline().addLast(protocolFramerDecoder);
//                             // ch.pipeline().addLast(loggingHandler);
//                             ch.pipeline().addLast(messageCodec);
//                             // Add other handlers as needed, e.g., for login, message handling, etc.
//                             ch.pipeline().addLast(new RegisterRequestMessageHandler());
//                             ch.pipeline().addLast(new LoginRequestMessageHandler());
//                             ch.pipeline().addLast(new LogoutRequestMessageHandler());
//                             ch.pipeline().addLast(new ChatRequestMessageHandler());
//                             ch.pipeline().addLast(new GroupCreateRequestMessageHandler());
//                         }
//                     });
//             ChannelFuture channelFuture = serverBootstrap.bind(8090).sync();
//             log.debug("Netty Server started on port 8090...");
//             channelFuture.channel().closeFuture().sync();
//         } catch (Exception e) {
//             log.debug("Error With Netty Server: {}", e.getMessage());
//         } finally {
//             bossGroup.shutdownGracefully();
//             workerGroup.shutdownGracefully();
//         }
//     }

// }

// 使用 CommandLineRunner 来启动 Netty 服务器, Spring Boot 会在应用启动时自动调用 run 方法。
@Component
@Slf4j
public class NettyServer implements CommandLineRunner {

    @Autowired
    private RegisterRequestMessageHandler registerHandler;

    @Autowired
    private LoginRequestMessageHandler loginHandler;

    @Autowired
    private LogoutRequestMessageHandler logoutHandler;

    @Autowired
    private ChatRequestMessageHandler chatHandler;

    @Autowired
    private GroupCreateRequestMessageHandler groupCreateHandler;

    @Autowired
    private MessageCodecSharable messageCodec;

    // @Autowired
    // private ProtocolFramerDecoder protocolFramerDecoder;

    @Override
    public void run(String... args) throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) {
                            ch.pipeline().addLast(new ProtocolFramerDecoder());
                            ch.pipeline().addLast(messageCodec);
                            ch.pipeline().addLast(registerHandler);
                            ch.pipeline().addLast(loginHandler);
                            ch.pipeline().addLast(logoutHandler);
                            ch.pipeline().addLast(chatHandler);
                            ch.pipeline().addLast(groupCreateHandler); // ✅ Spring 注入的 handler
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(8090).sync();
            log.debug("✅ Netty Server started on port 8090...");
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
