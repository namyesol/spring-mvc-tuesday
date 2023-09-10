package com.namyesol.tuesday.controller.post;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.namyesol.tuesday.controller.constant.SessionConstants;
import com.namyesol.tuesday.domain.member.Member;
import com.namyesol.tuesday.domain.post.Post;
import com.namyesol.tuesday.service.post.CommentService;
import com.namyesol.tuesday.service.post.PostCommandService;
import com.namyesol.tuesday.service.post.PostReadService;

@RunWith(MockitoJUnitRunner.class)
public class PostControllerTest {
	
	@InjectMocks
	PostController postController;
	
	@Mock
	PostCommandService postCommandService;
	
	@Mock
	PostReadService postReadService;
	
	@Mock
	CommentService commentService;
	
	MockMvc mockMvc;
	MockHttpSession mockHttpSession;
	
	Member member;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(postController)
				.build();
		
    	this.member = new Member("test", "test", "test@test.com");
    	this.mockHttpSession = new MockHttpSession();
    	this.mockHttpSession.setAttribute(SessionConstants.AUTHENTICATED_MEMBER, member);

	}
	
	@Test
	public void testInitNewPostForm() throws Exception {
		mockMvc.perform(get("/posts/new"))
		.andExpect(status().isOk())
		.andExpect(view().name("post/post-new"))
		.andExpect(model().attributeExists("postForm"));
	}
	
	@Test
	public void testProcessNewFormSuccess() throws Exception {
		
		doAnswer(invocation -> {
			Post post = invocation.getArgument(0, Post.class);
			post.setId(1L);
			return null;
		}).when(postCommandService).save(any(Post.class));
		
		mockMvc.perform(post("/posts/new")
			.session(mockHttpSession)
			.param("title", "new-title")
			.param("content", "new-content"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/posts/{postId}"));
	}
	
	@Test
	public void testProcessNewFormHasErrors() throws Exception {
		mockMvc.perform(post("/posts/new")
				.session(mockHttpSession)
				.param("title", "")
				.param("content", ""))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasFieldErrors("postForm", "title", "content"))
			.andExpect(view().name("post/post-new"));
	}
	
}
