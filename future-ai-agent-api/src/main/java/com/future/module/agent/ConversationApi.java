package com.future.module.agent;

import com.future.common.base.ActionResult;
import com.future.module.agent.model.conversation.ChatResponse;
import com.future.module.agent.model.conversation.ConversationForm;
import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;


/**
 * 会话api
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2021-03-24
 */
@FeignClient(name = "future-agent", path = "/api")
public interface ConversationApi {

    /**
     * 对话
     * @return
     */
    @PostMapping("/v1/conversation/completion")
    Flux<String> completion(@RequestBody ConversationForm conversationForm);


    /**
     * 对话详情
     * @param conversation_id
     * @return
     */
    @PostMapping("/v1/conversation/get")
    ActionResult getConversation(@RequestParam(value = "dialog_id") String dialog_id,@RequestParam(value = "conversation_id") String conversation_id);

    /**
     * 新建对话
     * @param dialog_id
     * @return
     */
    @PostMapping("/v1/api/new_conversation")
    ActionResult newConversation(@RequestParam(value = "dialog_id") String dialog_id);

    /**
     * 对话列表
     * @param dialog_id
     * @return
     */
    @PostMapping("/v1/conversation/list")
    ActionResult listConversation(@RequestParam(value = "dialog_id") String dialog_id);

    /**
     * 对话删除
     * @param conversation_id
     * @return
     */
    @PostMapping("/v1/conversation/rm")
    ActionResult rmConversation(@RequestParam(value = "conversation_id") String conversation_id);
}
