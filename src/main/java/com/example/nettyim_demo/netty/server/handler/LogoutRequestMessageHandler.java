package com.example.nettyim_demo.netty.server.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.nettyim_demo.netty.message.LogoutRequestMessage;
import com.example.nettyim_demo.netty.message.LogoutResponseMessage;
import com.example.nettyim_demo.netty.server.session.SessionFactory;
import com.example.nettyim_demo.service.UserService;
import com.example.nettyim_demo.util.SpringContextUtils;

import io.netty.channel.ChannelHandler;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ChannelHandler.Sharable
public class LogoutRequestMessageHandler extends SimpleChannelInboundHandler<LogoutRequestMessage> {

    // private final UserService userService = SpringContextUtils.getBean(UserService.class);

    @Autowired
    private UserService userService;


    @Override
    protected void channelRead0(io.netty.channel.ChannelHandlerContext ctx, LogoutRequestMessage msg) throws Exception {
        String username = msg.getUsername();

        boolean success = userService.logout(username);

        if (success) {
            // 解绑用户会话
            SessionFactory.getSession().unbind(ctx.channel());
            log.debug("User logged out successfully: {}", username);
            ctx.writeAndFlush(new LogoutResponseMessage(true, "登出成功", username, String.valueOf(System.currentTimeMillis())));
        } else {
            log.debug("User logout failed: {}", username);
            ctx.writeAndFlush(new LogoutResponseMessage(false, "登出失败：用户不存在或其他错误"));
        }
    }

    @Override
    public void exceptionCaught(io.netty.channel.ChannelHandlerContext ctx, Throwable cause) {
        log.error("Error occurred: {}", cause.getMessage());
        ctx.close(); 
    }
    
}
