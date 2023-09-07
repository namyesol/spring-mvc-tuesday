package com.namyesol.tuesday.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

public class MessageCodesResolverTest {

	MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();
	
	@Test
	public void messageCodesResolveObjectError() {
		String[] messageCodes = codesResolver.resolveMessageCodes("required", "post");
		for (String messageCode : messageCodes ) {
			System.out.println("messageCode = " + messageCode);
		}
		assertThat(messageCodes).containsExactly("required.post", "required");
	}
	
	@Test
	public void messageCodesResolveFieldError() {
		String[] messageCodes = codesResolver.resolveMessageCodes("required", "post", "title", String.class);
		for (String messageCode : messageCodes ) {
			System.out.println("messageCode = " + messageCode);
		}
		assertThat(messageCodes).containsExactly("required.post.title", "required.title", "required.java.lang.String", "required");
	}
	
}
