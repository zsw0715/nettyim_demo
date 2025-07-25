package com.example.nettyim_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.nettyim_demo.netty.server.NettyServer;

@SpringBootApplication
public class NettyimDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NettyimDemoApplication.class, args);

		new Thread(() -> {
			try {
				new NettyServer().start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, "NettyServer").start();
	}

}
