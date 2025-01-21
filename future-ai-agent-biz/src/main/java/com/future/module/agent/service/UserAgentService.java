package com.future.module.agent.service;

import com.future.base.service.SuperService;
import com.future.common.base.ActionResult;
import com.future.common.base.Pagination;
import com.future.module.agent.entity.UserAgentEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户信息
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2019年9月26日 上午9:18
 */
public interface UserAgentService extends SuperService<UserAgentEntity> {

    /**
     * 通过userid查询api_key
     *
     * @return
     */
    UserAgentEntity getApiKeyByUserId(String nickname);

    UserAgentEntity getApiKeyByAccount(String account);

    /**
     * 设置用户和agent  api_key绑定关系
     * @param userId
     * @param api_key
     * @return
     */
    void setApiKey(String userId, String api_key, String account);


}
