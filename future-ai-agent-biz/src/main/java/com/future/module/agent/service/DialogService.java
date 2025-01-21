package com.future.module.agent.service;

import com.future.base.service.SuperService;
import com.future.module.agent.entity.UserAgentEntity;
import com.future.module.agent.vo.DialogVo;

import java.util.List;

/**
 * 助理信息
 *
 * @author Future Platform Group
 * @version V4.0.0
 * @copyright 直方信息科技有限公司
 * @date 2019年9月26日 上午9:18
 */
public interface DialogService  {

    /**
     * 通过用户ID查询关联的智能体列表
     *
     * @return
     */
    DialogVo getDialogList(String userId);

    /**
     * 设置预设问题
     * @param question
     * @param dialog_id
     * @return
     */
    void setDialogQuestion(List<String> question,String dialog_id);


}
