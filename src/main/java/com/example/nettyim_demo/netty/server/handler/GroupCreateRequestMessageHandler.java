package com.example.nettyim_demo.netty.server.handler;

import java.util.Set;

import com.example.nettyim_demo.netty.message.GroupCreateRequestMessage;
import com.example.nettyim_demo.netty.message.GroupCreateResponseMessage;
import com.example.nettyim_demo.netty.server.session.GroupSession;
import com.example.nettyim_demo.netty.server.session.GroupSessionFactory;
import com.example.nettyim_demo.netty.server.session.SessionFactory;
import com.example.nettyim_demo.service.GroupChatService;
import com.example.nettyim_demo.util.SpringContextUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {

    private final GroupChatService groupChatService = SpringContextUtils.getBean(GroupChatService.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        Set<String> members = msg.getMembers();
        String creator = SessionFactory.getSession().getUsername(ctx.channel());

        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        if (groupSession.getMembers(groupName) != null) {
            ctx.writeAndFlush(new GroupCreateResponseMessage(false, "群名已存在，请换一个"));
            return;
        }

        boolean saved = groupChatService.createGroup(groupName, creator);
        if (!saved) {
            ctx.writeAndFlush(new GroupCreateResponseMessage(false, "群组保存数据库失败"));
            return;
        }

        // 调用 GroupSessionFactory 创建群组
        boolean success = GroupSessionFactory.getGroupSession().createGroup(groupName, members);

        if (success) {
            log.debug("群组 '" + groupName + "' 创建成功，成员：" + members);
            ctx.writeAndFlush(new GroupCreateResponseMessage(true, "群组创建成功", groupName, creator,
                    String.valueOf(System.currentTimeMillis()), members));

        } else {
            log.debug("群组 '" + groupName + "' 创建失败，可能已存在或成员列表无效");
            ctx.writeAndFlush(new GroupCreateResponseMessage(false, "群组创建失败", null, null,
                    String.valueOf(System.currentTimeMillis()), null));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 处理异常
        cause.printStackTrace();
        ctx.close(); // 关闭连接
    }

}
