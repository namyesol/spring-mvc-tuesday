package com.namyesol.tuesday.controller.member;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.namyesol.tuesday.service.member.RegistrationService;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerTest {

	@InjectMocks
	private RegistrationController registrationController;

	@Mock
	private RegistrationService registrationService;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
	}
	
	@Test
	public void processRegistrationSuccess() throws Exception {
		// given
		String username = "namyesol";
		String password = "password";
		String email = "namyesol@naver.com";
		
		// when
		this.mockMvc.perform(post("/register")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("username", username)
				.param("password", password)
				.param("email", email))
			.andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/"));
	}
	
	@Test
	public void processRegistrationHasFieldErrors() throws Exception {		
		// when
		this.mockMvc.perform(post("/register")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("username", "")
				.param("password", "")
				.param("email", "namyesol.naver.com"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("register/register"))
			.andExpect(view().name("register/register"))
			.andExpect(model().attributeErrorCount("form", 3))
			.andExpect(model().attributeHasFieldErrors("form", "username", "password", "email"));	
	}

}
