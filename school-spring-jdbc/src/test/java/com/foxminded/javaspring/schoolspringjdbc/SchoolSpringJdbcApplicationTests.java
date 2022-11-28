package com.foxminded.javaspring.schoolspringjdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcCourseDao;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@AutoConfigureMockMvc
class SchoolSpringJdbcApplicationTests {

	@Autowired
	JdbcCourseDao jdbcCourseDao;

	@Sql(statements = "INSERT INTO school.courses(course_name) VALUES ('math');")
	@Test
	void whenApplicationIsRunning_thenaddCourseToDBReturnsCorrectCountOfCoursesAddedToDB() {
		assertEquals(1, jdbcCourseDao.addCourseToDB("TestCourse"));
	}

}
