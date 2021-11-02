package com.nttdata.nttdatadarmytasksms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.nttdatadarmytasksms.controller.Tasks;
import com.nttdata.nttdatadarmytasksms.repository.TasksListRepository;

@Service
public class TasksListService {
	
	@Autowired
	private TasksListRepository repository;
	
	public boolean checkTaskAlreadyExist(int id) {
		
		Optional<Tasks> task=repository.findById(id);
		return task.isPresent();
	
	}
	
	public Tasks getTaskByID(int id) {
		
		return repository.findById(id).get();
	}

}
