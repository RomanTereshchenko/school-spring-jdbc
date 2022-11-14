package com.foxminded.javaspring.schoolspringjdbc.controller;

import static java.lang.System.exit;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

public class Controller {

	private JdbcTablesDao jdbcTablesDao = new JdbcTablesDao();
	private GroupGenerator groupGenerator = new GroupGenerator();
	private JdbcGroupDao jdbcGroupDao = new JdbcGroupDao();
	private JdbcCourseDao jdbcCourseDao = new JdbcCourseDao();
	private StudentGenerator studentGenerator = new StudentGenerator();
	private JdbcStudentDao jdbcStudentDao = new JdbcStudentDao();
	private JdbcStudentsCoursesDao jdbcStudentsCoursesDao = new JdbcStudentsCoursesDao();
	private CourseGenerator courseGenerator = new CourseGenerator();
	private Scanner scan = new Scanner(System.in);
	private int groupsNumber = 10;
	private int studentsNumber = 200;
	
	public static List<Group> groups = new ArrayList<>();
	public static List<Course> courses = new ArrayList<>();
	public static List<Student> students = new ArrayList<>();

	public void startUp() {
		jdbcTablesDao.createSchemaAndTables();
		groups = groupGenerator.generateNGroups(groupsNumber);
		jdbcGroupDao.addAllGroupsToDB();
		courses = courseGenerator.generateCourses();
		jdbcCourseDao.addAllCoursesToDB();
		students = studentGenerator.generateNStudents(studentsNumber);
		jdbcStudentDao.addStudentsToDB();
		studentGenerator.assignAllGroupsToAllItsStudents();
		jdbcStudentDao.addGroupIDToAllTheirStudentsInDB();
		studentGenerator.assignCoursesToAllStudents();
		jdbcStudentsCoursesDao.addStudentsCoursesAssignmentsToDB();
	}

	public void menu() {
		String[] options = { "1 - Find all groups with less or equal students� number",
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
			} catch (Exception e) {
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
		jdbcStudentsCoursesDao.deleteStudentFromCourse(studentIdToRemove, courseIdToRemove);
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
			System.out.println("This student is already addigned to this course. Choose other student and course.");
		} else
			jdbcStudentsCoursesDao.addStudentCourseAssignmentInDB(studentId, courseId);
		System.out.println();
	}

	private void deleteStudent() {
		System.out.println("Delete a student by the STUDENT_ID");
		System.out.println("Enter the student ID");
		int studentIdToDelete = scan.nextInt();
		scan.nextLine();
		jdbcStudentDao.deleteStudentFromDB(studentIdToDelete);
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
		System.out.println();
	}

	private void findStudentsRelatedToCourse() {
		System.out.println("Find all students related to the course with the given name");
		System.out.println(
				"Enter a course name (Mathematics, Science, Health, Handwriting, Art, Music, Leadership, Speech, English, Algebra)");
		scan.nextLine();
		String courseName = scan.nextLine();
		List<Student> studentsOfCourse = jdbcStudentDao.getStudentsRelatedToCourse(courseName);
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
