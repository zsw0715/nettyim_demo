package com.example.nettyim_demo.netty.server.handler;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.nettyim_demo.netty.message.GroupChatRequestMessage;
import com.example.nettyim_demo.netty.message.GroupChatResponseMessage;
import com.example.nettyim_demo.netty.server.session.GroupSession;
import com.example.nettyim_demo.netty.server.session.SessionFactory;
import com.example.nettyim_demo.service.GroupChatService;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {

    @Autowired
    private GroupSession groupSession;

    @Autowired
    private GroupChatService groupChatService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        if (groupName == null || groupName.isEmpty()) {
            log.debug("群聊名称不能为空");
            ctx.writeAndFlush(new GroupChatResponseMessage(false, "群聊名称不能为空"));
            return;
        }
        String sender = msg.getSender();
        String content = msg.getContent();
        String timestamp = String.valueOf(System.currentTimeMillis());

        // 获取群成员列表 -- 这里也判断了 server重启后 是否加载数据
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
                GroupChatResponseMessage responseMessage = new GroupChatResponseMessage(true, "群聊消息发送成功", sender, groupName, content, timestamp);
                channel.writeAndFlush(responseMessage);
            }
        }
        // 发给发送者自己
        ctx.writeAndFlush(new GroupChatResponseMessage(true, "群聊消息已发送", sender, groupName, content, timestamp));

        // 保存这条群聊消息到数据库
        groupChatService.saveGroupMessage(sender, groupName, content);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("处理群聊请求时发生异常", cause);
        ctx.close(); // 关闭连接
    }
    
}
