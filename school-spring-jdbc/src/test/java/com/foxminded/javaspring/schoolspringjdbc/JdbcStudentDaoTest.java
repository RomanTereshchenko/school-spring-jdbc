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
	void testAddStudentToDB() {
		jdbcStudentDao.addStudentToDB(new Student("TestFName", "TestLName"));
		Student student = jdbcTemplate.queryForObject(
				"SELECT * FROM school.students WHERE first_name = ? AND " + "last_name = ?;",
				BeanPropertyRowMapper.newInstance(Student.class), "TestFName", "TestLName");
		assertNotNull(student);
		assertEquals("TestFName", student.getFirstName());
		assertEquals("TestLName", student.getLastName());
	}

	@Test
	void testDeleteStudentFromDB() {
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
	void testAddGroupIDToStudentInDB() {
		jdbcTemplate.update("INSERT INTO school.students (first_name, last_name) VALUES ('TestStudentFName', "
				+ "'TestStudentLName');");
		jdbcTemplate.update("INSERT INTO school.groups(group_name) VALUES('tt-11');");
		jdbcStudentDao.addGroupIDToStudentInDB(new Student (1, 1));
		int groupID = jdbcTemplate.queryForObject("SELECT group_id AS groupID from school.students WHERE "
				+ "first_name = 'TestStudentFName' AND " + "last_name = 'TestStudentLName'", Integer.class);
		assertEquals(1, groupID);
	}

	@Test
	void findStudentsRelatedToCourse_ReturnsCorrectListOfStudentsAssignedToSelectedCourse() {
		jdbcTemplate.update("INSERT INTO school.students (first_name, last_name) VALUES ('TestStudentFName', "
				+ "'TestStudentLName');");
		jdbcTemplate.update("INSERT INTO school.courses(course_name) VALUES ('TestCourse');");
		jdbcTemplate.update("INSERT INTO school.students_courses (student_id, course_id) VALUES (1, 1);");
		List<Student> studentsRelatedToCourse = jdbcStudentDao.findStudentsRelatedToCourse("TestCourse");
		assertEquals(1, studentsRelatedToCourse.size());
		assertEquals("TestStudentFName", studentsRelatedToCourse.get(0).getFirstName());
		assertEquals("TestStudentLName", studentsRelatedToCourse.get(0).getLastName());
	}

}
