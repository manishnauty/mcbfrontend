package com.mcb.bankpropertyevaluation.controller.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	private String accessToken = "Bearer";
	private String refreshToken;
	private Long id;
	private String username;
	private Integer roleId;
	private String businessUnit;

	public JwtResponse(String token, String refreshToken, Long id, String username, Integer roleId) {
	}
}
