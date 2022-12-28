package com.foxminded.javaspring.schoolspringjdbc.serviceTests;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcCourseDao;
import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcGroupDao;
import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcStudentDao;
import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcStudentsCoursesDao;
import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcTablesDao;
import com.foxminded.javaspring.schoolspringjdbc.service.CourseGeneratorService;
import com.foxminded.javaspring.schoolspringjdbc.service.DBGeneratorService;
import com.foxminded.javaspring.schoolspringjdbc.service.GroupGeneratorService;
import com.foxminded.javaspring.schoolspringjdbc.service.StudentGeneratorService;

@ExtendWith (MockitoExtension.class)
class DBGeneratorServiceTest {
	
	@Mock
	private JdbcTablesDao jdbcTablesDao;
	@Mock
	private GroupGeneratorService groupGeneratorService;
	@Mock
	private JdbcGroupDao jdbcGroupDao;
	@Mock
	private CourseGeneratorService courseGeneratorService;
	@Mock
	private JdbcCourseDao jdbcCourseDao;
	@Mock
	private StudentGeneratorService studentGeneratorService;
	@Mock
	private JdbcStudentDao jdbcStudentDao;
	@Mock
	private JdbcStudentsCoursesDao jdbcStudentsCoursesDao;
	
	@InjectMocks
	private DBGeneratorService dbGeneratorService;
	
	@Test
	void testStartUp() {
		
		jdbcTablesDao.truncateTables();
		groupGeneratorService.generateNGroups(10);
		jdbcGroupDao.addAllGroupsToDB();
		courseGeneratorService.generateCourses();
		jdbcCourseDao.addAllCoursesToDB();
		studentGeneratorService.generateNStudents(20);
		jdbcStudentDao.addStudentsToDB();
		studentGeneratorService.assignAllGroupsToAllItsStudents();
		jdbcStudentDao.addGroupIDToAllTheirStudentsInDB();
		studentGeneratorService.assignCoursesToAllStudents();
		jdbcStudentsCoursesDao.addStudentsCoursesAssignmentsToDB();
		verify(jdbcTablesDao).truncateTables();
		verify(groupGeneratorService).generateNGroups(anyInt());
		verify(jdbcGroupDao).addAllGroupsToDB();
		verify(courseGeneratorService).generateCourses();
		verify(jdbcCourseDao).addAllCoursesToDB();
		verify(studentGeneratorService).generateNStudents(anyInt());
		verify(jdbcStudentDao).addStudentsToDB();
		verify(studentGeneratorService).assignAllGroupsToAllItsStudents();
		verify(jdbcStudentDao).addGroupIDToAllTheirStudentsInDB();
		verify(studentGeneratorService).assignCoursesToAllStudents();
		verify(jdbcStudentsCoursesDao).addStudentsCoursesAssignmentsToDB();
	}
	

}
