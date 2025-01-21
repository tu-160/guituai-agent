package com.future.module.agent.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.future.base.service.SuperServiceImpl;
import com.future.common.constant.MsgCode;
import com.future.common.exception.DataException;
import com.future.module.agent.entity.UserAgentEntity;
import com.future.module.agent.mapper.UserAgentMapper;
import com.future.module.agent.service.UserAgentService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserAgentServiceImpl extends SuperServiceImpl<UserAgentMapper, UserAgentEntity> implements UserAgentService {

    @Autowired
    private UserAgentMapper userAentMapper;


    public UserAgentEntity getApiKeyByAccount(String account) {
        QueryWrapper<UserAgentEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserAgentEntity::getAccount, account);
        return this.getOne(queryWrapper);
    }

    @Override
    public UserAgentEntity getApiKeyByUserId(String userId){
        QueryWrapper<UserAgentEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserAgentEntity::getUserId, userId);
        return this.getOne(queryWrapper);
    }

    @Override
    public void setApiKey(String userId, String api_key, String account) {
        UserAgentEntity ue = new UserAgentEntity();

        ue.setId(IdWorker.getIdStr());
        ue.setCreateTime(new Date());
        ue.setApiKey(api_key);
        ue.setUserId(userId);
        ue.setAccount(account);

        if(!this.save(ue)){
            throw new DataException(MsgCode.FA028.get());
        }
    }


}
