package com.future.module.agent;

import com.future.common.base.ActionResult;
import com.future.module.agent.model.knowledge.ConfigurationForm;
import com.future.module.agent.model.knowledge.KnowledgeForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 知识库Api
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2021-03-24
 */
        @FeignClient(name = "future-agent", path = "/api")
        public interface KnowledgeApi {

        /**
         * 获取知识库列表
         * @return
         */
        @GetMapping("/v1/kb/list")
        ActionResult getKbList(@RequestParam("page") Integer page, @RequestParam("page_size") Integer page_size, @RequestParam("orderby") String orderby, @RequestParam("desc") String desc, @RequestParam("keywords") String keywords);
        /**
         * 知识库删除
         * @return
         */
        @PostMapping("/v1/kb/rm")
        ActionResult rmKb(@RequestBody KnowledgeForm knowledgeForm);
        /**
         * 获取知识库详情
         * @return
         */
        @GetMapping("/v1/kb/detail")
        ActionResult detailKb(@RequestParam(value = "kb_id") String kb_id);
        /**
         * 知识库配置编辑
         * @return
         */
        @PostMapping("/v1/kb/update")
        ActionResult updateKb(@RequestBody ConfigurationForm configurationForm);
        /**
         * 新建知识库
         * @return
         */
        @PostMapping("/v1/kb/create")
        ActionResult createKb(@RequestBody KnowledgeForm knowledgeForm);
        }


