package com.foxminded.javaspring.schoolspringjdbc.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.foxminded.javaspring.schoolspringjdbc.model.Course;

@Service
public class CourseGeneratorService {
	
	public final List<String> courseNames = Arrays.asList("Mathematics", "Science", "Health", "Handwriting", "Art",
	"Music", "Leadership", "Speech", "English", "Algebra");

	public List<Course> generateCourses() {

		List<Course> coursesLocal = new ArrayList<>();

		for (int i = 1; i <= courseNames.size(); i++) {
			coursesLocal.add(new Course((i), courseNames.get(i-1)));
		}
		
		System.out.println("Courses generated");
		return coursesLocal;

	}

}
