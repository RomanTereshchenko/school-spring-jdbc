package com.foxminded.javaspring.schoolspringjdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcStudentsCoursesDao;
import com.foxminded.javaspring.schoolspringjdbc.model.StudentCourse;

@SpringBootTest
class JdbcStudentsCoursesDaoTest {

	private JdbcStudentsCoursesDao jdbcStudentsCoursesDao;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcStudentsCoursesDaoTest(JdbcStudentsCoursesDao jdbcStudentsCoursesDao, JdbcTemplate jdbcTemplate) {
		this.jdbcStudentsCoursesDao = jdbcStudentsCoursesDao;
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
	void addStudentCourseAssignmentInDB_AddsStudentCourseAssignment() {
		jdbcTemplate.update("INSERT INTO school.courses(course_name) VALUES ('TestCourse');");
		jdbcTemplate.update("INSERT INTO school.students (first_name, last_name) VALUES ('TestFName', 'TestLName');");
		jdbcStudentsCoursesDao.addStudentCourseAssignmentInDB(new StudentCourse(1, 1));
		StudentCourse studentCourse = jdbcTemplate.queryForObject(
				"SELECT * FROM school.students_courses WHERE student_id = 1 AND course_id = 1;",
				BeanPropertyRowMapper.newInstance(StudentCourse.class));
		assertNotNull(studentCourse);
		assertEquals(1, studentCourse.getStudentId());
		assertEquals(1, studentCourse.getCourseId());
	}

	@Test
	void getCoursesOfStudent_ReturnsListOfStudentCourses() {
		jdbcTemplate.update("INSERT INTO school.students (first_name, last_name) VALUES ('TestFName', 'TestLName');");
		jdbcTemplate.update("INSERT INTO school.courses(course_name) VALUES ('TestCourse1');");
		jdbcTemplate.update("INSERT INTO school.courses(course_name) VALUES ('TestCourse2');");
		jdbcTemplate.update("INSERT INTO school.students_courses (student_id, course_id) VALUES (1, 1);");
		jdbcTemplate.update("INSERT INTO school.students_courses (student_id, course_id) VALUES (1, 2);");
		List<StudentCourse> coursesOfStudent = jdbcStudentsCoursesDao.getCoursesOfStudent(1);
		assertNotNull(coursesOfStudent);
		assertEquals(1, coursesOfStudent.get(0).getCourseId());
		assertEquals(2, coursesOfStudent.get(1).getCourseId());
	}

	@Test
	void deleteStudentFromCourse_deletesStudentCourseAssignmentFromDB() {
		jdbcTemplate.update("INSERT INTO school.students (first_name, last_name) VALUES ('TestFName', 'TestLName');");
		jdbcTemplate.update("INSERT INTO school.courses(course_name) VALUES ('TestCourse1');");
		jdbcTemplate.update("INSERT INTO school.students_courses (student_id, course_id) VALUES (1, 1);");
		List<StudentCourse> studentCourses = jdbcTemplate.query(
				"SELECT * from school.students_courses WHERE student_id = 1",
				BeanPropertyRowMapper.newInstance(StudentCourse.class));
		assertNotNull(studentCourses);
		jdbcStudentsCoursesDao.deleteStudentFromCourse(1, 1);
		assertThrows(EmptyResultDataAccessException.class, () -> {
			jdbcTemplate.queryForObject("Select * " + "from school.students_courses WHERE student_id = 1",
					BeanPropertyRowMapper.newInstance(StudentCourse.class));});
	}
}
