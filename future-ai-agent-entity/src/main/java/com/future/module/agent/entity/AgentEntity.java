package com.future.module.agent.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * agent类型
 *
 * @author Future Platform Group
 * @version v4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2024-11-6
 */
@Data
@TableName("f_agent")
public class AgentEntity {

    /**
     * ID
     */
    @TableField("id")
    private String id;

    /**
     * 对应id
     */
    @TableField("agent_id")
    private String agent_id;


    /**
     * 类型：1:dialog,2:canvas
     */
    @TableField("agent_type")
    private String agent_type;

    /**
     * apikey:一个agent对应一个api_token
     */
    @TableField("api_token")
    private String api_token;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

}
