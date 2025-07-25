package com.example.nettyim_demo.netty.client.handler;

import java.util.Scanner;

import com.example.nettyim_demo.netty.message.LoginRequestMessage;
import com.example.nettyim_demo.netty.message.RegisterRequestMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String response = msg.toString();
        log.debug("Received response from server: {}", response);
        System.out.print(">>> ");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        // demo 阶段 控制台模拟一切请求
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                showMenu();
                // System.out.print(">>> ");
                String command = scanner.nextLine();
                String[] parts = command.trim().split(" ", 3);
                String cmd = parts[0].toLowerCase();

                switch (cmd) {
                    case "register":
                        if (parts.length == 3) {
                            String username = parts[1];
                            String password = parts[2];
                            ctx.writeAndFlush(new RegisterRequestMessage(username, password));
                        } else {
                            System.out.println("注册命令格式错误，请使用: register <username> <password>");
                        }
                        break;
                    case "login":
                        if (parts.length == 3) {
                            String username = parts[1];
                            String password = parts[2];
                            String timestamp = String.valueOf(System.currentTimeMillis());
                            ctx.writeAndFlush(new LoginRequestMessage(username, password, timestamp));
                        } else {
                            System.out.println("登录命令格式错误，请使用: login <username> <password>");
                        }
                        break;
                    default:
                        System.out.println("未知命令，请重新输入！");
                }
            }
        }, "ConsoleInput").start();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    private void showMenu() {
        System.out.println("\n");
        System.out.println("┌───────────────────────────────────────────────┐");
        System.out.println("│             🌐 Netty IM Client v1.0           │");
        System.out.println("├───────────────────────────────────────────────┤");
        System.out.println("│ ✨ 账户管理：                                 │");
        System.out.println("│   1. register <username> <password>  注册     │");
        System.out.println("│   2. login    <username> <password>  登录     │");
        System.out.println("│   3. logout                         登出      │");
        System.out.println("├───────────────────────────────────────────────┤");
        System.out.println("│ 💬 单聊功能：                                 │");
        System.out.println("│   4. send <to_user> <message>       发送消息  │");
        System.out.println("├───────────────────────────────────────────────┤");
        System.out.println("│ 👥 群组功能：                                 │");
        System.out.println("│   5. gcreate  <group> <u1,u2,...>   创建群聊  │");
        System.out.println("│   6. gjoin    <group>               加入群聊  │");
        System.out.println("│   7. gleave   <group>               退出群    │");
        System.out.println("│   8. gsend    <group> <message>     群发息    │");
        System.out.println("│   9. gmembers <group>               查看成员  │");
        System.out.println("├───────────────────────────────────────────────┤");
        System.out.println("│ ❌ 系统命令：                                 │");
        System.out.println("│  10. quit                           退出客户端│");
        System.out.println("└───────────────────────────────────────────────┘");
        System.out.println(">>> 请输入命令: ");
    }

}
