package com.future.module.agent;

import com.alibaba.fastjson.JSONObject;
import com.future.common.base.ActionResult;
import com.future.module.agent.model.dialog.DialogForm;
import com.future.module.agent.utils.AgentApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 模型api
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2021-03-24
 */
@FeignClient(name = "llmApi", path = "/api", url = "${ai.agent.host.url}")
public interface OpenLlmApi {

    @GetMapping("/v1/llm/factories")
    AgentApiResult getFactories();

    @GetMapping("/v1/llm/my_llms")
    AgentApiResult getMyLlms();

    @GetMapping("/v1/llm/list")
    AgentApiResult getLlmList();
}
