package com.future.module.agent.fallback;

import com.alibaba.fastjson.JSONObject;
import com.future.module.agent.OpenUserAgentApi;
import com.future.module.agent.utils.AgentApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
public class UserAgentApiFallback implements OpenUserAgentApi {


//    @Override
//    public Object register(String nickname, String password, String email) {
//        return null;
//    }

    @Override
    public AgentApiResult register(JSONObject jsonObject) {
        return null;
    }

    @Override
    public AgentApiResult emailIsExist(JSONObject jsonObject) {
        return null;
    }
}
