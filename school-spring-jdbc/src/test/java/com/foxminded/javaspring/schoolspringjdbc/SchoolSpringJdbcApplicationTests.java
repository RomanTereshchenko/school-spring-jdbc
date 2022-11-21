package com.foxminded.javaspring.schoolspringjdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcCourseDao;

//import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcCourseDao;

//@SpringBootTest
@JdbcTest

class SchoolSpringJdbcApplicationTests {

//	JdbcTemplate jdbcTemplate;
//	JdbcCourseDao jdbcCourseDao;
	
//	@Autowired
//	public SchoolSpringJdbcApplicationTests(JdbcCourseDao jdbcCourseDao) {
//		super();
//		this.jdbcCourseDao = jdbcCourseDao;
//	}

//	@Test
//	void contextLoads() {
//	}

//	@Test
//	@Sql("INSERT INTO school.courses(course_name) VALUES (?);")
//	void whenApplicationIsRunning_thenaddCourseToDBReturnsCorrectCountOfCoursesAddedToDB() {
//		assertEquals(1, jdbcCourseDao.addCourseToDB("TestCourse"));
//	}



}
