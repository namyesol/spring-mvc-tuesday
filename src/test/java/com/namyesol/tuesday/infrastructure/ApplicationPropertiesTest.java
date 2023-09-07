package com.namyesol.tuesday.infrastructure;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "file:src/main/webapp/WEB-INF/application-context.xml"
})
public class ApplicationPropertiesTest {

	@Value("${driverClassName}")
	String driverClassName;
	
	@Value("${url}")
	String url;
	
	@Value("${username}")
	String username;
	
	@Value("${password}")
	String password;
	
	@Test
	public void testApplicationPropertiesLoads() {
		assertThat(driverClassName).isEqualTo("oracle.jdbc.driver.OracleDriver");
		assertThat(url).isEqualTo("jdbc:oracle:thin:@localhost:1521:xe");
		assertThat(username).isEqualTo("tuesday");
		assertThat(password).isEqualTo("tuesday");
	}
}
