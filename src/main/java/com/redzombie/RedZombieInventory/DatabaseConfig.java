package com.redzombie.RedZombieInventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DatabaseConfig{
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	private final ApplicationContext applicationContext;
	
	@Autowired
	public DatabaseConfig(NamedParameterJdbcTemplate jdbcTemplate, 
			ApplicationContext applicationContext) {
		this.jdbcTemplate = jdbcTemplate;
		this.applicationContext = applicationContext;
	}
	
	
	
}
