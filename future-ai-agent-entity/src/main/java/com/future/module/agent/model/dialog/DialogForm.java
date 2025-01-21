package com.future.module.agent.model.dialog;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * DialogForm
 * @版本： V4.0.0
 * @版权： 直方信息科技有限公司
 * @作者： Future开发平台组
 * @日期： 2024-09-27
 */
@Data
public class DialogForm {

    /*名称*/
    private String name;
    /*描述*/
    private String description;
    /*头像*/
    private String icon;

    /*top_n*/
    private String top_n;
    /*相似度阈值（0-1）*/
    private Double similarity_threshold;
    /*关键字相似度权重（0-1） */
    private Double vector_similarity_weight;
    /*智能体ID*/
    private String dialog_id;
    /*模型（默认：qwen2.5-instruct）*/
    private String llm_id;

    private PromptConfig prompt_config;

    private LlmSetting llm_setting;

    private List<String> default_question;

    /**知识库IDs*/
    private List<String> kb_ids;

    /*提示配置*/
    @Data
    public static class PromptConfig{
        /*空回复*/
        private String   empty_response;
        /*开场白*/
        private String    prologue;
        /*提示语*/
        private String    system;
        /*参数*/
        private List<Parameters> parameters;

        /*多轮对话优化*/
        private String refine_multiturn;

        public PromptConfig() {
            Parameters param = new Parameters();
            param.setKey("knowledge");
            param.setOptional(false);
            this.parameters = Arrays.asList(param); // 默认值：一个空的ArrayList
        }

        // 你可以根据需要添加其他构造函数
        public PromptConfig(List<Parameters> parameters) {
            this.parameters = parameters;
        }
    }

    @Data
    public static class Parameters{
        private String key;
        private boolean optional;
    }

    /*模型设置*/
    @Data
    public static class LlmSetting{
        /*温度（0-1）*/
        private Double   temperature;
        /*Top P（0-1）*/
        private Double     top_p;
        /*出席处罚（0-1）*/
        private Double     presence_penalty;
        /*频率惩罚（0-1）*/
        private Double     frequency_penalty;
        /*最大token数（0-8192）*/
        private Integer       max_tokens;
    }




}
