package com.namyesol.tuesday.infrastructure;

import java.util.Locale;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "file:src/main/webapp/WEB-INF/application-context.xml",
        "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"
})
public class MessageSourceTest {

	@Autowired
	private MessageSource messageSource;
	
	@Test
	public void testErrorMessage() {
		Locale.setDefault(Locale.KOREAN);
		String message = messageSource.getMessage("NotBlank", null, null, null);
		Assertions.assertThat(message).isEqualTo("빈 칸을 입력해주세요.");
	}
}
