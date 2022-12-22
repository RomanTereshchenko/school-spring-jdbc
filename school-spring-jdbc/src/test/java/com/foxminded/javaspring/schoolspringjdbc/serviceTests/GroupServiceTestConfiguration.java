package com.foxminded.javaspring.schoolspringjdbc.serviceTests;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcGroupDao;

@Profile("test")
@Configuration
public class GroupServiceTestConfiguration {
	@Bean
	@Primary
	public JdbcGroupDao jdbcGroupDao() {
		return Mockito.mock(JdbcGroupDao.class);
	}
}
