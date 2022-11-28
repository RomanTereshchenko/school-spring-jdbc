package com.foxminded.javaspring.schoolspringjdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcCourseDao;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc

class SchoolSpringJdbcApplicationTests {

	@Autowired
	JdbcCourseDao jdbcCourseDao;

	@Test
	@Sql("INSERT INTO school.courses(course_name) VALUES (?);")
	void whenApplicationIsRunning_thenaddCourseToDBReturnsCorrectCountOfCoursesAddedToDB() {
		assertEquals(1, jdbcCourseDao.addCourseToDB("TestCourse"));
	}

}
