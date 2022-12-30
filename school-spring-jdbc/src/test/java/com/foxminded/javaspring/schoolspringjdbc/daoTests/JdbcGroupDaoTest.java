package com.foxminded.javaspring.schoolspringjdbc.daoTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.foxminded.javaspring.schoolspringjdbc.dao.JdbcGroupDao;
import com.foxminded.javaspring.schoolspringjdbc.model.Group;

@SpringBootTest
class JdbcGroupDaoTest {

	private JdbcGroupDao jdbcGroupDao;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcGroupDaoTest(JdbcGroupDao jdbcGroupDao, JdbcTemplate jdbcTemplate) {
		this.jdbcGroupDao = jdbcGroupDao;
		this.jdbcTemplate = jdbcTemplate;
	}

	@BeforeEach
	void truncateTables() {
		jdbcTemplate.execute("TRUNCATE TABLE school.groups CASCADE;");
		jdbcTemplate.execute("ALTER SEQUENCE school.groups_group_id_seq RESTART WITH 1;");
		jdbcTemplate.execute(
				"ALTER TABLE school.groups ALTER COLUMN group_id SET DEFAULT nextval('school.groups_group_id_seq');");
		jdbcTemplate.execute("TRUNCATE TABLE school.courses CASCADE;");
		jdbcTemplate.execute("ALTER SEQUENCE school.courses_course_id_seq RESTART WITH 1;");
		jdbcTemplate.execute(
				"ALTER TABLE school.courses ALTER COLUMN course_id SET DEFAULT nextval('school.courses_course_id_seq');");
		jdbcTemplate.execute("TRUNCATE TABLE school.students CASCADE;");
		jdbcTemplate.execute("ALTER SEQUENCE school.students_student_id_seq RESTART WITH 1;");
		jdbcTemplate.execute(
				"ALTER TABLE school.students ALTER COLUMN student_id SET DEFAULT nextval('school.students_student_id_seq');");
		jdbcTemplate.execute("TRUNCATE TABLE school.students_courses CASCADE;");
	}

	@Test
	void testAddGroupToDB() {
		jdbcGroupDao.addGroupToDB(new Group(1, "tt-00"));
		Group group = jdbcTemplate.queryForObject(
				"SELECT g.group_id AS groupID, g.group_name as groupName FROM school.groups g WHERE group_name = ?",
				BeanPropertyRowMapper.newInstance(Group.class), "tt-00");
		assertNotNull(group);
		assertEquals("tt-00", group.getGroupName());
	}

	@Test
	void selectGroupsByStudentsCount_ReturnsGropWithSelectedStudentCount() {
		jdbcGroupDao.addGroupToDB(new Group(1, "tt-00"));
		jdbcTemplate.update(
				"INSERT INTO school.students (group_id, first_name, last_name) VALUES (1, 'TestFName1', 'TestLName1');");
		jdbcTemplate.update(
				"INSERT INTO school.students (group_id, first_name, last_name) VALUES (1, 'TestFName2', 'TestLName2');");
		assertEquals("tt-00", jdbcGroupDao.selectGroupsByStudentsCount(2).get(0).getGroupName());
	}

}
