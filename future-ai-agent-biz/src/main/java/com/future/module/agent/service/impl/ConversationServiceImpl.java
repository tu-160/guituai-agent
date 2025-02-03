package com.future.module.agent.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.future.base.service.SuperServiceImpl;
import com.future.common.constant.MsgCode;
import com.future.common.exception.DataException;
import com.future.module.agent.entity.AgentMessageEntity;
import com.future.module.agent.entity.UserConversationEntity;
import com.future.module.agent.mapper.AgentMessageMapper;
import com.future.module.agent.mapper.UserConversationMapper;
import com.future.module.agent.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 对话信息
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2019年9月26日 上午9:18
 */
@Service
public class ConversationServiceImpl extends SuperServiceImpl<UserConversationMapper, UserConversationEntity> implements ConversationService {

    @Autowired
    private AgentMessageMapper agentMessageMapper;

    @Override
    public UserConversationEntity getConversationId(String userId, String dialog_id) {
        //判断是否有未使用的会话ID
        QueryWrapper<UserConversationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserConversationEntity::getUserId, userId)
                .eq(UserConversationEntity::getDialogId, dialog_id)
                .eq(UserConversationEntity::getConversationStatus,'0');
        UserConversationEntity uc = this.getOne(queryWrapper);
        if(uc != null){
            return uc;
        }
        return null;
    }

    @Override
    public UserConversationEntity setConversation(String userId, String dialog_id,String conversation_id,String message, String dialogType) {
        UserConversationEntity uc = new UserConversationEntity();
        uc.setId(IdWorker.getIdStr());
        uc.setConversationStatus("0");
        uc.setCreateTime(new Date());
        uc.setDialogStatus("0");
        uc.setUserId(userId);
        uc.setDialogId(dialog_id);
        uc.setConversation_id(conversation_id);
        uc.setConversation_name("新会话");
        uc.setConversation_message(message);
        uc.setDialogType(dialogType);
        boolean save = this.save(uc);
        if(!save){
            throw new DataException(MsgCode.FA028.get());
        }
        return uc;
    }

    @Override
    public List<String> getAgentMessage(String dialog_id) {
        LambdaQueryWrapper<AgentMessageEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(AgentMessageEntity::getDialogId,dialog_id);
        List<AgentMessageEntity> agentMessageEntities = agentMessageMapper.selectList(lambdaQueryWrapper);
        return agentMessageEntities.stream().map(AgentMessageEntity::getMessage).collect(Collectors.toList());
    }

    @Override
    public void updateUserConversation(String user_id, String dialog_id,String conversation_name) {
        UserConversationEntity uc = getUserConversation(user_id, dialog_id);
        if(uc!=null){
            uc.setConversationStatus("1");
            uc.setDialogStatus("1");
            uc.setConversation_name(conversation_name);
            boolean b = this.updateById(uc);
            if(!b){
                throw new DataException(MsgCode.FA028.get());
            }
        }
    }

    @Override
    public List<String> getUserConversationList(String user_id) {
        QueryWrapper<UserConversationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserConversationEntity::getUserId, user_id)
                .eq(UserConversationEntity::getConversationStatus, "1");
        List<UserConversationEntity> list = this.list(queryWrapper);
        return list.stream().map(UserConversationEntity::getDialogId).collect(Collectors.toList());
    }

    @Override
    public void rmUserConversation(String user_id, String dialog_id) {
        QueryWrapper<UserConversationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserConversationEntity::getUserId, user_id)
                .eq(UserConversationEntity::getDialogId, dialog_id);
        boolean remove = this.remove(queryWrapper);
        if(!remove){
//            throw new DataException(MsgCode.FA003.get());
        }
    }

    @Override
    public UserConversationEntity getUserConversation(String user_id, String dialog_id) {
        QueryWrapper<UserConversationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserConversationEntity::getUserId, user_id)
                .eq(UserConversationEntity::getDialogId, dialog_id)
                .eq(UserConversationEntity::getConversationStatus, "0");
        UserConversationEntity uc = this.getOne(queryWrapper);
        return uc;
    }

    @Override
    public List<UserConversationEntity> getUserConversationAll(String user_id, String dialog_id) {
        QueryWrapper<UserConversationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserConversationEntity::getUserId, user_id)
                .eq(UserConversationEntity::getDialogId, dialog_id);
        return this.list(queryWrapper);
    }
}
