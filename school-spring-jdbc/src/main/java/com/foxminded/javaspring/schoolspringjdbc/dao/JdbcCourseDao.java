package com.foxminded.javaspring.schoolspringjdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.foxminded.javaspring.schoolspringjdbc.model.Course;
import com.foxminded.javaspring.schoolspringjdbc.service.DBGeneratorService;

@Repository
public class JdbcCourseDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcCourseDao (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void addAllCoursesToDB() {

		DBGeneratorService.courses.forEach(this::addCourseToDB);
		System.out.println("Courses added to School database");
	}

	public int addCourseToDB(Course course) {
		return jdbcTemplate.update("INSERT INTO school.courses(course_name) VALUES (?);", course.getCourseName());
	}
	
	

}
