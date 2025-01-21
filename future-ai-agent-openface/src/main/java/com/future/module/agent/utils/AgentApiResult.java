//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.future.module.agent.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.future.common.base.ActionResult;
import com.future.common.base.vo.PageListVO;
import com.future.common.base.vo.PaginationVO;
import com.future.common.constant.MsgCode;
import com.future.common.exception.DataException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@JsonInclude(Include.NON_NULL)
@Data
public class AgentApiResult<T> {
    @Schema(
        description = "状态码"
    )
    private Integer code;
    @Schema(
        description = "返回信息"
    )
    private String message;
    @Schema(
        description = "返回数据"
    )
    private T data;

    public AgentApiResult() {

    }

    public static void  checkResult(AgentApiResult agentApiResult) {
        if(agentApiResult==null){
            throw new RuntimeException("接口请求异常");
        }
        if(null==agentApiResult.getCode()){
            throw new RuntimeException("接口请求异常");
        }
        if(agentApiResult.getCode() == 102){ //表示未查找到数据
            return;
        }
        if(agentApiResult.getCode()!=0){
            throw new RuntimeException(agentApiResult.getMessage());
        }
    }

    public static <T> ActionResult<T> cActionResult(AgentApiResult<T> agentApiResult) {
        checkResult(agentApiResult);
        ActionResult<T> jsonData = new ActionResult();
        jsonData.setData(agentApiResult.getData());
        jsonData.setCode(200);
        return jsonData;
    }

}
