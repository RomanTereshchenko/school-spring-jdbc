package com.foxminded.javaspring.schoolspringjdbc.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcStudentsCoursesDao;
import com.foxminded.javaspring.schoolspringjdbc.model.StudentCourse;
import com.foxminded.javaspring.schoolspringjdbc.service.StudentCourseService;
import com.foxminded.javaspring.schoolspringjdbc.utils.ScannerUtil;

@ExtendWith(MockitoExtension.class)
class StudentCourseServiceTest {

	@Mock
	private ScannerUtil scannerUtil;

	@Mock
	private JdbcStudentsCoursesDao jdbcStudentsCoursesDao;

	@InjectMocks
	private StudentCourseService studentCourseService;

	@Test
	void testAddStudentToCourse() {
		Mockito.when(scannerUtil.scanInt()).thenReturn(5);
		Mockito.when(jdbcStudentsCoursesDao.addStudentCourseAssignmentInDB(new StudentCourse(5, 5))).thenReturn(1);
		assertTrue(studentCourseService.addStudentToCourse().containsKey(5));
		assertEquals(5, studentCourseService.addStudentToCourse().get(5));
	}
	
	@Test
	void testRemoveStudentFromCourse() {
		Mockito.when(scannerUtil.scanInt()).thenReturn(1);
		assertTrue(studentCourseService.removeStudentFromCourse().containsKey(1));
		assertEquals(1, studentCourseService.removeStudentFromCourse().get(1));
	}

}
