package com.foxminded.javaspring.schoolspringjdbc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcStudentsCoursesDao;
import com.foxminded.javaspring.schoolspringjdbc.model.Course;
import com.foxminded.javaspring.schoolspringjdbc.model.StudentCourse;
import com.foxminded.javaspring.schoolspringjdbc.utils.ScannerUtil;

@Service
public class StudentCourseService {

	private ScannerUtil scannerUtil;
	private JdbcStudentsCoursesDao jdbcStudentsCoursesDao;

	@Autowired
	public StudentCourseService(JdbcStudentsCoursesDao jdbcStudentsCoursesDao, ScannerUtil scannerUtil) {
		this.jdbcStudentsCoursesDao = jdbcStudentsCoursesDao;
		this.scannerUtil = scannerUtil;
	}

	public void addStudentToCourse() {
		System.out.println("Add a student to the course (from a list)");
		System.out.println("Enter the student ID");
		int studentId = scannerUtil.scanInt();
		System.out.println("The available courses are:");
		for (Course course : DBGeneratorService.courses) {
			System.out.println(course.getCourseID() + " - " + course.getCourseName());
		}
		System.out.println("Enter the course ID");
		int courseId = scannerUtil.scanInt();
		List<StudentCourse> studentCourses = jdbcStudentsCoursesDao.getCoursesOfStudent(studentId);
		for (StudentCourse studentCourse : studentCourses) {
			if (studentCourse.getCourseId() == courseId) {
				System.out.println("This student is already assigned to this course. Choose other student and course.");
				return;
			}
		}
		jdbcStudentsCoursesDao.addStudentCourseAssignmentInDB(new StudentCourse(studentId, courseId));
		System.out.println(
				"Course with ID " + courseId + " is assigned to student with ID " + studentId + " in School database");
		return;
	}

	public void removeStudentFromCourse() {
		System.out.println("Remove the student from one of their courses");
		System.out.println("Enter the student ID");
		int studentIdToRemove = scannerUtil.scanInt();
		List<StudentCourse> studentCourses = jdbcStudentsCoursesDao.getCoursesOfStudent(studentIdToRemove);
		System.out.println("This student is assigned to the following courses:");
		for (StudentCourse studentCourse : studentCourses) {
			System.out.println(studentCourse.getCourseId() + " - "
					+ DBGeneratorService.courses.get(studentCourse.getCourseId() - 1).getCourseName());
		}
		System.out.println("Enter the course ID, from which to remove this student");
		int courseIdToRemove = scannerUtil.scanInt();
		for (StudentCourse studentCourse : studentCourses) {
			if (studentCourse.getCourseId() == courseIdToRemove) {
				jdbcStudentsCoursesDao.deleteStudentFromCourse(studentIdToRemove, courseIdToRemove);
				System.out.println("Student with ID " + studentIdToRemove + " is removed from the course "
						+ courseIdToRemove + " in School database");
				return;
			}
		}
		System.out.println("This course is not assigned to this student. Choose other student and course");
		return;
	}

}
