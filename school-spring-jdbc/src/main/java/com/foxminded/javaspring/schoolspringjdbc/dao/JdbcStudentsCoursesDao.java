package com.foxminded.javaspring.schoolspringjdbc.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.foxminded.javaspring.schoolspringjdbc.controller.Controller;
import com.foxminded.javaspring.schoolspringjdbc.model.Course;
import com.foxminded.javaspring.schoolspringjdbc.model.StudentCourse;

@Repository
public class JdbcStudentsCoursesDao implements StudentsCoursesDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcStudentsCoursesDao (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void addStudentsCoursesAssignmentsToDB() {
		Controller.students.forEach(student -> addOneStudentCoursesAssignmentsToDB(student.getStudentID()));

		System.out.println("Students' assignments to courses added to School database");
	}

	void addOneStudentCoursesAssignmentsToDB(int studentID) {
		List<Course> coursesOfStudent = Controller.students.get(studentID - 1).getCourses();
		for (Course course : coursesOfStudent) {
			addStudentCourseAssignmentInDB(studentID, course.getCourseID());
		}
	}

	@Override
	public int addStudentCourseAssignmentInDB(int studentID, int courseID) {
		return jdbcTemplate.update("INSERT INTO school.students_courses (student_id, course_id) VALUES (?, ?);",
				studentID, courseID);
	}

	@Override
	public List<StudentCourse> getCoursesOfStudent(int studentID) {
		return jdbcTemplate.query("SELECT * FROM school.students_courses WHERE student_id = ?;",
				BeanPropertyRowMapper.newInstance(StudentCourse.class), studentID);
	}

	@Override
	public int deleteStudentFromCourse(int studentID, int courseID) {
		return jdbcTemplate.update("DELETE FROM school.students_courses WHERE student_id = ? AND course_id = ?;",
				studentID, courseID);
	}

}
