package com.future.module.agent.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 智能体预设问题
 *
 * @author Future Platform Group
 * @version v4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2024-11-6
 */
@Data
@TableName("f_agent_message")
public class AgentMessageEntity {

    /**
     * ID
     */
    @TableField("id")
    private String id;

    /**
     * 预设问题
     */
    @TableField("message")
    private String message;

    /**
     * 智能体ID
     */
    @TableField("dialog_id")
    private String dialogId;



}
