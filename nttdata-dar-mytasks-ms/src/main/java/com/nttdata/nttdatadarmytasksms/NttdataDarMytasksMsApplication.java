package com.nttdata.nttdatadarmytasksms;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import com.nttdata.nttdatadarmytasksms.controller.Tasks;
import com.nttdata.nttdatadarmytasksms.repository.TasksListRepository;

@SpringBootApplication
public class NttdataDarMytasksMsApplication /*implements CommandLineRunner*/ {

	@Autowired
	TasksListRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(NttdataDarMytasksMsApplication.class, args);
	}

}
