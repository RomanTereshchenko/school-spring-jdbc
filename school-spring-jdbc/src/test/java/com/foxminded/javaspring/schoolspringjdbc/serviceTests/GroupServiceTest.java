package com.foxminded.javaspring.schoolspringjdbc.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcGroupDao;
import com.foxminded.javaspring.schoolspringjdbc.model.Group;
import com.foxminded.javaspring.schoolspringjdbc.service.GroupService;
import com.foxminded.javaspring.schoolspringjdbc.utils.ScannerUtil;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

	@Mock
	private ScannerUtil scannerUtil;
	
	@Mock
	private JdbcGroupDao jdbcGroupDao;

	@InjectMocks
	private GroupService groupService;

	@Test
	void testFindGroupsByStudentsCount() {
		List<Group> selectedGroupsStub = new ArrayList<>();
		selectedGroupsStub.add(new Group(1, "tt-11"));
		Mockito.when(scannerUtil.scanInt()).thenReturn(20);
		Mockito.when(jdbcGroupDao.selectGroupsByStudentsCount(20)).thenReturn(selectedGroupsStub);
		assertEquals("tt-11", groupService.findGroupsByStudentsCount().get(0).getGroupName());
	}

}
