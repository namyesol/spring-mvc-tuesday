package com.namyesol.tuesday.controller.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

public class ExampleControllerTest {

	private MockMvc mockMvc;
	
	@Test
	public void testPlainText() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new ExampleController()).build();
		
		this.mockMvc.perform(get("/example/hello"))
			.andExpect(status().isOk())
			.andExpect(content().string("Hello, World!"));
	}
	
	@Test
	public void testView() throws Exception {
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(new ExampleController())
				.setViewResolvers(viewResolver())
				.build();
		
		
		this.mockMvc.perform(get("/example/example-view"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("example/example-view"));
	}
	
	private ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	@Test
	public void testModel() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new ExampleController())
				.setViewResolvers(viewResolver())
				.build();
		
		this.mockMvc.perform(get("/example/example-model"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("example/example-model"))
			.andExpect(model().attribute("name", "yesol"));
	}
	
	@Test
	public void testInitExampleForm() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new ExampleController())
				.setViewResolvers(viewResolver())
				.build();
		
		mockMvc.perform(get("/example/create"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("example"))
			.andExpect(view().name("example/example-create"));
		
	}
	@Test
	public void testProcessFormSuccess() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new ExampleController())
				.setViewResolvers(viewResolver())
				.build();
		
		mockMvc.perform(
				post("/example/create")
				.param("author", "yesol")
				.param("title", "should greater than 4")
				.param("content", "should not be blank")
			)
			.andExpect(status().is3xxRedirection());
	}
	
	@Test
	public void testProcessFormHasErrors() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new ExampleController())
				.setViewResolvers(viewResolver())
				.build();
		
		this.mockMvc.perform(post("/example/create"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("example"))
			.andExpect(model().errorCount(3))
			.andExpect(model().attributeHasFieldErrors("example", "author", "title", "content"));
	}
	
}
