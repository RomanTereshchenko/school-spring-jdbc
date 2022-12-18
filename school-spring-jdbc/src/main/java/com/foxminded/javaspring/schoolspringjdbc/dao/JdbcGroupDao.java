package com.foxminded.javaspring.schoolspringjdbc.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.foxminded.javaspring.schoolspringjdbc.controller.Controller;
import com.foxminded.javaspring.schoolspringjdbc.model.Group;

@Repository
public class JdbcGroupDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcGroupDao (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void addAllGroupsToDB() {
		Controller.groups.forEach(this::addGroupToDB);
		System.out.println("Groups added to School database");
	}

	public int addGroupToDB(Group group) {
		return jdbcTemplate.update("INSERT INTO school.groups(group_name) VALUES(?);", group.getGroupName());
	}

	public List<Group> selectGroupsByStudentsCount(int studentsCount) {
		return jdbcTemplate.query("SELECT g.group_id AS groupID, g.group_name AS groupName FROM "
				+ "school.groups g INNER JOIN school.students s ON g.group_id = s.group_id "
				+ "GROUP BY g.group_id HAVING COUNT (g.group_id) <= ?", 
				BeanPropertyRowMapper.newInstance(Group.class), studentsCount);
	}

}
