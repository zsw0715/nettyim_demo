package com.example.nettyim_demo.netty.message;

import java.util.HashMap;
import java.util.Map;

public class MessageTypeManager {
    private static final Map<Integer, Class<? extends Message>> messageTypeMap = new HashMap<>();

    static {
        messageTypeMap.put(MessageType.REGISTER_REQUEST_MESSAGE, RegisterRequestMessage.class);
        messageTypeMap.put(MessageType.REGISTER_RESPONSE_MESSAGE, RegisterResponseMessage.class);
        messageTypeMap.put(MessageType.LOGIN_REQUEST_MESSAGE, LoginRequestMessage.class);
        messageTypeMap.put(MessageType.LOGIN_RESPONSE_MESSAGE, LoginResponseMessage.class);
        messageTypeMap.put(MessageType.LOGOUT_REQUEST_MESSAGE, LogoutRequestMessage.class);
        messageTypeMap.put(MessageType.LOGOUT_RESPONSE_MESSAGE, LogoutResponseMessage.class);
        messageTypeMap.put(MessageType.CHAT_REQUEST_MESSAGE, ChatRequestMessage.class);
        messageTypeMap.put(MessageType.CHAT_RESPONSE_MESSAGE, ChatResponseMessage.class);
        messageTypeMap.put(MessageType.GROUP_CHAT_REQUEST_MESSAGE, GroupChatRequestMessage.class);
        messageTypeMap.put(MessageType.GROUP_CHAT_RESPONSE_MESSAGE, GroupChatResponseMessage.class);
        messageTypeMap.put(MessageType.GROUP_CREATE_REQUEST_MESSAGE, GroupCreateRequestMessage.class);
        messageTypeMap.put(MessageType.GROUP_CREATE_RESPONSE_MESSAGE, GroupCreateResponseMessage.class);
        messageTypeMap.put(MessageType.GROUP_JOIN_REQUEST_MESSAGE, GroupJoinRequestMessage.class);
        messageTypeMap.put(MessageType.GROUP_JOIN_RESPONSE_MESSAGE, GroupJoinResponseMessage.class);
        messageTypeMap.put(MessageType.GROUP_LEAVE_REQUEST_MESSAGE, GroupLeaveRequestMessage.class);
        messageTypeMap.put(MessageType.GROUP_LEAVE_RESPONSE_MESSAGE, GroupLeaveResponseMessage.class);
        messageTypeMap.put(MessageType.GROUP_MEMBER_LIST_REQUEST_MESSAGE, GroupMemberListRequestMessage.class);
        messageTypeMap.put(MessageType.GROUP_MEMBER_LIST_RESPONSE_MESSAGE, GroupMemberListResponseMessage.class);
        messageTypeMap.put(MessageType.HEARTBEAT_REQUEST_MESSAGE, HeartbeatRequestMessage.class);
        messageTypeMap.put(MessageType.HEARTBEAT_RESPONSE_MESSAGE, HeartbeatResponseMessage.class);
    }

    public static Class<? extends Message> getMessageClass(int messageType) {
        return messageTypeMap.get(messageType);
    }

}
