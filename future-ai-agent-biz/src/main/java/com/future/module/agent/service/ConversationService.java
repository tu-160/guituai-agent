package com.future.module.agent.service;

import com.future.base.service.SuperService;
import com.future.module.agent.entity.UserConversationEntity;

import java.util.List;

/**
 * 会话信息
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2019年9月26日 上午9:18
 */
public interface ConversationService extends SuperService<UserConversationEntity> {

    /**
     * 获取会话
     * @param userId
     * @param dialog_id
     * @return
     */
    UserConversationEntity getConversationId(String userId, String dialog_id);

    /**
     * 创建会话
     * @param userId
     * @param dialog_id
     * @return
     */
    UserConversationEntity setConversation(String userId, String dialog_id,String conversation_id,String message, String dialogType);

    /**
     * 获取智能体预设问题
     * @param dialog_id
     * @return
     */
    List<String> getAgentMessage(String dialog_id);

    /**
     * 更新会话
     */
    void updateUserConversation(String user_id,String dialog_id,String conversation_name);

    /**
     * 获取已发生过对话的智能体列表IDs
     * @param user_id
     * @return
     */
    List<String> getUserConversationList(String user_id);

    /**
     * 删除我最近使用的智能体
     * @param user_id
     * @param dialog_id
     */
    void rmUserConversation(String user_id,String dialog_id);

    UserConversationEntity getUserConversation(String user_id, String dialog_id);

    List<UserConversationEntity> getUserConversationAll(String user_id, String dialog_id);
}
