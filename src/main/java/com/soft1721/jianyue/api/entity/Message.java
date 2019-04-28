package com.soft1721.jianyue.api.entity;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * WebSocket 聊天消息类
 */
@AllArgsConstructor
@Data
public class Message {

    public static final String ENTER = "ENTER";
    public static final String SPEAK = "SPEAK";
    public static final String QUIT = "QUIT";

    private String type;

    private Integer userId;

    private String username; //发送人

    private String avatar;

    private String msg; //发送消息

    private int onlineCount; //在线用户数

    public static String jsonStr(String type,Integer userId, String username, String avatar,String msg, int onlineTotal) {
        return JSON.toJSONString(new Message(type,userId, username,avatar, msg, onlineTotal));
    }


}