package com.example.nettyim_demo.netty.client.session;

/**
 * 客户端不能依赖服务端 Session 来取用户名，应该在 login 成功后 主动保存自己的用户名
 */
public class ClientSession {
    private static volatile String username;

    public static void setUsername(String name) {
        username = name;
    }

    public static String getUsername() {
        return username;
    }

    public static boolean isLogin() {
        return username != null;
    }
}