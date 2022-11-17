package com.foxminded.javaspring.schoolspringjdbc.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.foxminded.javaspring.schoolspringjdbc.model.Course;

public class CourseGenerator {
	
	private final List<String> courseNames = Arrays.asList("Mathematics", "Science", "Health", "Handwriting", "Art",
	"Music", "Leadership", "Speech", "English", "Algebra");

	public List<Course> generateCourses() {

		List<Course> coursesLocal = new ArrayList<>();

		for (int i = 0; i < courseNames.size(); i++) {
			coursesLocal.add(new Course((i + 1), courseNames.get(i)));
		}
		
		System.out.println("Courses generated");
		return coursesLocal;

	}

}
