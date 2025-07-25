package com.example.nettyim_demo.netty.server.handler;


import com.example.nettyim_demo.entity.User;
import com.example.nettyim_demo.netty.message.LoginRequestMessage;
import com.example.nettyim_demo.netty.message.LoginResponseMessage;
import com.example.nettyim_demo.service.UserService;
import com.example.nettyim_demo.util.SpringContextUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {

    private final UserService userService = SpringContextUtils.getBean(UserService.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        String password = msg.getPassword();

        User user = userService.login(username, password);
        if (user != null) {
            log.debug("User logged in successfully: {}", username);
            ctx.writeAndFlush(new LoginResponseMessage(true, "登录成功", 
                    user.getUsername(), String.valueOf(System.currentTimeMillis())));
        } else {
            log.debug("User login failed: {}", username);
            ctx.writeAndFlush(new LoginResponseMessage(false, "登录失败：用户名或密码错误。请重试。"));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
