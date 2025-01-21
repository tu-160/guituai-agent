package com.future.module.agent.model.knowledge;

import lombok.Data;
/**
 * knowledgeForm 知识库创建表单
 * @日期： 2024-12-06
 */
@Data
public class KnowledgeForm extends ConfigurationForm {
    private String kb_id;
    private String name;
    private String kb_type;
}
