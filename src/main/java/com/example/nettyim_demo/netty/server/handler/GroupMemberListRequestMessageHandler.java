package com.example.nettyim_demo.netty.server.handler;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.nettyim_demo.netty.message.GroupMemberListRequestMessage;
import com.example.nettyim_demo.netty.message.GroupMemberListResponseMessage;
import com.example.nettyim_demo.netty.server.session.GroupSession;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ChannelHandler.Sharable
public class GroupMemberListRequestMessageHandler extends SimpleChannelInboundHandler<GroupMemberListRequestMessage> {

    @Autowired
    private GroupSession groupSession;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMemberListRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        if (groupName == null || groupName.isEmpty()) {
            log.debug("群聊名称不能为空");
            ctx.writeAndFlush(new GroupMemberListResponseMessage(false, "群聊名称不能为空"));
            return;
        }
        Set<String> members = groupSession.getMembers(groupName);
        if (members == null || members.isEmpty()) {
            log.debug("群聊 {} 不存在或没有成员", groupName);
            ctx.writeAndFlush(new GroupMemberListResponseMessage(false, "群聊不存在或没有成员"));
        } else {
            ctx.writeAndFlush(new GroupMemberListResponseMessage(true, "获取群成员列表成功", groupName, members));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("处理群成员列表请求时发生异常", cause);
        ctx.close(); // 关闭连接
    }
    
}
