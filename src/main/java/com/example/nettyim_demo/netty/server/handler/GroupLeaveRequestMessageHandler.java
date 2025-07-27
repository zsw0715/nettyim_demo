package com.example.nettyim_demo.netty.server.handler;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.nettyim_demo.netty.message.GroupChatResponseMessage;
import com.example.nettyim_demo.netty.message.GroupLeaveRequestMessage;
import com.example.nettyim_demo.netty.message.GroupLeaveResponseMessage;
import com.example.nettyim_demo.netty.server.session.GroupSession;
import com.example.nettyim_demo.netty.server.session.SessionFactory;
import com.example.nettyim_demo.service.GroupChatService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ChannelHandler.Sharable
public class GroupLeaveRequestMessageHandler extends SimpleChannelInboundHandler<GroupLeaveRequestMessage> {

    @Autowired
    private GroupChatService groupChatService;

    @Autowired
    private GroupSession groupSession;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupLeaveRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        String username = msg.getUsername();
        if (groupName == null || groupName.isEmpty()) {
            log.debug("群组名称不能为空");
            ctx.writeAndFlush(new GroupLeaveResponseMessage(false, "群组名称不能为空"));
            return;
        }
        if (username == null || username.isEmpty()) {
            log.debug("用户名不能为空");
            ctx.writeAndFlush(new GroupLeaveResponseMessage(false, "用户名不能为空"));
            return;
        }
        // 从数据库中移除用户
        boolean removedFromDatabase = groupChatService.removeMemberFromGroup(groupName, username);

        if (!removedFromDatabase) {
            log.debug("从数据库中移除用户失败");
            ctx.writeAndFlush(new GroupLeaveResponseMessage(false, "离开群组失败，可能是数据库错误"));
            return;
        }

        // 从群组会话中移除用户
        boolean successGroupSession = groupSession.leaveGroup(groupName, username);
        if (!successGroupSession) {
            log.debug("从群组会话中移除用户失败");
            ctx.writeAndFlush(new GroupLeaveResponseMessage(false, "离开群组失败，可能是群组不存在或用户不在群组中"));
            return;
        }

        ctx.writeAndFlush(new GroupLeaveResponseMessage(true, "离开群组成功", groupName, username,
                String.valueOf(System.currentTimeMillis())));

        // 给群里每一个人 发送离开群组的消息
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
                log.debug("群聊消息发送给 {} 成员，但 {} 成员不在线", SessionFactory.getSession().getUsername(channel),
                        SessionFactory.getSession().getUsername(channel));
                // 存入 消息队列的逻辑放到 demo2 中实现
            } else {
                // 发送群聊消息给在线的成员
                GroupChatResponseMessage responseMessage = new GroupChatResponseMessage(true, "群聊消息发送成功", username,
                        groupName, String.format("%s刚才离开了群聊", username), String.valueOf(System.currentTimeMillis()));
                channel.writeAndFlush(responseMessage);
            }
        }
        // 发给发送者自己
        // ctx.writeAndFlush(new GroupChatResponseMessage(true, "群聊消息已发送", username, groupName,
        //         String.format("%s刚才离开了群聊", username), String.valueOf(System.currentTimeMillis())));

        // 保存这条群聊消息到数据库
        groupChatService.saveGroupMessage(username, groupName, String.format("%s刚才离开了群聊", username));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 处理异常
        log.error("处理群组离开请求时发生异常", cause);
        ctx.close(); // 关闭连接
    }

}
