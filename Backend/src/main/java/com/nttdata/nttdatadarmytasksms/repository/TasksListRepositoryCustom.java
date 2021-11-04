package com.nttdata.nttdatadarmytasksms.repository;

import java.util.List;

import com.nttdata.nttdatadarmytasksms.controller.Tasks;

public interface TasksListRepositoryCustom {
	
	List<Tasks> findAllByTitle(String title);

}
