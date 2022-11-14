package com.foxminded.javaspring.schoolspringjdbc.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.foxminded.javaspring.schoolspringjdbc.controller.Controller;
import com.foxminded.javaspring.schoolspringjdbc.model.Group;

@Repository
public class JdbcGroupDao implements GroupDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void addAllGroupsToDB() {
		Controller.groups.forEach(group -> addGroupToDB(group.getGroupName()));
		System.out.println("Groups added to School database");
	}

	@Override
	public int addGroupToDB(String groupName) {
		return jdbcTemplate.update("INSERT INTO school.groups(group_name) VALUES(?);", groupName);
	}

	@Override
	public List<Group> selectGroupsByStudentsCount(int studentsCount) {
		return jdbcTemplate.query("SELECT school.groups.group_id, school.groups.group_name FROM "
				+ "(school.groups inner join school.students ON school.groups.group_id = school.students.group_id) "
				+ "GROUP BY school.groups.group_id HAVING COUNT (school.groups.group_id) <= ?", 
				BeanPropertyRowMapper.newInstance(Group.class), studentsCount);
	}

}
