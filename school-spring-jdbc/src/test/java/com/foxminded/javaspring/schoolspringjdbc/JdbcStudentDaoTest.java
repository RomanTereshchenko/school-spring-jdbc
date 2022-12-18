package com.foxminded.javaspring.schoolspringjdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcStudentDao;
import com.foxminded.javaspring.schoolspringjdbc.model.Student;

@SpringBootTest
class JdbcStudentDaoTest {

	private JdbcStudentDao jdbcStudentDao;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcStudentDaoTest(JdbcStudentDao jdbcStudentDao, JdbcTemplate jdbcTemplate) {
		this.jdbcStudentDao = jdbcStudentDao;
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
	void addStudentToDB_AddsStudentToDB() {
		jdbcStudentDao.addStudentToDB(new Student("TestFName", "TestLName"));
		Student student = jdbcTemplate.queryForObject(
				"SELECT * FROM school.students WHERE first_name = ? AND " + "last_name = ?;",
				BeanPropertyRowMapper.newInstance(Student.class), "TestFName", "TestLName");
		assertNotNull(student);
		assertEquals("TestFName", student.getFirstName());
		assertEquals("TestLName", student.getLastName());
	}

	@Test
	void deleteStudentFromDB_DeletsStudentFromDB() {
		jdbcTemplate.update("INSERT INTO school.students (first_name, last_name) VALUES ('DelStudentFName', "
				+ "'DelStudentLName');");
		Student student = jdbcTemplate.queryForObject("SELECT * from school.students WHERE "
				+ "first_name = 'DelStudentFName' AND " + "last_name = 'DelStudentLName'",
				BeanPropertyRowMapper.newInstance(Student.class));
		assertNotNull(student);
		int delStudentID = jdbcTemplate.queryForObject("SELECT student_id AS studentID from school.students s WHERE "
				+ "s.first_name='DelStudentFName' AND " + "s.last_name='DelStudentLName';",
				Integer.class);
		jdbcStudentDao.deleteStudentFromDB(delStudentID);
		assertThrows(EmptyResultDataAccessException.class, () -> {
			jdbcTemplate.queryForObject("Select * from " + "school.students WHERE first_name = 'DelStudentFName' AND "
					+ "last_name = 'DelStudentLName'", BeanPropertyRowMapper.newInstance(Student.class));
		});
	}

	@Test
	void addGroupIDToStudentInDB_AddsGroupIDToStudent() {
		jdbcTemplate.update("INSERT INTO school.students (first_name, last_name) VALUES ('TestStudentFName', "
				+ "'TestStudentLName');");
		jdbcTemplate.update("INSERT INTO school.groups(group_name) VALUES('tt-11');");
		jdbcStudentDao.addGroupIDToStudentInDB(new Student (1, 1));
		Student student = jdbcTemplate.queryForObject("Select * from school.students WHERE "
				+ "first_name = 'TestStudentFName' AND " + "last_name = 'TestStudentLName'",
				BeanPropertyRowMapper.newInstance(Student.class));
		assertEquals(0, student.getGroupID());
	}

	@Test
	void findStudentsRelatedToCourse_ReturnsCorrectListOfStudentsAssignedToSelectedCourse() {
		jdbcTemplate.update("INSERT INTO school.students (first_name, last_name) VALUES ('TestStudentFName1', "
				+ "'TestStudentLName1');");
		jdbcTemplate.update("INSERT INTO school.students (first_name, last_name) VALUES ('TestStudentFName2', "
				+ "'TestStudentLName2');");
		jdbcTemplate.update("INSERT INTO school.courses(course_name) VALUES ('TestCourse');");
		jdbcTemplate.update("INSERT INTO school.students_courses (student_id, course_id) VALUES (1, 1);");
		jdbcTemplate.update("INSERT INTO school.students_courses (student_id, course_id) VALUES (2, 1);");
		List<Student> studentsRelatedToCourse = jdbcStudentDao.findStudentsRelatedToCourse("TestCourse");
		assertNotNull(studentsRelatedToCourse);
		List<String> firstNames = new ArrayList<>();
		List<String> lastNames = new ArrayList<>();
		for (Student student : studentsRelatedToCourse) {
			firstNames.add(student.getFirstName());
			lastNames.add(student.getLastName());
		}
		assertTrue(firstNames.contains("TestStudentFName1"));
		assertTrue(firstNames.contains("TestStudentFName2"));
		assertTrue(lastNames.contains("TestStudentLName1"));
		assertTrue(lastNames.contains("TestStudentLName2"));
	}

}
