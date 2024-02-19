package com.mcb.bankpropertyevaluation.controller.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomResponse {
	
	private String message;
	private Long referenceId;
}
