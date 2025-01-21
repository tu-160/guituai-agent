package com.future.module.agent.mapper;

import com.future.base.mapper.SuperMapper;
import com.future.module.agent.entity.UserAgentEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户信息
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2019年9月26日 上午9:18
 */
public interface UserAgentMapper extends SuperMapper<UserAgentEntity> {
    /**
     * 获取用户id
     * @return
     */
    List<String> getListId();

}
