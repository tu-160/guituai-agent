package com.future.module.agent.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.future.base.service.SuperServiceImpl;
import com.future.common.constant.MsgCode;
import com.future.common.exception.DataException;
import com.future.module.agent.entity.AgentEntity;
import com.future.module.agent.mapper.AgentMapper;
import com.future.module.agent.service.AgentService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户信息
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2019年9月26日 上午9:18
 */
@Service
public class AgentServiceImpl extends SuperServiceImpl<AgentMapper, AgentEntity>  implements AgentService {


    @Override
    public void setAgentType(String agent_id, String type,String api_token) {
        AgentEntity agentEntity = new AgentEntity();
        agentEntity.setAgent_id(agent_id);
        agentEntity.setAgent_type(type);
        agentEntity.setApi_token(api_token);
        agentEntity.setId(IdWorker.getIdStr());
        agentEntity.setCreateTime(new Date());
        boolean save = this.save(agentEntity);
        if(!save){
            throw new DataException(MsgCode.FA028.get());
        }
    }

    @Override
    public AgentEntity getAgentType(String agent_id) {
        QueryWrapper<AgentEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgentEntity::getAgent_id, agent_id);
        AgentEntity agentEntity = this.getOne(queryWrapper);
//        if(agentEntity == null){
//            throw new DataException("找不到智能体类型");
//        }
        return agentEntity;
    }
}
