package com.future.module.agent.model.knowledge;

import lombok.Data;
/**
 * ConfigurationForm 知识库配置编辑表单
 * @日期： 2024-12-06
 */
@Data
public class ConfigurationForm {
    /*知识库id*/
    private String kb_id;
    /*知识库名称*/
    private String name;
    /*知识库描述*/
    private String description;
    /*权限 （默认为me）*/
    private String permission;
    /*解析方法*/
    private String parser_id;
    /*解析配置*/
    private parserConfig parser_config;
    /*语言*/
    private String language;
    /*头像*/
    private String avatar;
    /*嵌入模型*/
    private String embd_id;
    // 页面排名
    private Integer pagerank;

    @Data
    public static class parserConfig {
        /*自动关键词*/
        private Integer auto_keywords;
        /*自动问题*/
        private Integer auto_questions;
        /*块Token数*/
        private Integer chunk_token_num;
        /*分段标识符*/
        private String delimiter;
        /*表格转HTML*/
        private Boolean html4excel;
        /*布局识别*/
        private Boolean layout_recognize;
        /*增强RAPTOR策略配置*/
        private raptor raptor;
        @Data
        public static class raptor{
            /*使用召回增强RAPTOR策略*/
            private Boolean use_raptor;
            /*最大聚类数*/
            private Integer max_cluster;
            /*最大token数*/
            private Integer max_token;
            /*提示词*/
            private String  prompt;
            /*随机种子*/
            private Integer random_seed;
            /*阈值*/
            private Double threshold;
        }
    }
}
