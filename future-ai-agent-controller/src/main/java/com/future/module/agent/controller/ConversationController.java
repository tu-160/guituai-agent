package com.future.module.agent.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.future.common.base.ActionResult;
import com.future.common.exception.DataException;
import com.future.common.util.JsonUtil;
import com.future.common.util.StringUtil;
import com.future.common.util.UserProvider;
import com.future.module.agent.ConversationApi;
import com.future.module.agent.OpenAppApi;
import com.future.module.agent.OpenConversationApi;
import com.future.module.agent.OpenDialogApi;
import com.future.module.agent.entity.AgentEntity;
import com.future.module.agent.entity.UserConversationEntity;
import com.future.module.agent.model.conversation.ConversationForm;
import com.future.module.agent.service.AgentService;
import com.future.module.agent.service.ConversationService;
import com.future.module.agent.utils.AgentApiResult;
import com.future.reids.util.RedisUtil;
import io.netty.channel.ChannelOption;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 对话管理
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2019年9月26日 上午9:18
 */
@Tag(name = "对话管理", description = "conversationController")
@Slf4j
@RestController
@RequestMapping("api/v1/conversation")
public class ConversationController implements ConversationApi {

    @Autowired
    private OpenConversationApi openConversationApi;


    @Autowired
    private ConversationService conversationService;

    @Autowired
    private OpenDialogApi openDialogApi;

    @Autowired
    private AgentService agentService;


    @Autowired
    private OpenAppApi openAppApi;

    @Value("${ai.agent.host.url}")
    private String aiAgentHostUrl;

    private static RedisUtil redisUtil;

    public ConversationController(RedisUtil redisUtil ) {
        ConversationController.redisUtil = redisUtil;
    }

    @Operation(summary = "对话")
    @PostMapping(value="/completion",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    @Queued
    public Flux<String> completion(@RequestBody ConversationForm conversationForm) {
        System.out.println("执行对话");
        AgentEntity agentE = agentService.getAgentType(conversationForm.getDialog_id());
        if(agentE==null){
            throw new DataException("找不到智能体");
        }
        conversationForm.setApi_token(agentE.getApi_token());
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 6000*10); // 连接超时

        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(aiAgentHostUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();

        String redisKey = "agent_"+UserProvider.getLoginUserId();
        Object token = redisUtil.getString(redisKey);

        Flux<String> sseFlux = webClient.post()
                .uri("/api/v1/api/completion")
                .bodyValue(conversationForm) // 设置请求体为JSON对象
                .header("Authorization","barer "+token)
                .header("Accept","text/event-stream")
                .retrieve()
                .bodyToFlux(String.class);

        UserConversationEntity userConversation = conversationService.getUserConversation(UserProvider.getLoginUserId(), conversationForm.getDialog_id());
        if(userConversation!=null){
            String name = null;
            List<ConversationForm.Message> messagesList = conversationForm.getMessages();
            for(ConversationForm.Message message : messagesList){
                if("user".equals(message.getRole())){
                    name = message.getContent();
                }
            }
//            if(StringUtil.isNotEmpty(name)){
//                // 更新会话名称
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("dialog_id",conversationForm.getDialog_id());
//                jsonObject.put("is_new",false);
//                jsonObject.put("conversation_id",conversationForm.getConversation_id());
//                jsonObject.put("name",name);
//                AgentApiResult agentApiResult = openConversationApi.newConversation(jsonObject);
//                AgentApiResult.checkResult(agentApiResult);
//            }
//            List<ConversationForm.Message> collect = conversationForm.getMessages().stream().filter(entity -> entity.getRole() == "user").collect(Collectors.toList());
//            if(collect.size()==0){
//                throw new RuntimeException("没有输入问题");
//            }
            // 增加会话关系绑定
            conversationService.updateUserConversation(UserProvider.getLoginUserId(),conversationForm.getDialog_id(),name);
        }



        return sseFlux;
    }

    @Operation(summary = "新建对话")
    @PostMapping("/new")
    public ActionResult newConversation(@RequestParam String dialog_id) {
//        AgentApiResult dialog = openDialogApi.getDialog(dialog_id);
//        AgentApiResult.checkResult(dialog);
//        Map<String, Object> dataMap = JsonUtil.entityToMap(dialog.getData());
//        String dialog_detail_id = dataMap.get("id")+"";
        String userId = UserProvider.getUser().getUserId();
        // 会话ID
        UserConversationEntity userConversationEntity = conversationService.getConversationId(userId, dialog_id);
        if(userConversationEntity==null){
            // 判断智能体类型
            AgentEntity agentEntity = agentService.getAgentType(dialog_id);
            if(agentEntity == null){
                throw new DataException("找不到智能体类型");
            }
            JSONObject newConversationJson = new JSONObject();
            newConversationJson.put("api_token",agentEntity.getApi_token());
            AgentApiResult agentApiResult = openAppApi.new_conversation(newConversationJson);
            AgentApiResult.checkResult(agentApiResult);
            Map<String, Object> newConvMap = JsonUtil.entityToMap(agentApiResult.getData());
            String conversationId = newConvMap.get("id")+"";
            String message = newConvMap.get("message")+"";
            userConversationEntity = conversationService.setConversation(userId, dialog_id, conversationId, message, agentEntity.getAgent_type());
        }

//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("dialog_id",dialog_detail_id);
//        jsonObject.put("is_new",is_new);
//        jsonObject.put("conversation_id",conversationId);
//        if(is_new){
//            jsonObject.put("name","新会话");
//        }
//        AgentApiResult agentApiResult = openConversationApi.newConversation(jsonObject);
//        AgentApiResult.checkResult(agentApiResult);
//        Map<String, Object> agentApiResultMap = JsonUtil.entityToMap(agentApiResult.getData());
//        String message = agentApiResultMap.get("message")+"";
//        String name = agentApiResultMap.get("name")+"";

        // 预设问题
        List<String> agentMessage = conversationService.getAgentMessage(dialog_id);
        Map<String,Object> rMap = new HashMap<>();
        rMap.put("conversation_id",userConversationEntity.getConversation_id());
        rMap.put("default_question",agentMessage);
        rMap.put("message",userConversationEntity.getConversation_message());
        rMap.put("name",userConversationEntity.getConversation_name());
        return ActionResult.success(rMap);
    }

    @Operation(summary = "对话详情")
    @PostMapping("/get")
    public ActionResult getConversation(@RequestParam String dialog_id,@RequestParam String conversation_id) {
        AgentEntity agentE = agentService.getAgentType(dialog_id);
        if(agentE==null){
            return ActionResult.fail("找不到智能体");
        }
        AgentApiResult conversation = openAppApi.getConversation(conversation_id, agentE.getApi_token());
//        AgentApiResult agentApiResult = openConversationApi.getConversation(conversation_id);
        return AgentApiResult.cActionResult(conversation);
    }

    @Operation(summary = "历史对话列表")
    @PostMapping("/list")
    public ActionResult listConversation(@RequestParam String dialog_id) {
        List<UserConversationEntity> userConversationAll = conversationService.getUserConversationAll(UserProvider.getLoginUserId(), dialog_id);
//        AgentApiResult agentApiResult = openConversationApi.listConversation(dialog_id);
        return ActionResult.success(userConversationAll);
    }

    @Operation(summary = "历史对话列表")
    @PostMapping("/sessions")
    public ActionResult getAgentSessions(@RequestParam String dialog_id, @RequestParam String conversation_id) {
        AgentApiResult userConversationAll = openAppApi.getAgentSessions(dialog_id, conversation_id);
        if(userConversationAll.getCode() == 0) {
            ArrayList arr = (ArrayList)userConversationAll.getData();
            for(int i=0; i < arr.size(); i++) {
               LinkedHashMap link = (LinkedHashMap) arr.get(i);
               LinkedHashMap message = (LinkedHashMap) ((ArrayList)link.get("message")).get(((ArrayList)link.get("message")).size() - 1);
               link.put("name", message.get("content"));
            }
        }
        return ActionResult.success(userConversationAll);
    }

    @Operation(summary = "对话删除")
    @PostMapping("/rm/session")
    public ActionResult rmSession(@RequestParam String[] ids) {
        JSONObject params = new JSONObject();
        params.put("ids", ids);
        AgentApiResult agentApiResult = openAppApi.rmSessions(params);
        return AgentApiResult.cActionResult(agentApiResult);
    }

    @Operation(summary = "对话删除")
    @PostMapping("/rm")
    public ActionResult rmConversation(@RequestParam String conversation_id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("conversation_ids", Arrays.asList(conversation_id));
        AgentApiResult agentApiResult = openConversationApi.rmConversation(jsonObject);
        return AgentApiResult.cActionResult(agentApiResult);
    }


}
