package com.nttdata.nttdatadarmytasksms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nttdata.nttdatadarmytasksms.controller.Tasks;


//@Repository No es necesaria esta instrucción
public interface TasksListRepository extends JpaRepository<Tasks, Integer>, TasksListRepositoryCustom {
	
	List<Tasks> findAllByTitle(String title);

}
