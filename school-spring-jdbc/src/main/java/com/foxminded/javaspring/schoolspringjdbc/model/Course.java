package com.foxminded.javaspring.schoolspringjdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Course {
	
	private int courseID;
	private String courseName;
	private String courseDescription;
	public Course(int courseID, String courseName) {
		this.courseID = courseID;
		this.courseName = courseName;
	}
	
}
