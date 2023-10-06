package com.namyesol.tuesday.controller.storage;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.namyesol.tuesday.controller.constant.SessionConstants;
import com.namyesol.tuesday.domain.member.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/dispatcher-servlet.xml", "file:src/main/webapp/WEB-INF/application-context.xml"})
@WebAppConfiguration
public class StorageControllerTest {
    
    @Autowired
    private WebApplicationContext wac;
    
    private MockHttpSession mockHttpSession;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        Member currentMember = new Member("test", "test", "test@example.com");
        currentMember.setId(1L);
        this.mockHttpSession = new MockHttpSession(wac.getServletContext());
        this.mockHttpSession.setAttribute(SessionConstants.AUTHENTICATED_MEMBER, currentMember);
    }

    @Test
    public void shouldSaveStorageObject() throws Exception {
        
        List<MockMultipartFile> files = Arrays.asList(
            new MockMultipartFile("files", "file-1.txt  ", MediaType.TEXT_PLAIN_VALUE, "file-1".getBytes()),
            new MockMultipartFile("files", "file-2.txt", MediaType.TEXT_PLAIN_VALUE, "file-2".getBytes())
        );

        List<MockMultipartFile> imageFiles = Arrays.asList(
            new MockMultipartFile("imageFiles", "cat-1.png", MediaType.IMAGE_PNG_VALUE, "<<cat-1 data>>".getBytes()),
            new MockMultipartFile("imageFiles", "cat-2.png", MediaType.IMAGE_PNG_VALUE, "<<cat-2 data>>".getBytes())
        );

        MockMultipartHttpServletRequestBuilder request = MockMvcRequestBuilders.multipart("/storage/new");

        for (MockMultipartFile file : files) {
            request.file(file);
        }

        for (MockMultipartFile image : imageFiles) {
            request.file(image);
        }

        request.param("name", "test-name")
                .param("description", "test-description");

        request.session(mockHttpSession);
        
        mockMvc.perform(request)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is3xxRedirection());
    }

}
