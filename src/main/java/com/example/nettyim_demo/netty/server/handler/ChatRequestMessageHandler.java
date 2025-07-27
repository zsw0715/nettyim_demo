package com.example.nettyim_demo.netty.server.handler;

// import com.example.nettyim_demo.entity.User;
import com.example.nettyim_demo.netty.message.ChatRequestMessage;
import com.example.nettyim_demo.netty.message.ChatResponseMessage;
import com.example.nettyim_demo.netty.server.session.SessionFactory;
import com.example.nettyim_demo.service.ChatService;
import com.example.nettyim_demo.service.UserService;
import com.example.nettyim_demo.util.SpringContextUtils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {

    private final ChatService chatService = SpringContextUtils.getBean(ChatService.class);
    private final UserService userService = SpringContextUtils.getBean(UserService.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        // 通过会话管理获取发送者用户名
        String sender = SessionFactory.getSession().getUsername(ctx.channel());
        String receiver = msg.getReceiver();
        String content = msg.getContent();

        Channel receiverChannel = SessionFactory.getSession().getChannel(receiver);

        // 检查用户是否已登录
        if (sender == null) {
            ctx.writeAndFlush("错误：请先登录后再发送消息");
            return;
        }

        // 检查接收者是否在线 --> 以后存到消息队列中，当接收者上线时再发送
        if (receiverChannel == null) {
            ctx.writeAndFlush("错误：接收者 " + receiver + " 不在线或不存在");
            // return;
        } else {
            // 将消息发送给接收者
            receiverChannel.writeAndFlush(new ChatResponseMessage(true, "发送成功", sender, receiver, content, String.valueOf(System.currentTimeMillis())));
            ctx.writeAndFlush(new ChatResponseMessage(true, "消息已发送给 " + receiver, sender, receiver, content, String.valueOf(System.currentTimeMillis())));
        }
        chatService.saveMessage(sender, receiver, content);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        try {
            // 获取用户名
            String username = SessionFactory.getSession().getUsername(ctx.channel());
            // 连接断开时自动解绑会话
            SessionFactory.getSession().unbind(ctx.channel());
            // 如果用户已登录，则更新登出状态
            if (username != null) {
                userService.logout(username);
                log.debug("User {} disconnected and logged out", username);
            }
        } catch (Exception e) {
            log.error("Error during channel inactive processing", e);
        }
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 处理异常
        cause.printStackTrace();
        ctx.close(); // 关闭连接
    }

}
