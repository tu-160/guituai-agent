package com.future.module.agent.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.future.common.constant.MsgCode;
import com.future.common.exception.DataException;
import com.future.module.agent.entity.AgentMessageEntity;
import com.future.module.agent.mapper.AgentMessageMapper;
import com.future.module.agent.service.DialogService;
import com.future.module.agent.vo.DialogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户信息
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2019年9月26日 上午9:18
 */
@Service
public class DialogServiceImpl implements DialogService {

    @Autowired
    private AgentMessageMapper agentMessageMapper;

    @Override
    public DialogVo getDialogList(String userId) {

        return null;
    }

    @Override
    public void setDialogQuestion(List<String> question, String dialog_id) {
        // 先删除
        LambdaQueryWrapper<AgentMessageEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(AgentMessageEntity::getDialogId,dialog_id);
        agentMessageMapper.delete(lambdaQueryWrapper);
        for(String message : question){
            AgentMessageEntity am = new AgentMessageEntity();
            am.setDialogId(dialog_id);
            am.setMessage(message);
            am.setId(IdWorker.getIdStr());
            int insert = agentMessageMapper.insert(am);
            if(insert <= 0){
                throw new DataException(MsgCode.FA028.get());
            }
        }
    }
}
