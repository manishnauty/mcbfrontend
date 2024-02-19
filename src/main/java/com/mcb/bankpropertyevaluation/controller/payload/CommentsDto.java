package com.mcb.bankpropertyevaluation.controller.payload;

import lombok.Data;

@Data
public class CommentsDto {
    private Long id;
    private String createdDate;
    private String comments;
}
