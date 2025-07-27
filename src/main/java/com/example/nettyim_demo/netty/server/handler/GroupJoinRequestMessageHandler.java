package com.example.nettyim_demo.netty.server.handler;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.nettyim_demo.netty.message.GroupChatResponseMessage;
import com.example.nettyim_demo.netty.message.GroupJoinRequestMessage;
import com.example.nettyim_demo.netty.message.GroupJoinResponseMessage;
import com.example.nettyim_demo.netty.server.session.GroupSession;
import com.example.nettyim_demo.netty.server.session.SessionFactory;
import com.example.nettyim_demo.service.GroupChatService;

import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ChannelHandler.Sharable
public class GroupJoinRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {

    @Autowired
    private GroupChatService groupChatService;

    @Autowired
    private GroupSession groupSession;

    @Override
    protected void channelRead0(io.netty.channel.ChannelHandlerContext ctx, GroupJoinRequestMessage msg) throws Exception {
        // 处理群组加入请求
        String groupName = msg.getGroupName();
        String username = msg.getUsername();
        if (groupName == null || groupName.isEmpty()) {
            ctx.writeAndFlush(new GroupJoinResponseMessage(false, "群组名称不能为空"));
            return;
        }
        if (username == null || username.isEmpty()) {
            ctx.writeAndFlush(new GroupJoinResponseMessage(false, "用户名不能为空"));
            return;
        }
        // 添加到数据库
        boolean savedToDatabase = groupChatService.addMemberToGroup(groupName, username);
        if (!savedToDatabase) {
            ctx.writeAndFlush(new GroupJoinResponseMessage(false, "加入群组失败，可能是数据库错误"));
            return;
        }
        // 添加到群组会话，这里先加入 database 是因为 GroupInfo 有一个 判断 GroupName 是否存在的逻辑
        boolean successGroupSession = groupSession.joinGroup(groupName, username);
        if (!successGroupSession) {
            ctx.writeAndFlush(new GroupJoinResponseMessage(false, "加入群组失败，可能是群组不存在或已在群组中"));
            return;
        }
        ctx.writeAndFlush(new GroupJoinResponseMessage(true, "加入群组成功", groupName, username, String.valueOf(System.currentTimeMillis())));

        // 给群里每一个人 发送加入群组的消息
        Set<String> members = groupSession.getMembers(groupName);
        if (members == null || members.isEmpty()) {
            log.debug("群聊 '{}' 没有成员", groupName);
            ctx.writeAndFlush(new GroupChatResponseMessage(false, "群聊没有成员"));
            return;
        }

        Set<Channel> receiverChannels = new HashSet<>();
        for (String member : members) {
            Channel channel = SessionFactory.getSession().getChannel(member);
            if (channel != null) {
                receiverChannels.add(channel);
            }
        }

        for (Channel channel : receiverChannels) {
            if (channel == null || !channel.isActive()) {
                // 如果不在线的活以后加入 消息队列中
                // 但是存到 mysql 数据库先
                log.debug("群聊消息发送给 {} 成员，但 {} 成员不在线", SessionFactory.getSession().getUsername(channel), SessionFactory.getSession().getUsername(channel));
                // 存入 消息队列的逻辑放到 demo2 中实现
            } else {
                // 发送群聊消息给在线的成员
                GroupChatResponseMessage responseMessage = new GroupChatResponseMessage(true, "群聊消息发送成功", username, groupName, String.format("%s刚才加入了群聊", username), String.valueOf(System.currentTimeMillis()));
                channel.writeAndFlush(responseMessage);
            }
        }
        // 发给发送者自己
        ctx.writeAndFlush(new GroupChatResponseMessage(true, "群聊消息已发送", username, groupName, String.format("%s刚才加入了群聊", username), String.valueOf(System.currentTimeMillis())));

        // 保存这条群聊消息到数据库
        groupChatService.saveGroupMessage(username, groupName, String.format("%s刚才加入了群聊", username));
    }

    @Override
    public void exceptionCaught(io.netty.channel.ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}