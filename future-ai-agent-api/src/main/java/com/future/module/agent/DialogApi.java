package com.future.module.agent;

import com.alibaba.fastjson.JSONObject;
import com.future.common.base.ActionResult;
import com.future.module.agent.model.dialog.DialogForm;
import org.springframework.cloud.openfeign.FeignClient;
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
@FeignClient(name = "future-agent", path = "/api")
public interface DialogApi {


    @PostMapping("/v1/dialog/set")
    ActionResult setDialog(@RequestBody DialogForm dialogForm);

    @PostMapping("/v1/dialog/get")
    ActionResult getDialog(@RequestParam(value = "dialog_id") String dialog_id);

    @PostMapping("/v1/dialog/rm")
    ActionResult rmDialog(@RequestParam(value = "dialog_id") String dialog_id);

    @PostMapping("/v1/dialog/list")
    ActionResult getDialogList(@RequestBody JSONObject jsonObject);
}
