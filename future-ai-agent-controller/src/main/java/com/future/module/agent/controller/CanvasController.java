package com.future.module.agent.controller;

import com.alibaba.fastjson.JSONObject;
import com.future.common.base.ActionResult;
import com.future.common.util.JsonUtil;
import com.future.module.agent.CanvasApi;
import com.future.module.agent.OpenAppApi;
import com.future.module.agent.OpenCanvasApi;
import com.future.module.agent.entity.AgentEntity;
import com.future.module.agent.service.AgentService;
import com.future.module.agent.utils.AgentApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 高级智能体助理
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2019年9月26日 上午9:18
 */
@Tag(name = "高级智能体助理", description = "CanvasController")
@Slf4j
@RestController
@RequestMapping("/api/v1/canvas")
public class CanvasController implements CanvasApi {

    @Autowired
    private OpenCanvasApi openCanvasApi;

    @Autowired
    private OpenAppApi openAppApi;

    @Autowired
    private AgentService agentService;


    /**
     * 智能体新增、编辑
     * @return
     */
    @Operation(summary = "设置智能体")
    @PostMapping("/set")
    @Override
    public ActionResult setCanvas(@RequestBody JSONObject jsonObject) {
        AgentApiResult ap = openCanvasApi.setCanvas(jsonObject);
        AgentApiResult.checkResult(ap);
        Map<String, Object> dataMap = JsonUtil.entityToMap(ap.getData());
        String canvas_id = dataMap.get("id")+"";

        // 设置api_token
        AgentEntity agentType = agentService.getAgentType(canvas_id);
        if(agentType==null){
            JSONObject jo = new JSONObject();
            jo.put("dialog_id","");
            jo.put("canvas_id",canvas_id);
            AgentApiResult agentApiResult = openAppApi.new_token(jo);
            AgentApiResult.checkResult(agentApiResult);
            Map<String, Object> agentApiResultMap = JsonUtil.entityToMap(agentApiResult.getData());
            String api_token = agentApiResultMap.get("token")+"";
            agentService.setAgentType(canvas_id,"canvas",api_token);
        }
        return AgentApiResult.cActionResult(ap);
    }

    /**
     * 智能体新增、编辑
     * @return
     */
    @Operation(summary = "设置智能体")
    @PostMapping("/get/{canvas_id}")
    @Override
    public ActionResult getCanvas(@PathVariable(value = "canvas_id") String canvas_id) {
        AgentApiResult ap = openCanvasApi.getCanvas(canvas_id);
        return AgentApiResult.cActionResult(ap);
    }

    @Override
    @Operation(summary = "删除智能体")
    @PostMapping("/rm")
    public ActionResult rmCanvas(@RequestParam  String canvas_id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("canvas_ids", Arrays.asList(canvas_id));
        AgentApiResult ap = openCanvasApi.rmCanvas(jsonObject);
        return AgentApiResult.cActionResult(ap);
    }

    @Override
    @Operation(summary = "智能体列表")
    @PostMapping("/list")
    public ActionResult getCanvasList(@RequestBody JSONObject json) {
//        JSONObject json = new JSONObject();
        json.put("canvas_type", "");
        AgentApiResult ap = openCanvasApi.getCanvasList(json);
        return AgentApiResult.cActionResult(ap);
    }
}
