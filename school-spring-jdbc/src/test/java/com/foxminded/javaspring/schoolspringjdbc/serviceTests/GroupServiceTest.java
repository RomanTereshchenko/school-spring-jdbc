package com.foxminded.javaspring.schoolspringjdbc.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcGroupDao;
import com.foxminded.javaspring.schoolspringjdbc.model.Group;
import com.foxminded.javaspring.schoolspringjdbc.service.GroupService;

@ActiveProfiles("test")
@SpringBootTest
class GroupServiceTest {

	@Autowired
	private GroupService groupService;
	@Autowired
	private JdbcGroupDao jdbcGroupDao;

	@Test
	void whenStudentCountIsProvided_thenRetrievedGroupNameIsCorrect() {
		List<Group> selectedGroups = new ArrayList<>();
		selectedGroups.add(new Group(1, "tt-11"));
		Mockito.when(((OngoingStubbing<List<Group>>) jdbcGroupDao.selectGroupsByStudentsCount(20))
				.thenReturn(selectedGroups));
		assertEquals("tt-11", groupService.findGroupsByStudentsCount().get(0).getGroupName());
	}

}
