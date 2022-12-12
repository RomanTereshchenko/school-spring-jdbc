package com.foxminded.javaspring.schoolspringjdbc.dao;

import java.util.List;

import com.foxminded.javaspring.schoolspringjdbc.model.Group;

public interface GroupDao {
	
	int addGroupToDB(String groupName);
	
	List<Group> selectGroupsByStudentsCount (int studentsCount);

}
