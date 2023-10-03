package com.namyesol.tuesday.infrastructure.argumentresolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.namyesol.tuesday.controller.constant.SessionConstants;
import com.namyesol.tuesday.domain.member.Member;
import com.namyesol.tuesday.infrastructure.springframework.argumentresolver.CurrentMember;
import com.namyesol.tuesday.infrastructure.springframework.argumentresolver.CurrentMemberMethodArgumentResolver;

public class CurrentMemberMethodArgumentResolverTest {

	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(new MemberController())
				.setCustomArgumentResolvers(new CurrentMemberMethodArgumentResolver())
				.build();
	}
	
	@Test
	public void testMemberMethodArgumentResolver() throws Exception {
		String username = "namyesol";
		String password = "password";
		String email = "namyesol@naver.com";
		Member member = new Member(username, password, email);
		
		MockHttpSession mockHttpSession = new MockHttpSession();
		mockHttpSession.setAttribute(SessionConstants.AUTHENTICATED_MEMBER, member);
		
		this.mockMvc.perform(get("/echo")
				.session(mockHttpSession)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(jsonPath("$.username").value(username))
			.andExpect(jsonPath("$.password").value(password))
			.andExpect(jsonPath("$.email").value(email));
	}
	
	@Test
	public void shouldThrownErrorWhenLoggedInMemberDoesNotExists() throws Exception {
		Assertions.assertThatThrownBy(() -> {
			this.mockMvc.perform(get("/echo")
					.accept(MediaType.APPLICATION_JSON));
		}).hasCauseInstanceOf(IllegalStateException.class).hasMessageContaining("does not exists");
		
	}
	
	@Controller
	private class MemberController {
		@GetMapping("/echo")
		@ResponseBody
		public Member handler(@CurrentMember Member member) {
			return member;
		}
	}
}
