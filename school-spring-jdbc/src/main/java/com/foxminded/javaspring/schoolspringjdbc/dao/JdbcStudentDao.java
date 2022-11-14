package com.foxminded.javaspring.schoolspringjdbc.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.foxminded.javaspring.schoolspringjdbc.controller.Controller;
import com.foxminded.javaspring.schoolspringjdbc.model.Student;

public class JdbcStudentDao implements StudentDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void addStudentsToDB() {
		Controller.students.forEach(student -> addStudentToDB((student.getFirstName()), (student.getLastName())));
		System.out.println("Students added to School database");
	}

	@Override
	public int addStudentToDB(String firstName, String lastName) {
		return jdbcTemplate.update("INSERT INTO school.students (first_name, last_name) VALUES (?, ?);", firstName,
				lastName);
	}

	@Override
	public int deleteStudentFromDB(int studentID) {
		return jdbcTemplate.update("DELETE FROM school.students WHERE student_id = ?;", studentID);
	}

	public void addGroupIDToAllTheirStudentsInDB() {
		for (Student student : Controller.students) {
			if (student.getGroupID() != 0) {
				addGroupIDToStudentInDB(student.getGroupID(), student.getFirstName(), student.getLastName());
			}
		}
		System.out.println("Students assigned to groups in School database");
	}

	@Override
	public int addGroupIDToStudentInDB(int groupID, String studentFirstName, String studentLastName) {
		return jdbcTemplate.update("UPDATE school.students SET group_id = ? WHERE first_name = ? AND last_name = ?;",
				groupID, studentFirstName, studentLastName);
	}

	@Override
	public List<Student> getStudentsRelatedToCourse(String courseName) {
		return jdbcTemplate.query(
				"SELECT school.students.student_id, school.students.first_name, school.students.last_name FROM school.students "
						+ "INNER JOIN school.students_courses ON school.students.student_id = school.students_courses.student_id "
						+ "INNER JOIN school.courses ON school.courses.course_id = school.students_courses.course_id "
						+ "WHERE course_name = ?;",
				BeanPropertyRowMapper.newInstance(Student.class), courseName);
	}
}
