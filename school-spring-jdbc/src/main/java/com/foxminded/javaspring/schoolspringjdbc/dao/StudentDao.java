package com.foxminded.javaspring.schoolspringjdbc.dao;

import java.util.List;

import com.foxminded.javaspring.schoolspringjdbc.model.Student;

public interface StudentDao {
	
	int addStudentToDB(String firstName, String lastName);
	
	int deleteStudentFromDB(int studentID);
	
	int addGroupIDToStudentInDB (int groupID, String studentFirstName, String studentLastName);
	
	List<Student> findStudentsRelatedToCourse(String courseName);

}
