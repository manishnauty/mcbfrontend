package com.mcb.bankpropertyevaluation.controller;

import com.mcb.bankpropertyevaluation.controller.payload.request.LoginRequest;
import com.mcb.bankpropertyevaluation.controller.payload.request.UserRequestDTO;
import com.mcb.bankpropertyevaluation.controller.payload.response.JwtResponse;
import com.mcb.bankpropertyevaluation.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * The Class UserControllerTest.
 */
@ExtendWith(MockitoExtension.class)
class LoginControllerTest {
	
	/** The controller. */
	@InjectMocks
	private LoginController controller;
	
	/** The service. */
	@Mock
	private UserService service;

	/**
	 * Test register user.
	 */
	@Test
	void testRegisterUser() {
		Mockito.when(service.registerUser(Mockito.any())).thenReturn(1l);
		controller.registerUser(new UserRequestDTO());
		Mockito.verify(service, Mockito.times(1)).registerUser(Mockito.any());
	}

	/**
	 * Test authenticate user.
	 */
	@Test
	void testAuthenticateUser() {
		Mockito.when(service.authenticateUser(Mockito.any()))
				.thenReturn(new JwtResponse("abc", "abc", 1l, "USERNAME",1));
		controller.authenticateUser(new LoginRequest());
		Mockito.verify(service, Mockito.times(1)).authenticateUser(Mockito.any());
	}
}
