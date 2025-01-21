package com.future.module.agent.vo;

import lombok.Data;

/**
 * ConversationVo
 * @版本： V4.0.0
 * @版权： 直方信息科技有限公司
 * @作者： Future开发平台组
 * @日期： 2024-09-27
 */
@Data
public class ConversationVo {

    /*名称*/
    private String name;
    /*智能体ID*/
    private String dialog_id;
    /*会话ID*/
    private String id;

    /*提示配置*/
    @Data
    public static class Message{
        /*内容*/
        private String   content;
        /*角色*/
        private String    role;
        /*id*/
        private String    id;
    }


}
