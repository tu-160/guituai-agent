package com.future.module.agent;

import com.future.common.base.ActionResult;
import com.future.module.agent.model.knowledge.ConfigurationForm;
import com.future.module.agent.model.knowledge.KnowledgeForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * AI模型
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2021-03-24
 */
@FeignClient(name = "future-agent", path = "/api")
public interface LlmApi {

    /**
     * 获取系统可用的大模型供应商列表
     *
     * @return
     */
    @GetMapping("/v1/llm/factories")
    ActionResult getFactories();


    /*
     * 获取用户拥有的llm
     * @return
     * */
    @GetMapping("/v1/llm/my_llms")
    ActionResult getMyLlms();


    /*
     *获取当前用户所有的llm
     *@return
     **/
    @GetMapping("/v1/llm/list")
    ActionResult getLlmList();
}


