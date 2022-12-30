package com.foxminded.javaspring.schoolspringjdbc.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		List<Student> studentsOfCourseStub = new ArrayList<>();
		studentsOfCourseStub.add(new Student("TestFirstName", "TestLastName"));
		Mockito.when(scannerUtil.scanString()).thenReturn("Mathematics");
		Mockito.when(jdbcStudentDao.findStudentsRelatedToCourse("Mathematics")).thenReturn(studentsOfCourseStub);
		assertEquals("TestFirstName", studentService.findStudentsRelatedToCourse().get(0).getFirstName());
		assertEquals("TestLastName", studentService.findStudentsRelatedToCourse().get(0).getLastName());
	}

	@Test
	void testAddNewStudent() {
		Mockito.when(scannerUtil.scanString()).thenReturn("TestName");
		Mockito.when(jdbcStudentDao.addStudentToDB(new Student("TestName", "TestName"))).thenReturn(1);
		assertTrue(studentService.addNewStudent().containsKey("TestName"));
		assertEquals("TestName", (studentService.addNewStudent().get("TestName")));
	}

	@Test
	void testDeleteStudent() {
		Mockito.when(scannerUtil.scanInt()).thenReturn(1);
		Mockito.when(jdbcStudentDao.deleteStudentFromDB(1)).thenReturn(1);
		assertEquals(1, studentService.deleteStudent());
	}
}
