package com.future.module.agent.controller;

import com.alibaba.fastjson.JSONObject;
import com.future.common.base.ActionResult;
import com.future.module.agent.DocumentApi;
import com.future.module.agent.LlmApi;
import com.future.module.agent.OpenLlmApi;
import com.future.module.agent.model.knowledge.DocumentStatusRequest;
import com.future.module.agent.model.knowledge.KnowledgeForm;
import com.future.module.agent.utils.AgentApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 智能体助理
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2019年9月26日 上午9:18
 */
@Tag(name = "模型api", description = "LlmController")
@Slf4j
@RestController
@RequestMapping("/api/v1/llm")
public class LlmController implements LlmApi {

    @Autowired
    private OpenLlmApi openLlmApi;

    @Operation(summary = "获取系统可用的大模型供应商列表")
    @GetMapping("/factories")
    public ActionResult getFactories()  {
        AgentApiResult result = openLlmApi.getFactories();
        return AgentApiResult.cActionResult(result);
    }

    @Operation(summary = "获取用户拥有的llm")
    @GetMapping("/my_llms")
    public ActionResult getMyLlms()  {
        AgentApiResult result = openLlmApi.getMyLlms();
        return AgentApiResult.cActionResult(result);
    }

    @Operation(summary = "获取当前用户所有的llm")
    @GetMapping("/list")
    public ActionResult getLlmList()  {
        AgentApiResult result = openLlmApi.getLlmList();
        return AgentApiResult.cActionResult(result);
    }
}
