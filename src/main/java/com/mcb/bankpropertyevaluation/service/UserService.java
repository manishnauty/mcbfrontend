package com.mcb.bankpropertyevaluation.service;


import com.mcb.bankpropertyevaluation.controller.payload.request.LoginRequest;
import com.mcb.bankpropertyevaluation.controller.payload.request.TokenRefreshRequest;
import com.mcb.bankpropertyevaluation.controller.payload.request.UserRequestDTO;
import com.mcb.bankpropertyevaluation.controller.payload.response.JwtResponse;

import javax.validation.Valid;

/**
 * The Interface UserService.
 */
public interface UserService {

	/**
	 * Register user.
	 *
	 * @param userRequestDTO the user request DTO
	 * @return the string
	 */
	Long registerUser(@Valid UserRequestDTO userRequestDTO);

	/**
	 * Authenticate user.
	 *
	 * @param loginRequest the login request
	 * @return the jwt response
	 */
	JwtResponse authenticateUser(@Valid LoginRequest loginRequest);

	/**
	 * Signout.
	 */
	void signout();

	JwtResponse refreshToken(@Valid TokenRefreshRequest request);

}
