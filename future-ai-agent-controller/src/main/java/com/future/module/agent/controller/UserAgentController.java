package com.future.module.agent.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import com.future.common.base.ActionResult;
import com.future.common.base.UserInfo;
import com.future.common.util.JsonUtil;
import com.future.common.util.UserProvider;
import com.future.config.AgentConfig;
import com.future.module.agent.OpenPermissionFutureApi;
import com.future.module.agent.OpenUserAgentApi;
import com.future.module.agent.UserAgentApi;
import com.future.module.agent.entity.UserAgentEntity;
import com.future.module.agent.model.user.UserCrForm;
import com.future.module.agent.service.UserAgentService;
import com.future.module.agent.utils.AgentApiResult;
import com.future.module.oauth.model.LoginVO;
import com.future.module.oauth.oauth.AuthApi;
import com.future.permission.UserApi;
import com.future.permission.entity.UserEntity;
import com.future.reids.util.RedisUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2019年9月26日 上午9:18
 */
@Tag(name = "用户管理", description = "UserAgent")
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserAgentController  implements UserAgentApi {
    @Autowired
    private RedisUtil redisUtil;

    @Qualifier("com.future.module.agent.OpenUserAgentApi")
    @Autowired
    private OpenUserAgentApi openUserAgentApi;

    @Autowired
    private AuthApi authApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private AgentConfig agentConfig;

    @Autowired
    private UserAgentService userAgentService;

    @Autowired
    private OpenPermissionFutureApi openPermissionFutureApi;

    @PostMapping("/register")
    public ActionResult register(String nickname,String password,String email) {
        Boolean isexist = this.openPermissionFutureApi.getAccountIsExist(nickname);
        if(isexist) {
            return ActionResult.fail(200, "账号已存在");
        }
        // 同步agent用户
        log.info("同步agent用户的参数，nickname：{}，email：{}", nickname, email);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nickname",nickname);
        jsonObject.put("password",password);
        jsonObject.put("email",email);
        jsonObject.put("base_url", agentConfig.getReasoning_host());

        AgentApiResult.checkResult(openUserAgentApi.emailIsExist(jsonObject));

        UserCrForm userCrForm = new UserCrForm();
        userCrForm.setAccount(nickname);
        userCrForm.setGender("1");
        userCrForm.setRealName(nickname);
        // select * from base_organize t;
        userCrForm.setOrganizeId("96240625-934F-490B-8AA6-0BC775B18468");
        // 系统管理员角色
        userCrForm.setRoleId("d2b2418a2f664c61abf0053ff5d57783");
        userCrForm.setPassword(password);
        // 调整返回参数 TODO
        ActionResult<String> register = this.openPermissionFutureApi.register(userCrForm);
        if(register.getCode()!=200){
            return register;
        }

        AgentApiResult agentRegister = openUserAgentApi.register(jsonObject);
        AgentApiResult.checkResult(agentRegister);

        Map<String, Object> dataMap = JsonUtil.entityToMap(agentRegister);
        log.info("同步agent用户的响应：{}", dataMap);
        Object resData = dataMap.get("data");
        Map<String, Object> resMap = JsonUtil.entityToMap(resData);
        String api_key = resMap.get("api_token")+"";

        // 绑定api_key
        userAgentService.setApiKey(register.getData()+"", api_key, nickname);

        return register;
    }


    @PostMapping("/login")
    public ActionResult login(String nickname,String password) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("account",nickname);
        parameters.put("password",password);
        parameters.put("origin","password");
        parameters.put("grant_type","password");
        ActionResult login = authApi.login(parameters);
        if(login.getCode()!=200){
            return login;
        }
        Map<String, Object> dataMap = JsonUtil.entityToMap(login.getData());
        String token = dataMap.get("token")+"";
        // 获取登录用户信息
        UserInfo user = UserProvider.getUser(token);



        UserAgentEntity apiKeyByUserId = userAgentService.getApiKeyByUserId(user.getUserId());
        if(apiKeyByUserId==null){
            return ActionResult.fail("用户未绑定");
        }

        // 设置api_token
        redisUtil.insert("agent_"+user.getUserId(),apiKeyByUserId.getApiKey());


        return login;
    }


    @GetMapping(value = {"/infoGet"})
    public ActionResult infoGet(@RequestHeader(value = "Authorization") @NotBlank(message = "请求头Authorization为空") String authorization)
    {
        ActionResult actionResult = this.infoCheck(authorization);
        if (actionResult.getCode() != HttpStatus.HTTP_OK)
        {
            return actionResult;
        }
        UserInfo user = UserProvider.getUser(authorization.replace("bearer ", ""));
        return ActionResult.success(user);
    }

    @GetMapping(value = {"/infoCheck"})
    public ActionResult infoCheck(@RequestHeader(value = "Authorization") @NotBlank(message = "请求头Authorization为空") String authorization)
    {
        UserInfo user = UserProvider.getUser(authorization.replace("bearer ", ""));
        if (user == null || StringUtils.isBlank(user.getUserId()))
        {
            return ActionResult.fail("会话信息为空");
        }
        return ActionResult.success("校验通过");
    }

}
