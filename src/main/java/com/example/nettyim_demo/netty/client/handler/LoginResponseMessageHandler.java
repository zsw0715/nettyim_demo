package com.example.nettyim_demo.netty.client.handler;

import com.example.nettyim_demo.netty.client.session.ClientSession;
import com.example.nettyim_demo.netty.message.LoginResponseMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginResponseMessageHandler extends SimpleChannelInboundHandler<LoginResponseMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponseMessage loginResponse) throws Exception {
        if (loginResponse.isSuccess()) {
            // 登录成功，保存用户名到客户端会话
            ClientSession.setUsername(loginResponse.getUsername());
            log.debug("登录成功！用户记录设置成功: {}", loginResponse.getUsername());
        } else {
            // 登录失败
            log.debug("登录失败: {}", loginResponse.getReason());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 处理异常
        log.error("处理登录响应时发生异常", cause);
        ctx.close(); // 关闭连接
    }

}
