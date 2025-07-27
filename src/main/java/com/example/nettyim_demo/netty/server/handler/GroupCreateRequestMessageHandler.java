package com.example.nettyim_demo.netty.server.handler;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.nettyim_demo.netty.message.GroupCreateRequestMessage;
import com.example.nettyim_demo.netty.message.GroupCreateResponseMessage;
import com.example.nettyim_demo.netty.server.session.GroupSession;
// import com.example.nettyim_demo.netty.server.session.GroupSessionFactory;
import com.example.nettyim_demo.netty.server.session.SessionFactory;
import com.example.nettyim_demo.service.GroupChatService;
import com.example.nettyim_demo.util.SpringContextUtils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {

    // private final GroupChatService groupChatService = SpringContextUtils.getBean(GroupChatService.class);
    @Autowired
    private GroupChatService groupChatService;

    @Autowired
    private GroupSession groupSession;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        Set<String> members = msg.getMembers();
        String creator = SessionFactory.getSession().getUsername(ctx.channel());

        // 成功打印日志
        log.debug(creator + " 创建群组 " + groupName + " 成员：" + members + "你好！！");

        Set<String> existingMembers = groupSession.getMembers(groupName);
        if (!existingMembers.isEmpty()) {
            log.debug("群已存在: {}", groupName);
            ctx.writeAndFlush(new GroupCreateResponseMessage(false, "群名已存在，请换一个"));
            return;
        }

        boolean saved = groupChatService.createGroup(groupName, creator, members);
        if (!saved) {
            log.debug("启动！", "saved {}", saved);
            ctx.writeAndFlush(new GroupCreateResponseMessage(false, "群组保存数据库失败"));
            return;
        }

        log.debug("step 2!!!");

        // 调用 GroupSession 创建群组
        boolean success = groupSession.createGroup(groupName, members);

        // 获取群组成员的通道
        Set<Channel> memberChannels = groupSession.getMemberChannels(groupName);

        log.debug("step 3!!!");
        if (success) {
            log.debug("群组 '" + groupName + "' 创建成功，成员：" + members);
            // 通知自己
            ctx.writeAndFlush(new GroupCreateResponseMessage(true, "群组创建成功", groupName, creator,
                    String.valueOf(System.currentTimeMillis()), members));

            log.debug("step 4!!!");

            // 通知群组成员
            for (Channel channel : memberChannels) {
                if (channel != null && channel.isActive()) {
                    channel.writeAndFlush(
                            new GroupCreateResponseMessage(true, "你已被添加到群组 " + groupName, groupName, creator,
                                    String.valueOf(System.currentTimeMillis()), members));
                } else if (channel != null) {
                    log.debug("Channel for user {} is inactive, skipping notification", channel.id());
                } else {
                    log.debug("Channel is null, skipping notification");
                }
            }

        } else {
            log.debug("群组 '" + groupName + "' 创建失败，可能已存在或成员列表无效");
            ctx.writeAndFlush(new GroupCreateResponseMessage(false, "群组创建失败", null, null,
                    String.valueOf(System.currentTimeMillis()), null));
            log.debug("step 5!!!");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 处理异常
        cause.printStackTrace();
        ctx.close(); // 关闭连接
    }

}
