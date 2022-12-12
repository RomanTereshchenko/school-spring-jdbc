package com.foxminded.javaspring.schoolspringjdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcCourseDao;
import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcGroupDao;

@SpringBootTest
class SchoolSpringJdbcApplicationTests {

	@Autowired
	JdbcCourseDao jdbcCourseDao;
	
	@Autowired
	JdbcGroupDao jdbcGroupDao;

	@Test
	void whenApplicationIsRunning_thenaddCourseToDBReturnsCorrectCountOfCoursesAddedToDB() {
		assertEquals(1, jdbcCourseDao.addCourseToDB("TestCourse"));
	}
	
	@Test
	void whenApplicationIsRunning_thenaddGroupsToDBReturnsCorrectCountOfGroupsAddedToDB() {
		assertEquals(1, jdbcGroupDao.addGroupToDB("aa-11"));
	}

}
