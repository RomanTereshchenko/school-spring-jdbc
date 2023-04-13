package com.foxminded.javaspring.schoolspringjdbc.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.javaspring.schoolspringjdbc.model.Student;
import com.foxminded.javaspring.schoolspringjdbc.service.DBGeneratorService;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class JdbcStudentDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcStudentDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional
	public void addStudentsToDB() {
		DBGeneratorService.students.forEach(this::addStudentToDB);
		log.info("Students added to School database");
	}

	public int addStudentToDB(Student student) {
		return jdbcTemplate.update("INSERT INTO school.students (first_name, last_name) VALUES (?, ?);",
				student.getFirstName(), student.getLastName());
	}

	public int deleteStudentFromDB(int studentID) {
		return jdbcTemplate.update("DELETE FROM school.students WHERE student_id = ?;", studentID);
	}

	@Transactional
	public void addGroupIDToAllTheirStudentsInDB() {
		for (Student student : DBGeneratorService.students) {
			if (student.getGroupID() != 0) {
				addGroupIDToStudentInDB(student);
			}
		}
		log.info("Students assigned to groups in School database");
	}

	public int addGroupIDToStudentInDB(Student student) {
		return jdbcTemplate.update("UPDATE school.students SET group_id = ? WHERE student_id = ?;", student.getGroupID(),
				student.getStudentID());
	}

	public List<Student> findStudentsRelatedToCourse(String courseName) {
		return jdbcTemplate.query(
				"SELECT s.student_id AS studentID, s.first_name AS firstName, "
						+ "s.last_name AS lastName FROM school.students s "
						+ "INNER JOIN school.students_courses sc ON s.student_id = sc.student_id "
						+ "INNER JOIN school.courses c ON c.course_id = sc.course_id " + "WHERE course_name = ?;",
				BeanPropertyRowMapper.newInstance(Student.class), courseName);
	}
}
