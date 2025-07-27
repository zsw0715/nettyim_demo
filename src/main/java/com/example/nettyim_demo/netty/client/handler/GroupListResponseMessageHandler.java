package com.example.nettyim_demo.netty.client.handler;

import java.util.Map;
import java.util.Set;

import com.example.nettyim_demo.netty.message.GroupListResponseMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GroupListResponseMessageHandler extends SimpleChannelInboundHandler<GroupListResponseMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupListResponseMessage msg) throws Exception {
        if (msg.isSuccess()) {
            // 处理成功的群列表响应
            Map<String, Set<String>> groups = msg.getGroups();
            for (Map.Entry<String, Set<String>> entry : groups.entrySet()) {
                String groupName = entry.getKey();
                Set<String> members = entry.getValue();
                log.debug("群聊名称: {}, 成员: {}", groupName, members);
            }
        } else {
            log.debug("获取群列表失败: {}", msg.getReason());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 处理异常
        log.error("处理群列表响应时发生异常", cause);
        ctx.close(); // 关闭连接 
    }
    
}
