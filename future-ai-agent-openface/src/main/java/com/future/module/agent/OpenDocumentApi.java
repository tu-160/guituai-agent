package com.future.module.agent;

import com.alibaba.fastjson.JSONObject;
import com.future.common.base.ActionResult;
import com.future.module.agent.model.dialog.DialogForm;
import com.future.module.agent.model.knowledge.DocumentStatusRequest;
import com.future.module.agent.utils.AgentApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文档api
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2021-03-24
 */
@FeignClient(name = "documentApi", path = "/api", url = "${ai.agent.host.url}")
public interface OpenDocumentApi {

    @GetMapping("/v1/document/list")
    AgentApiResult getDocumentList(@RequestParam("kb_id") String kb_id, @RequestParam("page") Integer page, @RequestParam("page_size") Integer page_size, @RequestParam("orderby") String orderby, @RequestParam("desc") String desc);


    @PostMapping("/v1/document/change_status")
    AgentApiResult changeStatus(@RequestBody DocumentStatusRequest documentStatusRequest);

    @PostMapping("/v1/document/rm")
    AgentApiResult documentRm(@RequestBody JSONObject jsonObject);

    @PostMapping("/v1/document/run")
    AgentApiResult documentRun(@RequestBody JSONObject jsonObject);

    @PostMapping("/v1/document/rename")
    AgentApiResult documentRename(@RequestBody JSONObject jsonObject);

    @PostMapping("/v1/document/change_parser")
    AgentApiResult documentChangeparser(@RequestBody JSONObject request);

    @PostMapping(value = "/v1/document/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    AgentApiResult documentUpload(@RequestPart("kb_id") String kb_id ,@RequestPart("file") MultipartFile[] files);

    @GetMapping("/v1/document/get/{doc_id}")
    ResponseEntity<Resource> downloadFile(@PathVariable("doc_id") String doc_id);
}
