package com.future.module.agent.model.knowledge;

import lombok.Data;

@Data
public class DocumentStatusRequest {
    private String doc_id;
    private Integer status;
}
