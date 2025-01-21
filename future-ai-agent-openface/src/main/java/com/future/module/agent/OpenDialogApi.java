package com.future.module.agent;

import com.alibaba.fastjson.JSONObject;
import com.future.module.agent.model.dialog.DialogForm;
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
@FeignClient(name = "dialogApi", path = "/api", url = "${ai.agent.host.url}")
public interface OpenDialogApi {


    @PostMapping("/v1/dialog/set")
    AgentApiResult setDialog(@RequestBody DialogForm dialogForm);

    @GetMapping("/v1/dialog/get")
    AgentApiResult getDialog(@RequestParam(value = "dialog_id") String dialog_id);

    @PostMapping("/v1/dialog/rm")
    AgentApiResult rmDialog(@RequestBody JSONObject jsonObject);


    @PostMapping("/v1/dialog/list")
    AgentApiResult getDialogList(@RequestBody JSONObject jsonObject);

    @GetMapping("/v1/dialog/list/public")
    AgentApiResult getDialogPublicList();
}
