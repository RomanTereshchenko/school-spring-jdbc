package com.foxminded.javaspring.schoolspringjdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.foxminded.javaspring.schoolspringjdbc.controller.Controller;

@Repository
public class JdbcCourseDao implements CourseDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcCourseDao (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void addAllCoursesToDB() {

		Controller.courses.forEach(course -> addCourseToDB(course.getCourseName()));
		System.out.println("Courses added to School database");
	}

	@Override
	public int addCourseToDB(String courseName) {
		return jdbcTemplate.update("INSERT INTO school.courses(course_name) VALUES (?);", courseName);
	}
	
	

}
