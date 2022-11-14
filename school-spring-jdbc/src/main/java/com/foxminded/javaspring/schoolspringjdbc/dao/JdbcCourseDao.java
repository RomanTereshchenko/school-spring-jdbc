package com.foxminded.javaspring.schoolspringjdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.foxminded.javaspring.schoolspringjdbc.controller.Controller;

public class JdbcCourseDao implements CourseDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void addAllCoursesToDB() {

		Controller.courses.forEach(course -> addCourseToDB(course.getCourseName()));
		System.out.println("Courses added to School database");
	}

	@Override
	public int addCourseToDB(String courseName) {
		return jdbcTemplate.update("INSERT INTO school.courses(course_name) VALUES (?);", courseName);
	}
	
	

}
