package com.foxminded.javaspring.schoolspringjdbc.dao;

import java.util.List;

import com.foxminded.javaspring.schoolspringjdbc.model.StudentCourse;

public interface StudentsCoursesDao {
	
	int addStudentCourseAssignmentInDB (int studentID, int courseID);
	
	List<StudentCourse> getCoursesOfStudent (int studentID);
	
	int deleteStudentFromCourse (int studentID, int courseID);

}
