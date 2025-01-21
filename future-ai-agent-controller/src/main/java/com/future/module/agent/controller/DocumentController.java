package com.future.module.agent.controller;

import com.alibaba.fastjson.JSONObject;
import com.future.common.base.ActionResult;
import com.future.module.agent.DocumentApi;
import com.future.module.agent.OpenDocumentApi;
import com.future.module.agent.model.knowledge.DocumentStatusRequest;
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
@Tag(name = "数据集管理", description = "DocumentController")
@Slf4j
@RestController
@RequestMapping("/api/v1/document")
public class DocumentController implements DocumentApi {

    @Autowired
    private OpenDocumentApi openDocumentApi;

    @Operation(summary = "数据集列表")
    @GetMapping("/list")
    public ActionResult getDocumentList(@RequestParam String kb_id, @RequestParam Integer page, @RequestParam Integer page_size, @RequestParam String orderby, @RequestParam String desc)  {
        AgentApiResult result = openDocumentApi.getDocumentList(kb_id,page, page_size, orderby, desc);
        return AgentApiResult.cActionResult(result);
    }

    @Operation(summary = "数据集启动停用")
    @PostMapping("/change_status")
    public ActionResult changeStatus(@RequestBody DocumentStatusRequest documentStatusRequest)  {
        AgentApiResult result = openDocumentApi.changeStatus(documentStatusRequest);
        return AgentApiResult.cActionResult(result);
    }

    @Operation(summary = "数据集删除")
    @PostMapping("/rm")
    public ActionResult documentRm(@RequestBody JSONObject request)  {
        AgentApiResult result = openDocumentApi.documentRm(request);
        return AgentApiResult.cActionResult(result);
    }

    @Operation(summary = "解析数据集")
    @PostMapping("/run")
    public ActionResult documentRun(@RequestBody JSONObject request)  {
        AgentApiResult result = openDocumentApi.documentRun(request);
        return AgentApiResult.cActionResult(result);
    }
    @Operation(summary = "数据集重命名")
    @PostMapping("/rename")
    public ActionResult documentRename(@RequestBody JSONObject request)  {
        AgentApiResult result = openDocumentApi.documentRename(request);
        return AgentApiResult.cActionResult(result);
    }

    @Operation(summary = "解析方法编辑")
    @PostMapping("/change_parser")
    public ActionResult documentChangeparser(@RequestBody JSONObject request)  {
        AgentApiResult result = openDocumentApi.documentChangeparser(request);
        return AgentApiResult.cActionResult(result);
    }

    @Operation(summary = "新增文件")
    @PostMapping("/upload")
    public ActionResult documentUpload(@RequestPart("kb_id") String kb_id ,@RequestPart("file") MultipartFile[] files)  {
        AgentApiResult result = openDocumentApi.documentUpload(kb_id,files);
        return AgentApiResult.cActionResult(result);
    }

    @Operation(summary = "下载文件")
    @GetMapping("/get/{doc_id}")
    public ResponseEntity<Resource> docDownloadFile(@PathVariable String doc_id)  {
        ResponseEntity<Resource> response = openDocumentApi.downloadFile(doc_id);
        return ResponseEntity.ok().body(response.getBody());
    }
}
