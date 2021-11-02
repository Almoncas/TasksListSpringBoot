package com.nttdata.nttdatadarmytasksms.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nttdata.nttdatadarmytasksms.controller.Tasks;

public class TasksListRepositoryImpl implements TasksListRepositoryCustom {

	@Autowired
	TasksListRepository repository;
	
	@Override
	public List<Tasks> findAllByTitle(String title) { 
		//Realmente este metodo aqui no tiene mucho sentido, tiene mas sentido cuando un autor tiene muchos libros,
		//pero es raro que varias tareas tengan el mismo titulo
		List<Tasks> tasksWithTitle=new ArrayList<Tasks>();
		
		// TODO Auto-generated method stub
		List<Tasks> alltasks=repository.findAll();
		
		for(Tasks t:alltasks) {
			
			if(t.getTitle().equalsIgnoreCase(title)) {
				tasksWithTitle.add(t);//Lo mete en la nueva lista
			}
			
			
		}
		
		
		return tasksWithTitle;
	}
	

}
