package com.namyesol.tuesday.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

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

	@Value("${jdbc.driverClassName}")
	String driverClassName;
	
	@Value("${jdbc.url}")
	String url;
	
	@Value("${jdbc.username}")
	String username;
	
	@Value("${jdbc.password}")
	String password;
	
	@Value("${filestore.basedir}")
	String basedir;

	@Test
	public void testApplicationPropertiesLoads() {
		assertThat(driverClassName).isEqualTo("oracle.jdbc.driver.OracleDriver");
		assertThat(url).isEqualTo("jdbc:oracle:thin:@localhost:1521:xe");
		assertThat(username).isEqualTo("tuesday");
		assertThat(password).isEqualTo("tuesday");
	}

	@Test
	public void shouldFindFileStoreDirectory() {
		assertThat(basedir).isNotNull();
		assertThat(basedir).endsWith("/files/");
	}
}
