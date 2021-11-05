package com.nttdata.nttdatadarmytasksms;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//import com.nttdata.nttdatadarmytasksms.controller.Tasks;
import com.nttdata.nttdatadarmytasksms.repository.TasksListRepository;

@SpringBootApplication
public class NttdataDarMytasksMsApplication  {

	@Autowired
	TasksListRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(NttdataDarMytasksMsApplication.class, args);
	}
	
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:8080");
			}
		};
	}

}
