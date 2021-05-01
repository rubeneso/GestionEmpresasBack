package com.GestionEmpresas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.googlecode.jmapper.api.JMapperAPI;

@SpringBootApplication
public class GestionEmpresasBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionEmpresasBackApplication.class, args);
	}
	
	@Bean
    public JMapperAPI modelJMapperApi() {
    	return new JMapperAPI();
    }

}
