package com.mcb.bankpropertyevaluation.controller.payload;

import lombok.Data;


@Data
public class DocumentDto {
    private Long id;
    private DocumentTypeDto type;
    private byte[] document;
}
