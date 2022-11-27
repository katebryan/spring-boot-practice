package com.example.practice;

import com.example.practice.dao.ApplicationDao;
import com.example.practice.models.UniversityStudent;
import com.example.practice.service.ApplicationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class MvcTestingPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcTestingPracticeApplication.class, args);
	}

	@Bean(name = "applicationExample")
	ApplicationService getApplicationService() {
		return new ApplicationService();
	}

	@Bean(name = "applicationDao")
	ApplicationDao getApplicationDao() {
		return new ApplicationDao();
	}

	@Bean(name = "universityStudent")
	@Scope(value = "prototype")
	UniversityStudent getUniversityStudent() {
		return new UniversityStudent();
	}

}
