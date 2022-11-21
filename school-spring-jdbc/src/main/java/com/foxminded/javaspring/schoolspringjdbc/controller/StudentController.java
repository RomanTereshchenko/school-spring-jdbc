package com.foxminded.javaspring.schoolspringjdbc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foxminded.javaspring.schoolspringjdbc.dao.StudentDao;
import com.foxminded.javaspring.schoolspringjdbc.model.Student;

@RestController

public class StudentController {

	StudentDao studentDao;

	@Autowired
	public StudentController(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	@GetMapping("/studentsOfCourse")
	public ResponseEntity<List<Student>> getStudentsRelatedToCourse(@RequestParam String courseName) {
		try {
			List<Student> studentsOfCourse = new ArrayList<>();
			studentDao.findStudentsRelatedToCourse(courseName);
			return new ResponseEntity<List<Student>>(studentsOfCourse, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
