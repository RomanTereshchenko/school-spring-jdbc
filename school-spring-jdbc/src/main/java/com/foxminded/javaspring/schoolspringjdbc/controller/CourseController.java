package com.foxminded.javaspring.schoolspringjdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.foxminded.javaspring.schoolspringjdbc.dao.CourseDao;

@RestController

public class CourseController {
	
CourseDao courseDao;

@Autowired
public CourseController(CourseDao courseDao) {
	this.courseDao = courseDao;
}



}
