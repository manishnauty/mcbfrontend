package com.mcb.bankpropertyevaluation.controller;

import com.mcb.bankpropertyevaluation.controller.payload.request.LoginRequest;
import com.mcb.bankpropertyevaluation.controller.payload.request.TokenRefreshRequest;
import com.mcb.bankpropertyevaluation.controller.payload.request.UserRequestDTO;
import com.mcb.bankpropertyevaluation.controller.payload.response.CustomResponse;
import com.mcb.bankpropertyevaluation.controller.payload.response.JwtResponse;
import com.mcb.bankpropertyevaluation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The Class UserController.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class LoginController {

	/** The user service. */
	@Autowired
	private UserService userService;

	@PostMapping("/registerUser")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
		Long userId = userService.registerUser(userRequestDTO);
		return new ResponseEntity<>(new CustomResponse("User registered successfully", userId), HttpStatus.CREATED);
	}

	/**
	 * Authenticate user.
	 *
	 * @param loginRequest the login request
	 * @return the response entity
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		JwtResponse jwtResponse = userService.authenticateUser(loginRequest);
		return new ResponseEntity<>(jwtResponse, HttpStatus.OK);

	}
	
	/**
	 * Refreshtoken.
	 *
	 * @param request the request
	 * @return the response entity
	 */
	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		return new ResponseEntity<>(userService.refreshToken(request), HttpStatus.OK);
	}
	
}
