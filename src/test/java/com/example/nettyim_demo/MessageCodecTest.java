package com.example.nettyim_demo;

import org.junit.jupiter.api.Test;

import com.example.nettyim_demo.netty.message.LoginRequestMessage;
import com.example.nettyim_demo.netty.protocol.MessageCodecSharable;

import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class MessageCodecTest {
    
    @Test
    void testCodec() {
        EmbeddedChannel channel = new EmbeddedChannel(new MessageCodecSharable());

        LoginRequestMessage encodedMessage = new LoginRequestMessage("张三", "password123", "2023-10-01T12:00:00Z");
        encodedMessage.setSequenceId(1); // 设置请求序号
        // 编码
        channel.writeOutbound(encodedMessage);

        Object readEncoded = channel.readOutbound();
        channel.writeInbound(readEncoded);

        // 解码
        LoginRequestMessage decodedMessage = channel.readInbound();
        System.out.println("原始消息: " + encodedMessage);
        System.out.println("解码后消息: " + decodedMessage);

        assert encodedMessage.equals(decodedMessage);

    }

    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(
            new LoggingHandler(LogLevel.DEBUG),
            new MessageCodecSharable()
        );

        LoginRequestMessage encodedMessage = new LoginRequestMessage("张三", "password123", "2023-10-01T12:00:00Z");
        encodedMessage.setSequenceId(1); // 设置请求序号
        // 编码
        channel.writeOutbound(encodedMessage);

        Object readEncoded = channel.readOutbound();
        channel.writeInbound(readEncoded);

        // 解码
        LoginRequestMessage decodedMessage = channel.readInbound();
        System.out.println("原始消息: " + encodedMessage);
        System.out.println("解码后消息: " + decodedMessage);
    }

}


// 输出示例： successful encoding and decoding
// 19:04:01 [DEBUG] [main] i.n.h.l.LoggingHandler - [id: 0xembedded, L:embedded - R:embedded] WRITE: 284B
//          +-------------------------------------------------+
//          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
// +--------+-------------------------------------------------+----------------+
// |00000000| 20 04 07 15 01 00 00 00 00 00 01 00 00 00 01 0c | ...............|
// |00000010| ac ed 00 05 73 72 00 3a 63 6f 6d 2e 65 78 61 6d |....sr.:com.exam|
// |00000020| 70 6c 65 2e 6e 65 74 74 79 69 6d 5f 64 65 6d 6f |ple.nettyim_demo|
// |00000030| 2e 6e 65 74 74 79 2e 6d 65 73 73 61 67 65 2e 4c |.netty.message.L|
// |00000040| 6f 67 69 6e 52 65 71 75 65 73 74 4d 65 73 73 61 |oginRequestMessa|
// |00000050| 67 65 45 44 57 a9 f6 2c 4b 5d 02 00 03 4c 00 08 |geEDW..,K]...L..|
// |00000060| 70 61 73 73 77 6f 72 64 74 00 12 4c 6a 61 76 61 |passwordt..Ljava|
// |00000070| 2f 6c 61 6e 67 2f 53 74 72 69 6e 67 3b 4c 00 09 |/lang/String;L..|
// |00000080| 74 69 6d 65 73 74 61 6d 70 71 00 7e 00 01 4c 00 |timestampq.~..L.|
// |00000090| 08 75 73 65 72 6e 61 6d 65 71 00 7e 00 01 78 72 |.usernameq.~..xr|
// |000000a0| 00 2e 63 6f 6d 2e 65 78 61 6d 70 6c 65 2e 6e 65 |..com.example.ne|
// |000000b0| 74 74 79 69 6d 5f 64 65 6d 6f 2e 6e 65 74 74 79 |ttyim_demo.netty|
// |000000c0| 2e 6d 65 73 73 61 67 65 2e 4d 65 73 73 61 67 65 |.message.Message|
// |000000d0| 3d 07 13 39 01 3c c0 92 02 00 01 49 00 0a 73 65 |=..9.<.....I..se|
// |000000e0| 71 75 65 6e 63 65 49 64 78 70 00 00 00 01 74 00 |quenceIdxp....t.|
// |000000f0| 0b 70 61 73 73 77 6f 72 64 31 32 33 74 00 14 32 |.password123t..2|
// |00000100| 30 32 33 2d 31 30 2d 30 31 54 31 32 3a 30 30 3a |023-10-01T12:00:|
// |00000110| 30 30 5a 74 00 06 e5 bc a0 e4 b8 89             |00Zt........    |
// +--------+-------------------------------------------------+----------------+
// 19:04:01 [DEBUG] [main] i.n.h.l.LoggingHandler - [id: 0xembedded, L:embedded - R:embedded] FLUSH
// 19:04:01 [DEBUG] [main] i.n.h.l.LoggingHandler - [id: 0xembedded, L:embedded - R:embedded] READ: 284B
//          +-------------------------------------------------+
//          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
// +--------+-------------------------------------------------+----------------+
// |00000000| 20 04 07 15 01 00 00 00 00 00 01 00 00 00 01 0c | ...............|
// |00000010| ac ed 00 05 73 72 00 3a 63 6f 6d 2e 65 78 61 6d |....sr.:com.exam|
// |00000020| 70 6c 65 2e 6e 65 74 74 79 69 6d 5f 64 65 6d 6f |ple.nettyim_demo|
// |00000030| 2e 6e 65 74 74 79 2e 6d 65 73 73 61 67 65 2e 4c |.netty.message.L|
// |00000040| 6f 67 69 6e 52 65 71 75 65 73 74 4d 65 73 73 61 |oginRequestMessa|
// |00000050| 67 65 45 44 57 a9 f6 2c 4b 5d 02 00 03 4c 00 08 |geEDW..,K]...L..|
// |00000060| 70 61 73 73 77 6f 72 64 74 00 12 4c 6a 61 76 61 |passwordt..Ljava|
// |00000070| 2f 6c 61 6e 67 2f 53 74 72 69 6e 67 3b 4c 00 09 |/lang/String;L..|
// |00000080| 74 69 6d 65 73 74 61 6d 70 71 00 7e 00 01 4c 00 |timestampq.~..L.|
// |00000090| 08 75 73 65 72 6e 61 6d 65 71 00 7e 00 01 78 72 |.usernameq.~..xr|
// |000000a0| 00 2e 63 6f 6d 2e 65 78 61 6d 70 6c 65 2e 6e 65 |..com.example.ne|
// |000000b0| 74 74 79 69 6d 5f 64 65 6d 6f 2e 6e 65 74 74 79 |ttyim_demo.netty|
// |000000c0| 2e 6d 65 73 73 61 67 65 2e 4d 65 73 73 61 67 65 |.message.Message|
// |000000d0| 3d 07 13 39 01 3c c0 92 02 00 01 49 00 0a 73 65 |=..9.<.....I..se|
// |000000e0| 71 75 65 6e 63 65 49 64 78 70 00 00 00 01 74 00 |quenceIdxp....t.|
// |000000f0| 0b 70 61 73 73 77 6f 72 64 31 32 33 74 00 14 32 |.password123t..2|
// |00000100| 30 32 33 2d 31 30 2d 30 31 54 31 32 3a 30 30 3a |023-10-01T12:00:|
// |00000110| 30 30 5a 74 00 06 e5 bc a0 e4 b8 89             |00Zt........    |
// +--------+-------------------------------------------------+----------------+
// 19:04:01 [DEBUG] [main] i.n.h.l.LoggingHandler - [id: 0xembedded, L:embedded - R:embedded] READ COMPLETE
// 原始消息: LoginRequestMessage{username='张三', password=***}
// 解码后消息: LoginRequestMessage{username='张三', password=***}