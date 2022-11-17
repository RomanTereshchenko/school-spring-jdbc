package com.foxminded.javaspring.schoolspringjdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcCourseDao;

@SpringBootTest
@JdbcTest

class SchoolSpringJdbcApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	@Sql("INSERT INTO school.courses(course_name) VALUES (?);")
	void whenApplicationIsRunning_thenaddCourseToDBReturnsCorrectCountOfCoursesAddedToDB() {
	JdbcCourseDao jdbcCourseDao = new JdbcCourseDao();
	assertEquals(1, jdbcCourseDao.addCourseToDB("TestCourse"));
	}
	

}
