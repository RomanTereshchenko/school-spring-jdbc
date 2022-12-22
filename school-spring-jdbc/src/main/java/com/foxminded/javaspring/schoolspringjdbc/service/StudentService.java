package com.foxminded.javaspring.schoolspringjdbc.service;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcStudentDao;
import com.foxminded.javaspring.schoolspringjdbc.model.Course;
import com.foxminded.javaspring.schoolspringjdbc.model.Student;

@Service
public class StudentService {
	
	private Scanner scan = new Scanner(System.in);
	private ScannerUtil scannerUtil;
	private JdbcStudentDao jdbcStudentDao;

	@Autowired
	public StudentService(ScannerUtil scannerUtil, JdbcStudentDao jdbcStudentDao) {
		this.scannerUtil = scannerUtil;
		this.jdbcStudentDao = jdbcStudentDao;
	}

	public void findStudentsRelatedToCourse() {
		System.out.println("Find all students related to the course with the given name");
		System.out.println("Enter a course name from the list: ");
		for (Course course : DBGeneratorService.courses) {
			System.out.println(course.getCourseName());
		}
		String courseName = scan.nextLine();
		List<Student> studentsOfCourse = jdbcStudentDao.findStudentsRelatedToCourse(courseName);
		for (Student student : studentsOfCourse) {
			System.out.println(student.getFirstName() + " " + student.getLastName());
		}
		System.out.println();
	}

	public void addNewStudent() {
		System.out.println("Add a new student");
		System.out.println("Enter the student first name");
		String firstName = scan.nextLine();
		System.out.println("Enter the student last name");
		String lastName = scan.nextLine();
		jdbcStudentDao.addStudentToDB(new Student(firstName, lastName));
		System.out.println("New student " + firstName + " " + lastName + " is added to School database");
		System.out.println();
	}

	public void deleteStudent() {
		System.out.println("Delete a student by the STUDENT_ID");
		System.out.println("Enter the student ID");
		int studentIdToDelete = scannerUtil.scanInt();
		jdbcStudentDao.deleteStudentFromDB(studentIdToDelete);
		System.out.println("Student with ID " + studentIdToDelete + " is deleted from School database");
		System.out.println();
	}


}
