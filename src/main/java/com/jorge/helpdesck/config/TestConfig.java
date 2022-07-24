package com.jorge.helpdesck.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.jorge.helpdesck.services.DBService;

@Configuration
@Profile("test")  // informar que é perfil de test
public class TestConfig {
	
	@Autowired
	private DBService dbService;
	
	
	@Bean
	public void instanciaDB() {
	this.dbService.instanciaDB();

}
}