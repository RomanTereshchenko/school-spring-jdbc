package com.foxminded.javaspring.schoolspringjdbc.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.javaspring.schoolspringjdbc.model.Group;
import com.foxminded.javaspring.schoolspringjdbc.service.DBGeneratorService;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class JdbcGroupDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcGroupDao (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional
	public void addAllGroupsToDB() {
		DBGeneratorService.groups.forEach(this::addGroupToDB);
		log.info("Groups added to School database");
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
