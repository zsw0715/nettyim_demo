package com.example.nettyim_demo.netty.server.handler;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.nettyim_demo.netty.message.GroupAllRequestMessage;
import com.example.nettyim_demo.netty.message.GroupListResponseMessage;
import com.example.nettyim_demo.netty.server.session.GroupSession;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ChannelHandler.Sharable
public class GroupAllRequestMessageHandler extends SimpleChannelInboundHandler<GroupAllRequestMessage> {
    @Autowired
    private GroupSession groupSession;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 处理异常
        cause.printStackTrace();
        ctx.close(); // 关闭连接
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupAllRequestMessage msg) throws Exception {
        Map<String, Set<String>> groupNamesWithMembers = groupSession.getAllGroupNamesWithGroupMembers();
        if (groupNamesWithMembers.isEmpty()) {
            log.debug("没有可用的群聊");
            ctx.writeAndFlush(new GroupListResponseMessage(true, "当前没有可用的群聊"));
        } else {
            ctx.writeAndFlush(new GroupListResponseMessage(true, "群聊列表获取成功", groupNamesWithMembers));
        }
    }

}
