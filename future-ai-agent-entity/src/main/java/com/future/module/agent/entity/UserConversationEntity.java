package com.future.module.agent.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户聊天会话信息
 *
 * @author Future Platform Group
 * @version v4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2024-11-6
 */
@Data
@TableName("f_agent_user_conversation")
public class UserConversationEntity {

    /**
     * ID
     */
    @TableField("id")
    private String id;

    /**
     * 账号
     */
    @TableField("user_id")
    private String userId;

    /**
     * 智能体ID
     */
    @TableField("dialog_id")
    private String dialogId;

    /**
     * 智能体使用状态
     */
    @TableField("dialog_status")
    private String dialogStatus;

    /**
     * 会话状态
     */
    @TableField("conversation_status")
    private String conversationStatus;

    /**
     * 会话名称
     */
    @TableField("conversation_name")
    private String conversation_name;

    /**
     * 会话名称
     */
    @TableField("conversation_message")
    private String conversation_message;

    @TableField("dialog_type")
    private String dialogType;

    /**
     * 会话ID
     */
    @TableField("conversation_id")
    private String conversation_id;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

}
