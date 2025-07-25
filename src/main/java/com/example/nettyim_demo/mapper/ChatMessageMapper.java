package com.example.nettyim_demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.nettyim_demo.entity.ChatMessage;

@Mapper
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {

    /**
     * 根据发送者和接收者查询聊天记录
     * 
     * @param senderId   发送者ID
     * @param receiverId 接收者ID
     * @return 聊天记录列表
     */
    @Select("SELECT * FROM chat_message WHERE sender = #{sender} AND receiver = #{receiver} ORDER BY send_time ASC")
    List<ChatMessage> findBySenderAndReceiver(String sender, String receiver);

    /**
     * 查询两个用户之间的聊天记录，包括双向聊天，不管谁是发送者或接收者
     * 
     * @param userId1 用户1 ID
     * @param userId2 用户2 ID
     * @return 聊天记录列表
     */
    @Select("""
                SELECT * FROM chat_message
                WHERE (sender = #{user1} AND receiver = #{user2})
                   OR (sender = #{user2} AND receiver = #{user1})
                ORDER BY send_time ASC
            """)
    List<ChatMessage> findConversationBetween(String user1, String user2);

    /**
     * 清空聊天记录
     * 
     * @return 删除的记录数
     */
    @Delete("DELETE FROM chat_message")
    int deleteAll();

}
