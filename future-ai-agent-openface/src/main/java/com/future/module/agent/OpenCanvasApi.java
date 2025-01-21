package com.future.module.agent;

import com.alibaba.fastjson.JSONObject;
import com.future.module.agent.utils.AgentApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * 会话api
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2021-03-24
 */
@FeignClient(name = "openCanvasApi", path = "/api", url = "${ai.agent.host.url}")
public interface OpenCanvasApi {


    @PostMapping("/v1/canvas/set")
    AgentApiResult setCanvas(@RequestBody JSONObject jsonObject);

    @GetMapping("/v1/canvas/get/{canvas_id}")
    AgentApiResult getCanvas(@PathVariable(value = "canvas_id") String canvas_id);

    @PostMapping("/v1/canvas/rm")
    AgentApiResult rmCanvas(@RequestBody JSONObject jsonObject);

    @PostMapping("/v1/canvas/list")
    AgentApiResult getCanvasList(@RequestBody JSONObject jsonObject);


}
