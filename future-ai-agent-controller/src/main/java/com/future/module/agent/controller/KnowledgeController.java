package com.future.module.agent.controller;

import com.alibaba.fastjson.JSONObject;
import com.future.common.base.ActionResult;
import com.future.module.agent.KnowledgeApi;
import com.future.module.agent.OpenKnowledgeApi;
import com.future.module.agent.model.knowledge.ConfigurationForm;
import com.future.module.agent.model.knowledge.KnowledgeForm;
import com.future.module.agent.utils.AgentApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 知识库
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2019年9月26日 上午9:18
 */
@Tag(name = "知识库", description = "KnowledgeController")
@Slf4j
@RestController
@RequestMapping("/api/v1/kb")
public class KnowledgeController implements KnowledgeApi {

    @Autowired
    private OpenKnowledgeApi openKnowledgeApi;


    @Operation(summary = "知识库列表")
    @GetMapping("/list")
    public ActionResult getKbList(@RequestParam Integer page, @RequestParam Integer page_size, @RequestParam String orderby, @RequestParam String desc, @RequestParam String keywords)  {
        AgentApiResult result = openKnowledgeApi.getKbList(page, page_size, orderby, desc, keywords);
        return AgentApiResult.cActionResult(result);
    }
    @Operation(summary = "知识库删除")
    @PostMapping("/rm")
    public ActionResult rmKb(@RequestBody KnowledgeForm knowledgeForm)  {
        AgentApiResult kb = openKnowledgeApi.rmKb(knowledgeForm);
        return AgentApiResult.cActionResult(kb);
    }


    @Operation(summary = "获取知识库详情")
    @GetMapping("/detail")
    public ActionResult detailKb(@RequestParam String kb_id)  {
        AgentApiResult kb = openKnowledgeApi.detailKb(kb_id);
        return AgentApiResult.cActionResult(kb);
    }

    @Operation(summary = "知识库配置编辑")
    @PostMapping("/update")
    public ActionResult updateKb(@RequestBody ConfigurationForm configurationForm) {
        AgentApiResult kb = openKnowledgeApi.updateKb(configurationForm);
        AgentApiResult.checkResult(kb);
        return AgentApiResult.cActionResult(kb);
    }

    @Operation(summary = "知识库创建")
    @PostMapping("/create")
    public ActionResult createKb(@RequestBody KnowledgeForm knowledgeForm) {
        // kd_type(public或private，预留字段，暂时不用)
        knowledgeForm.setKb_type("private");
        knowledgeForm.setPagerank(0);
        knowledgeForm.setPermission("me");
        knowledgeForm.setLanguage("Chinese");
        knowledgeForm.setEmbd_id("bge-large-zh-v1.5@Xinference");
        knowledgeForm.setParser_id("naive");
        knowledgeForm.setPagerank(0);

        ConfigurationForm.parserConfig parserConfig = new ConfigurationForm.parserConfig();
        parserConfig.setAuto_keywords(0);
        parserConfig.setAuto_questions(0);
        parserConfig.setChunk_token_num(256);
        parserConfig.setDelimiter("\\n!?;。；！？");
        parserConfig.setHtml4excel(false);
        parserConfig.setLayout_recognize(true);

        ConfigurationForm.parserConfig.raptor raptor = new ConfigurationForm.parserConfig.raptor();
        raptor.setUse_raptor(false);
        raptor.setMax_cluster(64);
        raptor.setMax_token(256);
        raptor.setPrompt("请总结以下段落。 小心数字，不要编造。 段落如下：\n" +
                "      {cluster_content}\n" +
                "以上就是你需要总结的内容。");
        raptor.setRandom_seed(0);
        raptor.setThreshold(0.10);
        parserConfig.setRaptor(raptor);

        knowledgeForm.setParser_config(parserConfig);
        AgentApiResult kb = openKnowledgeApi.createKb(knowledgeForm);
        AgentApiResult.checkResult(kb);
        return AgentApiResult.cActionResult(kb);
    }
}
