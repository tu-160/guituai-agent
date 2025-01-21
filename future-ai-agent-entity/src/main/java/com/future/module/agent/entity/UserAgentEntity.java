package com.future.module.agent.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户APIKEY信息
 *
 * @author Future Platform Group
 * @version v4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2024-11-6
 */
@Data
@TableName("f_user_agent")
public class UserAgentEntity<String> {

    /**
     * ID
     */
    @TableField("id")
    private String id;

    @TableField("account")
    private String account;

    /**
     * 账号
     */
    @TableField("user_id")
    private String userId;


    /**
     * 密码
     */
    @TableField("api_key")
    private String apiKey;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

}
