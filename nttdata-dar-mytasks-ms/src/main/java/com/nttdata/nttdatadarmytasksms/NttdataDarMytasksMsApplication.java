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
	
	/*@Override
	public void run(String[] args) {
		
		Tasks tas=repository.findById(1).get(); //Busca al dato con id=1 y lo guarda en tas
		System.out.println(tas.getTitle()+ "\n"); //De tas, pide el titulo
		
		Tasks task= new Tasks();
		task.setId(4);
		task.setTitle("Prueba 4");
		task.setDescription("Esta es la prueba desde Spring Boot");
		task.setHecho(false);
		repository.save(task); //Espera un entity, que en mi caso es Tasks.
		
		List<Tasks> alltasks=repository.findAll();
		
		for(Tasks t: alltasks) {
			
			System.out.println(t.getId());
			System.out.println(t.getTitle());
			System.out.println(t.getDescription());
			System.out.println(t.isHecho()+ "\n");
		}
		
		Tasks pruebadelete=new Tasks();
		pruebadelete=repository.getById(2);
		repository.delete(pruebadelete);
		
		alltasks=repository.findAll();
		
		for(Tasks t: alltasks) {
			
			System.out.println(t.getId());
			System.out.println(t.getTitle());
			System.out.println(t.getDescription());
			System.out.println(t.isHecho()+ "\n");
		}
		
	}*/

}
