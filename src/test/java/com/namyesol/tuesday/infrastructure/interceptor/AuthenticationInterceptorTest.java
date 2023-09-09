package com.namyesol.tuesday.infrastructure.interceptor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.namyesol.tuesday.controller.constant.SessionConstants;
import com.namyesol.tuesday.domain.member.Member;
import com.namyesol.tuesday.infrastructure.springframework.interceptor.AuthenticationInterceptor;

public class AuthenticationInterceptorTest {

	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(new AuthController())
				.addMappedInterceptors(new String[]{"/protected"},
						new AuthenticationInterceptor())
				.build();
	}
	@Test
	public void testProtectedResource() throws Exception {
		Member member = new Member("namyesol", "password", "namyesol@naver.com");
		
		this.mockMvc.perform(get("/protected").sessionAttr(SessionConstants.AUTHENTICATED_MEMBER, member))
			.andExpect(status().isOk())
			.andExpect(content().string("ok"));
			
	}
	
	@Test
	public void testProtectedResourceWithoutAuthentication() throws Exception {
		this.mockMvc.perform(get("/protected"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/login?redirect=/protected"));
	}
	
	@Test
	public void testPublicResource() throws Exception {
		this.mockMvc.perform(get("/anonymous"))
			.andExpect(status().isOk())
			.andExpect(content().string("ok"));
	}
	
	@Controller
	private class AuthController {
		
		@GetMapping("/protected")
		@ResponseBody
		public String protect() {
			return "ok";
		}
		
		@GetMapping("/anonymous")
		@ResponseBody
		public String anonymous() {
			return "ok";
		}
	}
}
