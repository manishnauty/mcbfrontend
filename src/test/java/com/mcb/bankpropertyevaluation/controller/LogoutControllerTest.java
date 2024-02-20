package com.mcb.bankpropertyevaluation.controller;

import com.mcb.bankpropertyevaluation.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LogoutControllerTest {
	@InjectMocks
	private LogoutController controller;
	@Mock
	private UserService service;
	
	@Test
	void testLogoutUser() {
		Mockito.doNothing().when(service).signout();
		controller.logoutUser();
		Mockito.verify(service, Mockito.times(1)).signout();
	}

}
