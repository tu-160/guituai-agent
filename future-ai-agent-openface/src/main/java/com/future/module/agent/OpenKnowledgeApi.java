package com.future.module.agent;

import com.alibaba.fastjson.JSONObject;
import com.future.module.agent.model.knowledge.ConfigurationForm;
import com.future.module.agent.model.knowledge.KnowledgeForm;
import com.future.module.agent.utils.AgentApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 会话api
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2021-03-24
 */
@FeignClient(name = "knowledgeApi", path = "/api", url = "${ai.agent.host.url}")
public interface OpenKnowledgeApi {
    @GetMapping("/v1/kb/list")
    AgentApiResult getKbList(@RequestParam("page") Integer page,@RequestParam("page_size") Integer page_size,@RequestParam("orderby") String orderby,@RequestParam("desc") String desc, @RequestParam("keywords") String keywords);
    @PostMapping("/v1/kb/rm")
    AgentApiResult rmKb(@RequestBody KnowledgeForm knowledgeForm);
    @GetMapping("/v1/kb/detail")
    AgentApiResult detailKb(@RequestParam("kb_id") String kb_id);
    @PostMapping ("/v1/kb/update")
    AgentApiResult updateKb(@RequestBody ConfigurationForm configurationForm);
    @PostMapping ("/v1/kb/create")
    AgentApiResult createKb(@RequestBody KnowledgeForm knowledgeForm);

}
