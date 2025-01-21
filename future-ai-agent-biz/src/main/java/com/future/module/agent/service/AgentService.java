package com.future.module.agent.service;

import com.future.module.agent.entity.AgentEntity;

/**
 * 智能体类型信息
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2019年9月26日 上午9:18
 */
public interface AgentService {

    /**
     * 设置智能体类型
     * @param agent_id
     * @param type
     */
   void setAgentType(String agent_id,String type,String api_token);

    /**
     * 获取智能体类型
     * @param agent_id
     * @return
     */
   AgentEntity getAgentType(String agent_id);


}
