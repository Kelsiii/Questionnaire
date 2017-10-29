package com.Questionnaire.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentService{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(readOnly = true)
	public List getAllDepartments() {
		List departments = jdbcTemplate.queryForList("SELECT name FROM department"); 
		// TODO Auto-generated method stub
		return departments;
	}

}
