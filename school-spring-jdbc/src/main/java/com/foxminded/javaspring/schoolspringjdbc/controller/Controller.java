package com.foxminded.javaspring.schoolspringjdbc.controller;

import static java.lang.System.exit;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcCourseDao;
import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcGroupDao;
import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcStudentDao;
import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcStudentsCoursesDao;
import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcTablesDao;
import com.foxminded.javaspring.schoolspringjdbc.generator.CourseGenerator;
import com.foxminded.javaspring.schoolspringjdbc.generator.GroupGenerator;
import com.foxminded.javaspring.schoolspringjdbc.generator.StudentGenerator;
import com.foxminded.javaspring.schoolspringjdbc.model.Course;
import com.foxminded.javaspring.schoolspringjdbc.model.Group;
import com.foxminded.javaspring.schoolspringjdbc.model.Student;
import com.foxminded.javaspring.schoolspringjdbc.model.StudentCourse;

@Component
public class Controller {

	private JdbcTablesDao jdbcTablesDao;
	private GroupGenerator groupGenerator;
	private JdbcGroupDao jdbcGroupDao;
	private JdbcCourseDao jdbcCourseDao;
	private StudentGenerator studentGenerator;
	private JdbcStudentDao jdbcStudentDao;
	private JdbcStudentsCoursesDao jdbcStudentsCoursesDao;
	private CourseGenerator courseGenerator;
	private Scanner scan = new Scanner(System.in);
	private int groupsNumber = 10;
	private int studentsNumber = 200;

	public static List<Group> groups = new ArrayList<>();
	public static List<Course> courses = new ArrayList<>();
	public static List<Student> students = new ArrayList<>();

	@Autowired
	public Controller(JdbcTablesDao jdbcTablesDao, GroupGenerator groupGenerator, JdbcGroupDao jdbcGroupDao,
			JdbcCourseDao jdbcCourseDao, StudentGenerator studentGenerator, JdbcStudentDao jdbcStudentDao,
			JdbcStudentsCoursesDao jdbcStudentsCoursesDao, CourseGenerator courseGenerator) {
		super();
		this.jdbcTablesDao = jdbcTablesDao;
		this.groupGenerator = groupGenerator;
		this.jdbcGroupDao = jdbcGroupDao;
		this.jdbcCourseDao = jdbcCourseDao;
		this.studentGenerator = studentGenerator;
		this.jdbcStudentDao = jdbcStudentDao;
		this.jdbcStudentsCoursesDao = jdbcStudentsCoursesDao;
		this.courseGenerator = courseGenerator;
	}

	public void startUp() {
//		jdbcTablesDao.truncateTables();
		groups = groupGenerator.generateNGroups(groupsNumber);
		System.out.println(groups);
		jdbcGroupDao.addAllGroupsToDB();
//		courses = courseGenerator.generateCourses();
//		jdbcCourseDao.addAllCoursesToDB();
//		students = studentGenerator.generateNStudents(studentsNumber);
//		jdbcStudentDao.addStudentsToDB();
//		studentGenerator.assignAllGroupsToAllItsStudents();
//		jdbcStudentDao.addGroupIDToAllTheirStudentsInDB();
//		studentGenerator.assignCoursesToAllStudents();
//		jdbcStudentsCoursesDao.addStudentsCoursesAssignmentsToDB();
	}

	public void menu() {
		String[] options = { "1 - Find all groups with less or equal students' number",
				"2 - Find all students related to the course with the given name", "3 - Add a new student",
				"4 - Delete a student by the STUDENT_ID", "5 - Add a student to the course (from a list)",
				"6 - Remove the student from one of their courses", "7 - Exit", };
		int option = 1;
		while (option != 7) {
			printMenu(options);
			try {
				option = scan.nextInt();
				switch (option) {
				case 1:
					findGroupsByStudentsCount();
					break;
				case 2:
					findStudentsRelatedToCourse();
					break;
				case 3:
					addNewStudent();
					break;
				case 4:
					deleteStudent();
					break;
				case 5:
					addStudentToCourse();
					break;
				case 6:
					removeStudentFromCourse();
					break;
				default:
					exit(0);
				}
			} catch (IllegalArgumentException e) {
				System.out.println("Please, enter an integer value between 1 and " + options.length);
				scan.next();
			}
		}
		scan.close();

	}

	private void printMenu(String[] options) {
		for (String option : options) {
			System.out.println(option);
		}
		System.out.println("Choose your option : ");
	}

	private void removeStudentFromCourse() {
		System.out.println("Remove the student from one of their courses");
		System.out.println("Enter the student ID");
		int studentIdToRemove = scan.nextInt();
		scan.nextLine();
		System.out.println("Enter the course ID");
		int courseIdToRemove = scan.nextInt();
		scan.nextLine();
		List<StudentCourse> studentCourses = jdbcStudentsCoursesDao.getCoursesOfStudent(studentIdToRemove);
		List<Integer> studentCoursesIDs = new ArrayList<>();
		for (StudentCourse studentCourse : studentCourses) {
			studentCoursesIDs.add(studentCourse.getCourseId());
		}
		if (!studentCoursesIDs.contains(courseIdToRemove)) {
			System.out.println("This course is not assigned to this student. Choose other student and course");
		} else {
			jdbcStudentsCoursesDao.deleteStudentFromCourse(studentIdToRemove, courseIdToRemove);
			System.out.println("Student with ID " + studentIdToRemove + " is removed from the course "
					+ courseIdToRemove + " in School database");
		}
	}

	private void addStudentToCourse() {
		System.out.println("Add a student to the course (from a list)");
		System.out.println("Enter the student ID");
		int studentId = scan.nextInt();
		scan.nextLine();
		System.out.println("Enter the course ID");
		int courseId = scan.nextInt();
		scan.nextLine();
		List<StudentCourse> studentCourses = jdbcStudentsCoursesDao.getCoursesOfStudent(studentId);
		List<Integer> studentCoursesIDs = new ArrayList<>();
		for (StudentCourse studentCourse : studentCourses) {
			studentCoursesIDs.add(studentCourse.getCourseId());
		}
		if (studentCoursesIDs.contains(courseId)) {
			System.out.println("This student is already assigned to this course. Choose other student and course.");
		} else {
			jdbcStudentsCoursesDao.addStudentCourseAssignmentInDB(studentId, courseId);
			System.out.println("Course with ID " + courseId + " is assigned to student with ID " + studentId
					+ " in School database");
		}
	}

	private void deleteStudent() {
		System.out.println("Delete a student by the STUDENT_ID");
		System.out.println("Enter the student ID");
		int studentIdToDelete = scan.nextInt();
		scan.nextLine();
		jdbcStudentDao.deleteStudentFromDB(studentIdToDelete);
		System.out.println("Student with ID " + studentIdToDelete + " is deleted from School database");
		System.out.println();
	}

	private void addNewStudent() {
		System.out.println("Add a new student");
		System.out.println("Enter the student first name");
		scan.nextLine();
		String firstName = scan.nextLine();
		System.out.println("Enter the student last name");
		String lastName = scan.nextLine();
		jdbcStudentDao.addStudentToDB(firstName, lastName);
		System.out.println("New student " + firstName + " " + lastName + " is added to School database");
		System.out.println();
	}

	private void findStudentsRelatedToCourse() {
		System.out.println("Find all students related to the course with the given name");
		System.out.println(
				"Enter a course name (Mathematics, Science, Health, Handwriting, Art, Music, Leadership, Speech, English, Algebra)");
		scan.nextLine();
		String courseName = scan.nextLine();
		List<Student> studentsOfCourse = jdbcStudentDao.findStudentsRelatedToCourse(courseName);
		for (Student student : studentsOfCourse) {
			System.out.println(student.getFirstName() + " " + student.getLastName());
		}
		System.out.println();
	}

	private void findGroupsByStudentsCount() {
		System.out.println("Find all groups with less or equal students� number: \n Enter a number between 10 and 30");
		int lessOrEqualNum = scan.nextInt();
		scan.nextLine();
		System.out.println(jdbcGroupDao.selectGroupsByStudentsCount(lessOrEqualNum));
		System.out.println();
	}

}
