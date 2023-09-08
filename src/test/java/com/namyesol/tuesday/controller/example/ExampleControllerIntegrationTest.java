package com.namyesol.tuesday.controller.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/dispatcher-servlet.xml", "file:src/main/webapp/WEB-INF/application-context.xml"})
@WebAppConfiguration
public class ExampleControllerIntegrationTest {

		@Autowired
		private WebApplicationContext wac;
		
		private MockMvc mockMvc;
		
		@Before
		public void setUp() {
			this.mockMvc = MockMvcBuilders
					.webAppContextSetup(wac).build();
		}

		@Test
		public void testValidation() throws Exception {
			
			this.mockMvc.perform(post("/example/create"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("example"))
				.andExpect(model().errorCount(3))
				.andExpect(model().attributeHasErrors("example"))
				.andExpect(model().attributeHasFieldErrors("example", "title"))
				.andExpect(model().attributeHasFieldErrors("example", "author", "title", "content"));
		}
}
