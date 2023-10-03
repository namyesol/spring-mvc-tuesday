package com.namyesol.tuesday.controller.member;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.namyesol.tuesday.controller.constant.SessionConstants;
import com.namyesol.tuesday.domain.member.Member;
import com.namyesol.tuesday.service.member.LoginService;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

	private static final Logger log = LoggerFactory.getLogger(LoginControllerTest.class);
	
	@InjectMocks
	private LoginController loginController;
	
	@Mock
	private LoginService loginService;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
	}
	
	@Test
	public void testLogin() throws Exception {
		// given
		String username = "namyesol";
		String password = "password";
		String email = "namyesol@naver.com";
		Member member = new Member(username, password, email);
		
		given(loginService.login(username, password)).willReturn(member);
		
		//when
		this.mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("username", username)
				.param("password", password))
			.andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/"))
			.andExpect(request().sessionAttribute(SessionConstants.AUTHENTICATED_MEMBER, is(notNullValue())));
	}
	
	@Test
	public void testLoginFormHasFieldErrors() throws Exception {
		String username = "";
		String password = "";
		
		this.mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("username", username)
				.param("password", password))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("login/login"))
			.andExpect(view().name("login/login"))
			.andExpect(model().attributeExists("loginForm"))
			.andExpect(model().attributeErrorCount("loginForm", 2))
			.andExpect(model().attributeHasFieldErrors("loginForm", "username", "password"));	
	}
	
	@Test
	public void testLoginFormHasGlobalError() throws Exception {
		// given
		String username = "username";
		String password = "password";
		given(loginService.login(anyString(), anyString())).willReturn(null);

		// when
		this.mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("username", username)
				.param("password", password))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("login/login"))
			.andExpect(view().name("login/login"))
			.andExpect(model().attributeExists("loginForm"))
			.andExpect(model().attributeHasErrors("loginForm"));
	}
	
	@Test
	public void testLogout() throws Exception {
		// given
		String username = "namyesol";
		String password = "password";
		String email = "namyesol@naver.com";
		Member member = new Member(username, password, email);
		
		MockHttpSession mockHttpSession = new MockHttpSession();
		mockHttpSession.setAttribute(SessionConstants.AUTHENTICATED_MEMBER, member);
		
		Member loginMember = (Member) mockHttpSession.getAttribute(SessionConstants.AUTHENTICATED_MEMBER);
		log.debug("loginMember={}", loginMember);
		
		// when
		this.mockMvc.perform(post("/logout").session(mockHttpSession))
		.andDo(print())
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/"))
		.andExpect(request().sessionAttributeDoesNotExist(SessionConstants.AUTHENTICATED_MEMBER));
	}
}
