package com.example.nettyim_demo.netty.client.handler;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.example.nettyim_demo.netty.client.session.ClientSession;
import com.example.nettyim_demo.netty.message.ChatRequestMessage;
import com.example.nettyim_demo.netty.message.GroupCreateRequestMessage;
import com.example.nettyim_demo.netty.message.LoginRequestMessage;
import com.example.nettyim_demo.netty.message.LoginResponseMessage;
import com.example.nettyim_demo.netty.message.LogoutRequestMessage;
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

        if (msg instanceof LoginResponseMessage) {
            LoginResponseMessage loginResponse = (LoginResponseMessage) msg;
            if (loginResponse.isSuccess()) {
                ClientSession.setUsername(loginResponse.getUsername());
                log.debug("用户记录设置成功: {}", loginResponse.getUsername());
            } else {
                log.debug("Login failed: {}", loginResponse.getReason());
            }
        }

        // 输出响应信息（可以改成更友好格式）
        // System.out.println(response);

        // 显示下一轮输入提示
        printPrompt();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);

            showMenu();
            printPrompt();

            while (true) {
                String command = scanner.nextLine();
                String[] parts = command.trim().split(" ", 3);
                String cmd = parts[0].toLowerCase();

                switch (cmd) {
                    case "register":
                        if (parts.length == 3) {
                            ctx.writeAndFlush(new RegisterRequestMessage(parts[1], parts[2]));
                            showMenu();
                            printPrompt();
                        } else {
                            showMenu();
                            System.out.println("注册命令格式错误，请使用: register <username> <password>");
                            printPrompt();
                        }
                        break;
                    case "login":
                        if (parts.length == 3) {
                            ctx.writeAndFlush(new LoginRequestMessage(parts[1], parts[2],
                                    String.valueOf(System.currentTimeMillis())));
                            showMenu();
                            printPrompt();
                        } else {
                            showMenu();
                            System.out.println("登录命令格式错误，请使用: login <username> <password>");
                            printPrompt();
                        }
                        break;
                    case "logout":
                        if (parts.length == 2) {
                            ctx.writeAndFlush(new LogoutRequestMessage(parts[1]));
                            showMenu();
                            printPrompt();
                        } else {
                            showMenu();
                            System.out.println("登出命令格式错误，请使用: logout <username>");
                            printPrompt();
                        }
                        break;
                    case "send":
                        if (parts.length < 3) {
                            showMenu();
                            System.out.println("发送消息命令格式错误，请使用: send <to_user> <message>");
                            printPrompt();
                        } else {
                            String toUser = parts[1];
                            String message = parts[2];
                            ctx.writeAndFlush(new ChatRequestMessage(toUser, message));
                            showMenu();
                            printPrompt();
                        }
                        break;
                    case "gcreate":
                        if (parts.length < 3) {
                            showMenu();
                            System.out.println("创建群聊命令格式错误，请使用: gcreate <group_name> <u1,u2,...>");
                            printPrompt();
                        } else {
                            String groupName = parts[1];
                            String[] members = parts[2].split(",");
                            Set<String> memberSet = new HashSet<>(List.of(members));
                            String self = ClientSession.getUsername();
                            memberSet.add(self);
                            log.debug("Creating group '{}' with members: {}", groupName, memberSet);
                            ctx.writeAndFlush(new GroupCreateRequestMessage(groupName, memberSet));
                            showMenu();
                            printPrompt();
                        }
                        break;
                    
                    default:
                        showMenu();
                        System.out.println("未知命令，请重新输入！");
                        printPrompt();
                }
            }
        }, "ConsoleInput").start();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("Channel inactive, closing connection.");
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
        System.out.println("│   3. logout   <username>             登出     │");
        System.out.println("├───────────────────────────────────────────────┤");
        System.out.println("│ 💬 单聊功能：                                 │");
        System.out.println("│   4. send     <to_user> <message>   发送消息  │");
        System.out.println("├───────────────────────────────────────────────┤");
        System.out.println("│ 👥 群组功能：                                 │");
        System.out.println("│   5. gcreate  <group> <u1,u2,...>   创建群聊  │");
        System.out.println("│   6. glist                          查看所有群│");
        System.out.println("│   7. gmembers <group>               查看群成员│");
        System.out.println("│   8. gsend    <group> <message>     群发消息  │");
        System.out.println("│   9. gjoin    <group>               加入群聊  │");
        System.out.println("│  10. gleave   <group>               退出群聊  │");
        System.out.println("├───────────────────────────────────────────────┤");
        System.out.println("│ ❌ 系统命令：                                 │");
        System.out.println("│  11. quit                           退出客户端│");
        System.out.println("└───────────────────────────────────────────────┘");
        System.out.println(">>> 请输入命令: ");
    }

    private void printPrompt() {
        System.out.print(">>> ");
    }

}
