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

//	@Override
//	public void createSchemaAndTables() {
//		jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS school AUTHORIZATION postgres;");
//		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS school.courses "
//				+ "(course_id SERIAL PRIMARY KEY, course_name VARCHAR(20) NOT NULL, course_description VARCHAR(200));");
//		jdbcTemplate
//				.execute("CREATE TABLE IF NOT EXISTS school.groups (group_id SERIAL PRIMARY KEY, group_name VARCHAR(5) NOT NULL);");
//		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS school.students (student_id SERIAL PRIMARY KEY, group_id INT, "
//				+ "first_name VARCHAR(20) NOT NULL, last_name VARCHAR(20) NOT NULL, FOREIGN KEY (group_id) "
//				+ "REFERENCES school.groups(group_id) ON DELETE SET NULL);");
//		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS school.students_courses (student_id INT NOT NULL, course_id INT NOT NULL, "
//				+ "FOREIGN KEY (student_id) REFERENCES school.students(student_id), "
//				+ "FOREIGN KEY (course_id) REFERENCES school.courses(course_id), UNIQUE (student_id, course_id) );");
//		System.out.println("School database tables created");
//	}

	@Override
	public void createSchemaAndTables() {
		jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS school AUTHORIZATION postgres;");
		jdbcTemplate.execute("DROP TABLE IF EXISTS school.courses CASCADE;");
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS school.courses "
				+ "(course_id SERIAL PRIMARY KEY, course_name VARCHAR(20) NOT NULL, course_description VARCHAR(200));");
		jdbcTemplate.execute("DROP TABLE IF EXISTS school.groups CASCADE;");
		jdbcTemplate.execute(
				"CREATE TABLE IF NOT EXISTS school.groups (group_id SERIAL PRIMARY KEY, group_name VARCHAR(5) NOT NULL);");
		jdbcTemplate.execute("DROP TABLE IF EXISTS school.students CASCADE;");
		jdbcTemplate.execute(
				"CREATE TABLE IF NOT EXISTS school.students (student_id SERIAL PRIMARY KEY, group_id INT, "
				+ "first_name VARCHAR(20) NOT NULL, last_name VARCHAR(20) NOT NULL, FOREIGN KEY (group_id) "
				+ "REFERENCES school.groups(group_id) ON DELETE SET NULL);");
		jdbcTemplate.execute("DROP TABLE IF EXISTS school.students_courses CASCADE;");
		jdbcTemplate.execute(
				"CREATE TABLE IF NOT EXISTS school.students_courses (student_id INT NOT NULL, course_id INT NOT NULL, "
				+ "FOREIGN KEY (student_id) REFERENCES school.students(student_id), "
				+ "FOREIGN KEY (course_id) REFERENCES school.courses(course_id), UNIQUE (student_id, course_id) );");
		System.out.println("School database tables created");
	}
}
