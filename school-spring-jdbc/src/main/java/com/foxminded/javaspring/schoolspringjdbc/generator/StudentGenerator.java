package com.foxminded.javaspring.schoolspringjdbc.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.foxminded.javaspring.schoolspringjdbc.controller.Controller;
import com.foxminded.javaspring.schoolspringjdbc.model.Course;
import com.foxminded.javaspring.schoolspringjdbc.model.Student;

public class StudentGenerator {

	private Random random = new Random();
	private int nextUnassignedStudentID = 0;
	List<String> studentFirstNames = Arrays.asList("Lexi", "Elouise", "Wilbur", "Glenda", "Judah", "Salahuddin",
	"Juliet", "Tanner", "Luella", "Enid", "Hadiya", "Rares", "Bryan", "Patsy", "Eshan", "Lester", "Bentley",
	"Yu", "Finlay", "Sylvie");
	List<String> studentLastNames = Arrays.asList("Ferry", "Buck", "Moody", "Craft", "Ridley", "Aguilar",
	"Garrett", "Peralta", "Mcknight", "O'Quinn", "Simons", "Kelley", "Trejo", "Dougherty", "Palacios", "Murphy",
	"Gordon", "Mcgee", "Strong", "Philip");
	
	public List<Student> generateNStudents(int countToGenerate) {
		List<Student> studentsLocal = new ArrayList<>();
		IntStream.rangeClosed(1, countToGenerate)
				.forEach(studentID -> studentsLocal.add(new Student(studentID,
						studentFirstNames.get(random.nextInt(studentFirstNames.size())),
						studentLastNames.get(random.nextInt(studentLastNames.size())))));
		System.out.println("Students generated");
		return studentsLocal;
	}

	public void assignAllGroupsToAllItsStudents() {
		while (nextUnassignedStudentID < Controller.students.size()) {
			IntStream.rangeClosed(1, Controller.groups.size()).forEach(this::assignGroupToStudents);
		}
		System.out.println("Students assigned with groups");
	}

	private void assignGroupToStudents(int groupID) {
		int limitOfStudentsInGroup = ((random.nextInt(20)) + 10);
		int numberOfStudentsInGroup = 0;
		while ((numberOfStudentsInGroup < limitOfStudentsInGroup)
				&& (nextUnassignedStudentID < Controller.students.size())) {
			Controller.students.get(nextUnassignedStudentID).setGroupID(groupID);
			System.out.println("Student " + nextUnassignedStudentID + " assigned to group " + groupID);
			numberOfStudentsInGroup++;
			nextUnassignedStudentID++;
		}
	}

	public void assignCoursesToAllStudents() {
		IntStream.rangeClosed(0, (Controller.students.size() - 1)).forEach(this::assignCoursesToOneStudent);
		System.out.println("Students assigned with lists of courses");
	}

	private void assignCoursesToOneStudent(int nextStudentID) {
		final int maxNumberOfCourses = 3;
		int numberOfCoursesLimit = (random.nextInt(maxNumberOfCourses - 1) + 1);
		int numberOfAssignedCourses = 1;
		ArrayList<Integer> courseIDs = new ArrayList<>();
		for (int i = 0; i < Controller.courses.size(); i++)
			courseIDs.add(i);
		Collections.shuffle(courseIDs);
		int randomCourseIDIndex = 0;
		List<Course> coursesOfStudent = new ArrayList<>();
		while (numberOfAssignedCourses <= numberOfCoursesLimit) {
			coursesOfStudent.add(Controller.courses.get(courseIDs.get(randomCourseIDIndex)));
			randomCourseIDIndex++;
			numberOfAssignedCourses++;
		}
		Controller.students.get(nextStudentID).setCourses(coursesOfStudent);
	}

}
