package com.mcb.bankpropertyevaluation.controller.payload;

import lombok.Data;

@Data
public class BorrowerDto {
    private Long id;
    private String name;
    private String customerNumber;
    private String contactNumber;
    private String email;
    private String address;
}
