package com.foxminded.javaspring.schoolspringjdbc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcStudentDao;
import com.foxminded.javaspring.schoolspringjdbc.model.Course;
import com.foxminded.javaspring.schoolspringjdbc.model.Student;
import com.foxminded.javaspring.schoolspringjdbc.utils.ScannerUtil;

@Service
public class StudentService {

	private ScannerUtil scannerUtil;
	private JdbcStudentDao jdbcStudentDao;

	@Autowired
	public StudentService(ScannerUtil scannerUtil, JdbcStudentDao jdbcStudentDao) {
		this.scannerUtil = scannerUtil;
		this.jdbcStudentDao = jdbcStudentDao;
	}

	public List<Student> findStudentsRelatedToCourse() {
		System.out.println("Find all students related to the course with the given name");
		System.out.println("Enter a course name from the list: ");
		for (Course course : DBGeneratorService.courses) {
			System.out.println(course.getCourseName());
		}
		scannerUtil.scanString(); // When an instance of Scanner is taken from the context, it behaves as if nextInt()
							// has been previously called (the cursor is positioned before the newline character), 
							// so the newline character needs to be consumed by an extra scan.nextline()
		String courseName = scannerUtil.scanString();
		List<Student> studentsOfCourse = jdbcStudentDao.findStudentsRelatedToCourse(courseName);
		for (Student student : studentsOfCourse) {
			System.out.println(student.getFirstName() + " " + student.getLastName());
		}
		System.out.println();
		return studentsOfCourse;
	}

	public Map<String, String> addNewStudent() {
		System.out.println("Add a new student");
		System.out.println("Enter the student first name");
		scannerUtil.scanString();
		String firstName = scannerUtil.scanString();
		System.out.println("Enter the student last name");
		String lastName = scannerUtil.scanString();
		Map<String, String> addedStudentParametersMap = new HashMap<>();
		addedStudentParametersMap.put(firstName, lastName);
		jdbcStudentDao.addStudentToDB(new Student(firstName, lastName));
		System.out.println("New student " + firstName + " " + lastName + " is added to School database");
		System.out.println();
		return addedStudentParametersMap;
	}

	public int deleteStudent() {
		System.out.println("Delete a student by the STUDENT_ID");
		System.out.println("Enter the student ID");
		int studentIdToDelete = scannerUtil.scanInt();
		jdbcStudentDao.deleteStudentFromDB(studentIdToDelete);
		System.out.println("Student with ID " + studentIdToDelete + " is deleted from School database");
		System.out.println();
		return studentIdToDelete;
	}

}
