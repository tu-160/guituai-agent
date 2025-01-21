package com.future.module.agent;

import com.alibaba.fastjson.JSONObject;
import com.future.common.base.ActionResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * CanvasApi
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2021-03-24
 */
@FeignClient(name = "future-agent", path = "/api")
public interface CanvasApi {


    @PostMapping("/v1/canvas/set")
    ActionResult setCanvas(@RequestBody JSONObject jsonObject);

    @PostMapping("/v1/canvas/get/{canvas}")
    ActionResult getCanvas(@PathVariable(value = "canvas_id") String canvas_id);

    @PostMapping("/v1/canvas/rm")
    ActionResult rmCanvas(@RequestParam(value = "canvas_id") String Canvas_id);

    @PostMapping("/v1/canvas/list")
    ActionResult getCanvasList(@RequestBody JSONObject json);
}
