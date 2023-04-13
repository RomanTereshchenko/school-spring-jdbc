package com.foxminded.javaspring.schoolspringjdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.javaspring.schoolspringjdbc.model.Course;
import com.foxminded.javaspring.schoolspringjdbc.service.DBGeneratorService;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class JdbcCourseDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcCourseDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional
	public void addAllCoursesToDB() {
		DBGeneratorService.courses.forEach(this::addCourseToDB);
		log.info("Courses added to School database");
	}

	public int addCourseToDB(Course course) {
		return jdbcTemplate.update("INSERT INTO school.courses(course_name) VALUES (?);", course.getCourseName());
	}

}
