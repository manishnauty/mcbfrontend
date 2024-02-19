package com.mcb.bankpropertyevaluation.controller.payload.request;

import lombok.Data;

import javax.validation.constraints.Size;
@Data
public class UserRequestDTO {
	
	@Size(min = 3, max = 20)
	private String username;
	
	private String role;
	
	@Size(min = 6, max = 40)
	private String password;
	
	private String contactNumber;

	@Size(min = 3, max = 50)
	private String buisnessUnit;


}
