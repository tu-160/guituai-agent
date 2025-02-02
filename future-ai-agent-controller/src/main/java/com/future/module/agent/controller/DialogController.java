package com.future.module.agent.controller;

import com.alibaba.fastjson.JSONObject;
import com.future.common.base.ActionResult;
import com.future.common.util.JsonUtil;
import com.future.common.util.StringUtil;
import com.future.common.util.UserProvider;
import com.future.module.agent.DialogApi;
import com.future.module.agent.OpenAppApi;
import com.future.module.agent.OpenCanvasApi;
import com.future.module.agent.OpenDialogApi;
import com.future.module.agent.entity.AgentEntity;
import com.future.module.agent.model.dialog.DialogForm;
import com.future.module.agent.service.AgentService;
import com.future.module.agent.service.ConversationService;
import com.future.module.agent.service.DialogService;
import com.future.module.agent.utils.AgentApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.bouncycastle.oer.its.ieee1609dot2.CertificateId.name;

/**
 * 智能体助理
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2019年9月26日 上午9:18
 */
@Tag(name = "智能体助理", description = "DialogController")
@Slf4j
@RestController
@RequestMapping("/api/v1/dialog")
public class DialogController implements DialogApi {

    @Autowired
    private OpenDialogApi openDialogApi;

    @Autowired
    private DialogService dialogService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private OpenAppApi openAppApi;

    @Autowired
    OpenCanvasApi apenCanvasApi;

    /**
     * 智能体新增、编辑
     * @param dialogForm
     * @return
     */
    @Operation(summary = "设置智能体")
    @PostMapping("/set")
    public ActionResult setDialog(@RequestBody DialogForm dialogForm) {
        AgentApiResult ap = openDialogApi.setDialog(dialogForm);
        AgentApiResult.checkResult(ap);
        Map<String, Object> dataMap = JsonUtil.entityToMap(ap.getData());
        String dialog_id = dataMap.get("id")+"";
        // 设置预设问题
        dialogService.setDialogQuestion(dialogForm.getDefault_question(),dialog_id);

        // 设置api_token
        AgentEntity agentType = agentService.getAgentType(dialog_id);
        if(agentType==null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dialog_id",dialog_id);
            jsonObject.put("canvas_id","");
            AgentApiResult agentApiResult = openAppApi.new_token(jsonObject);
            AgentApiResult.checkResult(agentApiResult);
            Map<String, Object> agentApiResultMap = JsonUtil.entityToMap(agentApiResult.getData());
            String api_token = agentApiResultMap.get("token")+"";
            agentService.setAgentType(dialog_id,"dialog",api_token);
        }

        return AgentApiResult.cActionResult(ap);
    }

    @Operation(summary = "智能体详情")
    @PostMapping("/get")
    public ActionResult getDialog(@RequestParam String dialog_id) {
        AgentApiResult dialog = openDialogApi.getDialog(dialog_id);
        // 预设问题
        List<String> agentMessage = conversationService.getAgentMessage(dialog_id);
        Map<String, Object> dataMap = JsonUtil.entityToMap(dialog.getData());
        dataMap.put("default_question",agentMessage);
        dialog.setData(dataMap);
        return AgentApiResult.cActionResult(dialog);
    }

    @Operation(summary = "智能体删除")
    @PostMapping("/rm")
    public ActionResult rmDialog(@RequestParam String dialog_id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dialog_ids", Arrays.asList(dialog_id));
        AgentApiResult dialog = openDialogApi.rmDialog(jsonObject);
        return AgentApiResult.cActionResult(dialog);
    }

    @Operation(summary = "创作中心-智能体列表")
    @PostMapping("/list")
    public ActionResult getDialogList(@RequestBody JSONObject jsonObject) {
//        JSONObject jsonObject = new JSONObject();

        AgentApiResult data = openDialogApi.getDialogList(jsonObject);
        Object pub = openDialogApi.getDialogPublicList().getData();

        AgentApiResult agent = apenCanvasApi.getCanvasList(jsonObject);
        if(data.getCode() == 0 && agent.getCode() == 0) {
            ((ArrayList) agent.getData()).forEach(map -> ((Map) map).put("name", ((Map) map).get("title")));
            ((ArrayList) data.getData()).addAll((ArrayList) agent.getData());
        }

        Map join = new HashMap();
        join.put("public_data", pub);
        join.put("private_data", data.getData());

        data.setData(join);
        return AgentApiResult.cActionResult(data);
    }


    @Operation(summary = "最近使用智能体列表")
    @PostMapping("/list/my")
    public ActionResult getMyDialogList() {

//        List<String> userConversationList = conversationService.getUserConversationList(UserProvider.getLoginUserId());
//        userConversationList = userConversationList.stream().distinct().collect(Collectors.toList());
//        List<Object> rList = new ArrayList<>();
//        for(String dialog_id : userConversationList){
//            AgentApiResult dialog = openDialogApi.getDialog(dialog_id);
//            AgentApiResult.checkResult(dialog);
//            if(dialog.getData() != null) {
//                rList.add(dialog.getData());
//            }
//        }
        JSONObject params = new JSONObject();
        AgentApiResult dialog = openDialogApi.getDialogList(params);

        AgentApiResult agent = apenCanvasApi.getCanvasList(params);
        if(dialog.getCode() == 0 && agent.getCode() == 0) {
            ((ArrayList) agent.getData()).forEach(map -> ((Map) map).put("name", ((Map) map).get("title")));
            ((ArrayList) dialog.getData()).addAll((ArrayList) agent.getData());
        }

        return ActionResult.success(dialog.getData());
    }

    @Operation(summary = "最近使用智能体删除")
    @PostMapping("/list/my/rm")
    public ActionResult rmMyDialog(String dialog_id) {
        conversationService.rmUserConversation(UserProvider.getLoginUserId(),dialog_id);
        return ActionResult.success("");
    }



    @Operation(summary = "智能体列表")
    @PostMapping("/list/public")
    public ActionResult getPublicDialogList(@RequestParam String searchSize) {
        AgentApiResult dialog = openDialogApi.getDialogPublicList();
        AgentApiResult.checkResult(dialog);

        JSONObject params = new JSONObject();
        params.put("canvas_type", "public");
        AgentApiResult agent = apenCanvasApi.getCanvasList(params);
        if(dialog.getCode() == 0 && agent.getCode() == 0) {
            ((ArrayList) agent.getData()).forEach(map -> ((Map) map).put("name", ((Map) map).get("title")));
            ((ArrayList) dialog.getData()).addAll((ArrayList) agent.getData());
        }

        if(StringUtil.isNotEmpty(searchSize)){
            List<Object> list = (List<Object>) dialog.getData();
            List<Object> result = list.stream().limit(Integer.valueOf(searchSize)).collect(Collectors.toList());
            dialog.setData(result);
        }
        return AgentApiResult.cActionResult(dialog);
    }

    @Operation(summary = "智能体列表")
    @PostMapping("/default_question")
    public ActionResult getDefaultQuestion(@RequestParam String dialog_id) {
        List<String> agentMessage = conversationService.getAgentMessage(dialog_id);
        return ActionResult.success(agentMessage);
    }


}
