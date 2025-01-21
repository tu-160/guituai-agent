package com.future.module.agent.fallback;

import com.future.module.agent.UserAgentApi;
import com.future.module.agent.entity.UserAgentEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 获取用户信息Api降级处理
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2021-03-24
 */
@Component
@Slf4j
public class UserAgentApiFallback implements UserAgentApi {


    @Override
    public Object register(String nickname, String password,String email) {
        return null;
    }
}
