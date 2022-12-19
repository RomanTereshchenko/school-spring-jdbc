package com.foxminded.javaspring.schoolspringjdbc.model;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor

public class Student {

	private int studentID;
	private int groupID;
	private String firstName;
	private String lastName;
	private List<Course> courses;

	public Student(int studentID, String firstName, String lastName) {
		this.studentID = studentID;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student(int studentID, int groupID) {
		this.studentID = studentID;
		this.groupID = groupID;
	}

}
