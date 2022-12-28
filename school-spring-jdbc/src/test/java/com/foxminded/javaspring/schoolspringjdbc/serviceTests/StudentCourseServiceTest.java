package com.foxminded.javaspring.schoolspringjdbc.serviceTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcStudentsCoursesDao;
import com.foxminded.javaspring.schoolspringjdbc.model.Course;
import com.foxminded.javaspring.schoolspringjdbc.model.StudentCourse;
import com.foxminded.javaspring.schoolspringjdbc.service.DBGeneratorService;
import com.foxminded.javaspring.schoolspringjdbc.service.StudentCourseService;
import com.foxminded.javaspring.schoolspringjdbc.utils.ScannerUtil;

@ExtendWith(MockitoExtension.class)
class StudentCourseServiceTest {

	@Mock
	private ScannerUtil scannerUtil;

	@Mock
	private JdbcStudentsCoursesDao jdbcStudentsCoursesDao;
	
	@Mock
	private DBGeneratorService dbGeneratorService;

	@InjectMocks
	private StudentCourseService studentCourseService;
	
	@Test
	void testAddStudentToCourse() {
		int courseAndStudentId = 5;
		Mockito.when(scannerUtil.scanInt()).thenReturn(courseAndStudentId);
		Mockito.when(jdbcStudentsCoursesDao.addStudentCourseAssignmentInDB(any(StudentCourse.class))).thenReturn(1);
		studentCourseService.addStudentToCourse();
		verify(scannerUtil, times(2)).scanInt();
		verify(jdbcStudentsCoursesDao).getCoursesOfStudent(courseAndStudentId);
		verify(jdbcStudentsCoursesDao).addStudentCourseAssignmentInDB(any(StudentCourse.class));
	}
	
	@Test
	void testRemoveStudentFromCourse() {
		int courseAndStudentId = 1;
		DBGeneratorService.courses.add(new Course (1, "TestCourseName"));
		List<StudentCourse> coursesOfStudent = new ArrayList<>();
		coursesOfStudent.add(new StudentCourse(1, 1));
		Mockito.when(scannerUtil.scanInt()).thenReturn(courseAndStudentId);
		Mockito.when(jdbcStudentsCoursesDao.deleteStudentFromCourse(anyInt(), anyInt())).thenReturn(1);
		Mockito.when(jdbcStudentsCoursesDao.getCoursesOfStudent(anyInt())).thenReturn(coursesOfStudent);
		studentCourseService.removeStudentFromCourse();
		verify(scannerUtil, times(2)).scanInt();
		verify(jdbcStudentsCoursesDao).getCoursesOfStudent(courseAndStudentId);
		System.out.println(coursesOfStudent.get(0).getCourseId());
		System.out.println();
		verify(jdbcStudentsCoursesDao).deleteStudentFromCourse(anyInt(), anyInt());
		DBGeneratorService.courses.clear();
	}


}
