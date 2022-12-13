package com.foxminded.javaspring.schoolspringjdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcCourseDao;
import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcGroupDao;
import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcStudentDao;
import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcStudentsCoursesDao;
import com.foxminded.javaspring.schoolspringjdbc.model.Group;
import com.foxminded.javaspring.schoolspringjdbc.model.Student;
import com.foxminded.javaspring.schoolspringjdbc.model.StudentCourse;

@SpringBootTest
class SchoolSpringJdbcApplicationTests {

	private JdbcCourseDao jdbcCourseDao;
	private JdbcGroupDao jdbcGroupDao;
	private JdbcStudentDao jdbcStudentDao;
	private JdbcStudentsCoursesDao jdbcStudentsCoursesDao;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public SchoolSpringJdbcApplicationTests(JdbcCourseDao jdbcCourseDao, JdbcGroupDao jdbcGroupDao,
			JdbcStudentDao jdbcStudentDao, JdbcStudentsCoursesDao jdbcStudentsCoursesDao, JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcCourseDao = jdbcCourseDao;
		this.jdbcGroupDao = jdbcGroupDao;
		this.jdbcStudentDao = jdbcStudentDao;
		this.jdbcStudentsCoursesDao = jdbcStudentsCoursesDao;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Test
	void addCourseToDBReturnsCorrectCountOfCoursesAddedToDB() {
		assertEquals(1, jdbcCourseDao.addCourseToDB("TestCourse"));
	}

	@Test
	void addGroupsToDBReturnsCorrectCountOfGroupsAddedToDB() {
		assertEquals(1, jdbcGroupDao.addGroupToDB("aa-11"));
	}

	@Test
	void whenStudentCountBetweenTenAndThiry_selectGroupsByStudentsCountReturnsListOfGroupsWithSizeMoreThenZero() {
		List<Group> groupsByStudentsCount = jdbcGroupDao.selectGroupsByStudentsCount(20);
		assertTrue(groupsByStudentsCount.size() > 0);
	}

	@Test
	void addStudentToDBReturnsCorrectCountOfStudentsAddedToDB() {
		assertEquals(1, jdbcStudentDao.addStudentToDB("TestStudentFirstName", "TestStudentLastName"));
	}

	@Test
	void deleteStudentFromDBReturnsCorrectCountOfStudentsDeletedFromDB() {
		jdbcStudentDao.addStudentToDB("DelTestStudentFName", "DelTestStudentLName");
		int testStudentID = jdbcTemplate
				.queryForObject("SELECT student_id from school.students WHERE first_name='DelTestStudentFName' AND "
						+ "last_name='DelTestStudentLName';", Integer.class);
		assertEquals(1, jdbcStudentDao.deleteStudentFromDB(testStudentID));
	}

	@Test
	void addGroupIDToStudentInDBReturnsCountOfStudentsWithAddedGroupIDMoreThanZero() {
		assertTrue(jdbcStudentDao.addGroupIDToStudentInDB(1, "TestStudentFirstName", "TestStudentLastName") > 0);
	}

	@Test
	void whenCourseNameIsFromListOfCourses_findStudentsRelatedToCourseReturnsListOfStudnetsWithSizeMoreThenZero() {
		List<Student> studentsRelatedToCourse = jdbcStudentDao.findStudentsRelatedToCourse("Mathematics");
		assertTrue(studentsRelatedToCourse.size() > 0);
	}

	@Test
	void addStudentCourseAssignmentInDBReturnsCorrectCountOfAddedAssignments() {
		List<StudentCourse> currentAssignments = jdbcTemplate.query("SELECT * from school.students_courses",
				BeanPropertyRowMapper.newInstance(StudentCourse.class));
		int studentId = currentAssignments.get(0).getStudentId();
		int courseId = currentAssignments.get(0).getCourseId();
		jdbcTemplate.update("DELETE FROM school.students_courses WHERE student_id = ? AND course_id = ?",
				currentAssignments.get(0).getStudentId(), currentAssignments.get(0).getCourseId());
		assertEquals(1, jdbcStudentsCoursesDao.addStudentCourseAssignmentInDB(studentId, courseId));
	}

	@Test
	void whenStudentIDIsWithinTheList_getCoursesOfStudentReturnsListOfStudentCoursesWithSizeMoreThenZero() {
		List<StudentCourse> coursesOfStudent = jdbcStudentsCoursesDao.getCoursesOfStudent(1);
		assertTrue(coursesOfStudent.size() > 0);

	}

	@Test
	void deleteStudentFromCourseReturnsCorrectCountOfDeletedAssignments() {
		List<StudentCourse> currentAssignments = jdbcTemplate.query("SELECT * from school.students_courses",
				BeanPropertyRowMapper.newInstance(StudentCourse.class));
		assertEquals(1, jdbcStudentsCoursesDao.deleteStudentFromCourse(currentAssignments.get(0).getStudentId(),
				currentAssignments.get(0).getCourseId()));
	}

}
