package com.future.module.agent.model.conversation;

import lombok.Data;

import java.util.List;

@Data
public class ConversationForm {


    /*会话ID*/
    private String conversation_id;

    /*智能体ID*/
    private String dialog_id;

    /*智能体api_token*/
    private String api_token;

    /**
     * 对话列表
     */
    private List<Message> messages;


    /*提示配置*/
    @Data
    public static class Message{
        /*内容*/
        private String   content;
        /*角色*/
        private String    role;
    }
}
