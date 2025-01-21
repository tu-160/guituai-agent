package com.future.module.agent;

import com.alibaba.fastjson.JSONObject;
import com.future.common.base.ActionResult;
import com.future.module.agent.model.knowledge.DocumentStatusRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 知识库Api
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2021-03-24
 */
@FeignClient(name = "future-agent", path = "/api")
public interface DocumentApi {

    /**
     * 获取数据集列表
     *
     * @return
     */
    @GetMapping("/v1/document/list")
    ActionResult getDocumentList(@RequestParam("kb_id") String kb_id, @RequestParam("page") Integer page, @RequestParam("page_size") Integer page_size, @RequestParam("orderby") String orderby, @RequestParam("desc") String desc);

    /*
     * 数据集启动停用
     * @return
     * */
    @PostMapping("/v1/document/change_status")
    ActionResult changeStatus(@RequestBody DocumentStatusRequest documentStatusRequest);

    /*
     * 数据集删除
     * @return
     * */
    @PostMapping("/v1/document/rm")
    ActionResult documentRm(@RequestBody JSONObject request);

    /*
     * 解析数据集
     * @return
     * */
    @PostMapping("/v1/document/run")
    ActionResult documentRun(@RequestBody JSONObject request);

    /*
     * 解析数据集
     * @return
     * */
    @PostMapping("/v1/document/rename")
    ActionResult documentRename(@RequestBody JSONObject request);

    /*
     * 解析方法编辑
     * @return
     * */
    @PostMapping("/v1/document/change_parser")
    ActionResult documentChangeparser(@RequestBody JSONObject request);

    /*
    * 新增文件
    * @return
    * */
    @PostMapping("/v1/document/upload")
    ActionResult documentUpload(@RequestPart("kb_id") String kb_id ,@RequestPart("file") MultipartFile[] files);
}


