package com.foxminded.javaspring.schoolspringjdbc.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcStudentDao;
import com.foxminded.javaspring.schoolspringjdbc.model.Student;
import com.foxminded.javaspring.schoolspringjdbc.service.StudentService;
import com.foxminded.javaspring.schoolspringjdbc.utils.ScannerUtil;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

	@Mock
	private ScannerUtil scannerUtil;

	@Mock
	private JdbcStudentDao jdbcStudentDao;

	@InjectMocks
	private StudentService studentService;

	@Test
	void testFindStudentsRelatedToCourse() {
		List<Student> studentsOfCourse = new ArrayList<>();
		studentsOfCourse.add(new Student("TestFirstName", "TestLastName"));
		Mockito.when(scannerUtil.scanString()).thenReturn("TestCourse");
		Mockito.when(jdbcStudentDao.findStudentsRelatedToCourse("TestCourse")).thenReturn(studentsOfCourse);
		List<Student> result = studentService.findStudentsRelatedToCourse();
		assertEquals("TestFirstName", result.get(0).getFirstName());
		assertEquals("TestLastName", result.get(0).getLastName());
	}

	@Test
	void testAddNewStudent() {
		Mockito.when(scannerUtil.scanString()).thenReturn("TestName");
		Mockito.when(jdbcStudentDao.addStudentToDB(any(Student.class))).thenReturn(1);
		studentService.addNewStudent();
		verify(jdbcStudentDao).addStudentToDB(any(Student.class));
	}

	@Test
	void testDeleteStudent() {
		Mockito.when(scannerUtil.scanInt()).thenReturn(1);
		Mockito.when(jdbcStudentDao.deleteStudentFromDB(1)).thenReturn(1);
		studentService.deleteStudent();
		verify(jdbcStudentDao).deleteStudentFromDB(anyInt());
	}
}
