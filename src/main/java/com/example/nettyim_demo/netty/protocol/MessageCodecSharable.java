package com.example.nettyim_demo.netty.protocol;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.nettyim_demo.netty.message.Message;
import com.example.nettyim_demo.netty.message.MessageTypeManager;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;
import io.netty.channel.ChannelHandler;

/**
 * 自定义协议消息结构类，用于 Netty 通信中定义消息格式。
 *
 * 包含以下字段以支持完整的通信协议功能：
 *
 * 1. 魔数（Magic Number） - 用于快速判断是否为合法协议包，防止误解析。
 * 2. 协议版本号（Version） - 支持协议升级和兼容性处理。
 * 3. 序列化算法（Serializer） - 指定消息正文序列化方式（如 JSON、Protobuf、Hessian、JDK 原生等）。
 * 4. 指令类型（Command Type） - 表示消息功能，如登录、注册、单聊、群聊等业务类型。
 * 5. 请求序号（Sequence ID） - 为支持双向异步通信提供唯一标识。
 * 6. 正文长度（Content Length） - 表示正文部分的字节长度，便于拆包粘包处理。
 * 7. 消息正文（Body） - 实际传输的数据内容，如用户名、密码、聊天内容等。
 *
 * 本类将配合编码器/解码器（如 MessageCodec）进行编解码处理，作为 Netty 中消息传输的基础结构。
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {

    private static final int MAGIC_NUMBER = 0x20040715; // 魔数，用于标识协议包
    private static final byte VERSION = 1; // 协议版本号

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        ByteBuf buf = ctx.alloc().buffer();

        // 魔数 4 字节
        buf.writeInt(MAGIC_NUMBER);
        // 版本号 1 字节
        buf.writeByte(VERSION);
        // 序列化方式 1 字节（0 表示 JAVA）
        buf.writeByte(0);
        // 消息类型 1 字节
        buf.writeByte(msg.getMessageType());
        // 请求序号 4 字节
        buf.writeInt(msg.getSequenceId());
        // 填充 1 字节，保留位
        buf.writeByte(0); // 保留位，未来可扩展
        // 序列化消息体
        byte[] bytes = Serializer.Algorithm.JAVA.serialize(msg);
        // 消息体长度 4 字节
        buf.writeInt(bytes.length);
        // 消息体内容
        buf.writeBytes(bytes);

        out.add(buf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        // 魔数
        int magic = buf.readInt();
        // 版本
        byte version = buf.readByte();
        // 序列化方式
        byte serializerType = buf.readByte();
        // 消息类型
        byte messageType = buf.readByte();
        // 请求序号
        int sequenceId = buf.readInt();
        // 保留位
        byte reserved = buf.readByte();
        // 消息体长度
        int length = buf.readInt();
        // 消息体内容
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);

        // 反序列化
        Serializer serializer = Serializer.Algorithm.values()[serializerType];
        Class<? extends Message> messageClass = MessageTypeManager.getMessageClass((int) messageType);
        Message message = serializer.deserialize(messageClass, bytes);
        message.setSequenceId(sequenceId);

        out.add(message);
    }
    
}
