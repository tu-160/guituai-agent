package com.future.module.agent;

import com.alibaba.fastjson.JSONObject;
import com.future.module.agent.model.conversation.ChatResponse;
import com.future.module.agent.model.conversation.ConversationForm;
import com.future.module.agent.utils.AgentApiResult;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * agent - API
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2021-03-24
 */
@FeignClient(name = "OpenAppApi", path = "/api", url = "${ai.agent.host.url}")
public interface OpenAppApi {

    /**
     * 新建agent与token绑定
     * @return
     */
    @PostMapping("/v1/api/new_token")
    AgentApiResult new_token(@RequestBody JSONObject jsonObject);

    /**
     * 新建会话
     * @return
     */
    @PostMapping("/v1/api/new_conversation")
    AgentApiResult new_conversation(@RequestBody JSONObject jsonObject);

    /**
     * 获取对话历史
     * @return
     */
    @GetMapping("/v1/api/conversation/{conversation_id}")
    AgentApiResult getConversation(@PathVariable("conversation_id") String conversation_id, @RequestParam(value = "api_token") String api_token);



}
