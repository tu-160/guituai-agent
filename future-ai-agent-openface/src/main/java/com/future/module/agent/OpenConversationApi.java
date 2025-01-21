package com.future.module.agent;

import com.alibaba.fastjson.JSONObject;
import com.future.module.agent.model.conversation.ChatResponse;
import com.future.module.agent.model.conversation.ConversationForm;
import com.future.module.agent.utils.AgentApiResult;
import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.InputStream;


/**
 * 会话api
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2021-03-24
 */
@FeignClient(name = "conversationApi", path = "/api", url = "${ai.agent.host.url}")
public interface OpenConversationApi {

    /**
     * 对话
     * @return
     */
    @PostMapping(value = "/v1/conversation/completion",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    ResponseEntity<ChatResponse> completion(@RequestBody ConversationForm conversationForm);


    /**
     * 对话详情
     * @param conversation_id
     * @return
     */
    @GetMapping("/v1/conversation/get")
    AgentApiResult getConversation(@RequestParam(value = "conversation_id") String conversation_id);

    /**
     * 新建对话
     * @param
     * @return
     */
    @GetMapping("/v1/conversation/set")
    AgentApiResult newConversation(@RequestBody JSONObject jsonObject);

    /**
     * 对话列表
     * @param dialog_id
     * @return
     */
    @GetMapping("/v1/conversation/list")
    AgentApiResult listConversation(@RequestParam(value = "dialog_id") String dialog_id);

    /**
     * 对话删除
     * @param
     * @return
     */
    @PostMapping("/v1/conversation/rm")
    AgentApiResult rmConversation(@RequestBody JSONObject jsonObject);
}
