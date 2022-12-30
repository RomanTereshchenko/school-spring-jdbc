package com.foxminded.javaspring.schoolspringjdbc.daoTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcCourseDao;
import com.foxminded.javaspring.schoolspringjdbc.model.Course;

@SpringBootTest
class JdbcCourseDaoTest {

	private JdbcCourseDao jdbcCourseDao;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcCourseDaoTest(JdbcCourseDao jdbcCourseDao, JdbcTemplate jdbcTemplate) {
		this.jdbcCourseDao = jdbcCourseDao;
		this.jdbcTemplate = jdbcTemplate;
	}

	@BeforeEach
	void truncateTables() {
		jdbcTemplate.execute("TRUNCATE TABLE school.groups CASCADE;");
		jdbcTemplate.execute("ALTER SEQUENCE school.groups_group_id_seq RESTART WITH 1;");
		jdbcTemplate.execute(
				"ALTER TABLE school.groups ALTER COLUMN group_id SET DEFAULT nextval('school.groups_group_id_seq');");
		jdbcTemplate.execute("TRUNCATE TABLE school.courses CASCADE;");
		jdbcTemplate.execute("ALTER SEQUENCE school.courses_course_id_seq RESTART WITH 1;");
		jdbcTemplate.execute(
				"ALTER TABLE school.courses ALTER COLUMN course_id SET DEFAULT nextval('school.courses_course_id_seq');");
		jdbcTemplate.execute("TRUNCATE TABLE school.students CASCADE;");
		jdbcTemplate.execute("ALTER SEQUENCE school.students_student_id_seq RESTART WITH 1;");
		jdbcTemplate.execute(
				"ALTER TABLE school.students ALTER COLUMN student_id SET DEFAULT nextval('school.students_student_id_seq');");
		jdbcTemplate.execute("TRUNCATE TABLE school.students_courses CASCADE;");
	}

	@Test
	void testAddCourseToDB() {
		jdbcCourseDao.addCourseToDB(new Course(1, "TestCourse"));
		Course course = jdbcTemplate.queryForObject("SELECT * FROM school.courses c WHERE c.course_name = ?",
				BeanPropertyRowMapper.newInstance(Course.class), "TestCourse");
		assertNotNull(course);
		assertEquals("TestCourse", course.getCourseName());
	}

}
