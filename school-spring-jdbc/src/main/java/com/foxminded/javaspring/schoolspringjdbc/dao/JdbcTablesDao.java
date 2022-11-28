package com.foxminded.javaspring.schoolspringjdbc.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
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
	public void createSchemaAndTables() {
		try {
			String initDBScript = Files.lines(Paths.get(getClass().getClassLoader().getResource("initDB.sql").toURI()))
					.collect(Collectors.joining(""));
			jdbcTemplate.update(initDBScript);
			System.out.println("School database tables created");
		} catch (IOException | URISyntaxException e) {
			System.err.println("Exception occurred during DB init: " + e.getClass().getName());
		}
	}
}
