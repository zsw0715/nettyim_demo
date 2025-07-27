package com.example.nettyim_demo.netty.server.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.nettyim_demo.netty.message.RegisterRequestMessage;
import com.example.nettyim_demo.netty.message.RegisterResponseMessage;
import com.example.nettyim_demo.service.UserService;
import com.example.nettyim_demo.util.SpringContextUtils;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ChannelHandler.Sharable
public class RegisterRequestMessageHandler extends SimpleChannelInboundHandler<RegisterRequestMessage> {

    // private final UserService userService = SpringContextUtils.getBean(UserService.class);
    @Autowired
    private UserService userService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        String password = msg.getPassword();

        boolean success = userService.register(username, password);
        if (success) {
            log.debug("User registered successfully: {}", username);
            ctx.writeAndFlush(new RegisterResponseMessage(username, "注册成功", "at " + System.currentTimeMillis()));
        } else {
            log.debug("User registration failed: {}", username);
            ctx.writeAndFlush(new RegisterResponseMessage(username, "注册失败: 用户名已存在或其他错误", "at " + System.currentTimeMillis()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("Error occurred: {}", cause.getMessage());
        ctx.close();
    }


    // public static void main(String[] args) {
    //     UserService userService = SpringContextUtils.getBean(UserService.class);
    //     userService.register("testUser", "testPassword");
    // }

}
