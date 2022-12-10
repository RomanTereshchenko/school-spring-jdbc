package com.foxminded.javaspring.schoolspringjdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTablesDao implements TablesDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcTablesDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void truncateTables() {
	jdbcTemplate.execute("TRUNCATE TABLE school.groups CASCADE;");
	jdbcTemplate.execute("TRUNCATE TABLE school.courses CASCADE;");
	jdbcTemplate.execute("TRUNCATE TABLE school.students CASCADE;");
	jdbcTemplate.execute("TRUNCATE TABLE school.students_courses CASCADE;");
	}
}
