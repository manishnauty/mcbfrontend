package com.mcb.bankpropertyevaluation.controller.payload;

import lombok.Data;

@Data
public class FacilityDto {
    private Long id;
    private FacilityTypeDto type;
    private String catagory;
    private String purpose;
    private Integer term;
    private CurrencyDto ccy;
    private Double amount;
}
