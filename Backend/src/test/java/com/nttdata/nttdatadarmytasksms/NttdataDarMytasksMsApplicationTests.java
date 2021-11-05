package com.nttdata.nttdatadarmytasksms;

//import static org.hamcrest.CoreMatchers.is; Me salta error al importar este paquete: paquete de .is(1)

import static org.mockito.Mockito.*;
//import static org.mockito.Matchers.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.nttdatadarmytasksms.controller.AddResponse;
import com.nttdata.nttdatadarmytasksms.controller.Progreso;
import com.nttdata.nttdatadarmytasksms.controller.TaskController;
import com.nttdata.nttdatadarmytasksms.controller.Tasks;
import com.nttdata.nttdatadarmytasksms.repository.TasksListRepository;
import com.nttdata.nttdatadarmytasksms.service.TasksListService;

@SpringBootTest
@AutoConfigureMockMvc

class NttdataDarMytasksMsApplicationTests {
	
	@Autowired
	TaskController con;
	@Autowired
	private MockMvc mockMvc;

	
	@MockBean
	TasksListRepository repository;
	@MockBean
	TasksListService tasksListService;
	//Lo que pongamos aqui funcionara como una clase falsa, por lo que si luego en el codigo tenemos que usarlo,
	//tenemos que especificar qué devolveria con when(...).thenReturn(...)
	
	
	

	@Test
	void contextLoads() {
	}
	
	@Test
	public void addTaskImplementationTest() {
		
		//mock
		Tasks task= buildTask();
		when(tasksListService.checkTaskAlreadyExist(task.getId())).thenReturn(true);
		
		//Test del código de envío HTTP
		ResponseEntity<AddResponse> response=con.addTaskImplementation(buildTask());
		System.out.println(response.getStatusCodeValue());
		assertEquals(response.getStatusCode(),HttpStatus.ACCEPTED);
		
		//Test del body
		AddResponse ad = response.getBody();
		assertEquals(ad.getMsg(),"La tarea con id:" + task.getId() + " ya existe en la base de datos. No se va a crear ninguna nueva");
		assertEquals(ad.getTitle(),task.getTitle());
		
		
	}
	
	public Tasks buildTask() {
		Tasks task=new Tasks();
		task.setId(10);
		task.setTitle("PruebaTest10");
		task.setDescription("Esta es una prueba de test");
		task.setHecho(Progreso.Pending);
		return task;
	}
	
	public Tasks updateTask() {
		Tasks task=new Tasks();
		task.setId(10);
		task.setTitle("PruebaTest15");
		task.setDescription("Esta es una prueba de test 15");
		task.setHecho(Progreso.InProgress);
		return task;
	}
	
	
	@Test
	public void addTaskControllerTest() throws Exception {
		
		//mock
		Tasks task= buildTask();
		ObjectMapper map = new ObjectMapper();
		String jsonString = map.writeValueAsString(task);
		//Normalmente Spring Boot convierte el objeto Java a JSON, pero como estamos en test y no queremos depender de
		//metodos externos, vamos a usar este metodo que sí te convierte explicitamente de Java a JSON, para luego
		//poder enviarlo en el content(...) de más abajo
		
		when(tasksListService.checkTaskAlreadyExist(task.getId())).thenReturn(false);
		when(repository.save(any())).thenReturn(task);
		
		//Aqui vamos a usar MockMvc
		//Como estamos haciendo una llamada Mock, la respuesta también será Mock (en concreto, JSON);
		this.mockMvc.perform(post("/tasks").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)).andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.title").value(task.getTitle())); //En esta ultima linea comparo el title que recibo con el que he mandado
		//Enviar JSON en formato String, también puedes enviarlo haciendo un copia y pega a lo bruto
		//false -> isCreated
		//true -> isAccepted
	}
	
	@Test
	public void getTasksByTitleTest() throws Exception {
		
		List<Tasks> ta= new ArrayList<Tasks>();
		ta.add(buildTask());
		when(repository.findAllByTitle(any())).thenReturn(ta);
		
		this.mockMvc.perform(get("/tasks").param("title", "PruebaTest10"))
			.andDo(print()).andExpect(status().isOk())
			.andExpect(jsonPath("$.[0].id").value(10)); //Busco el id del primer elemento [0] de la lista que me devuelve
			//.andExpect(jsonPath("$.length()").is(1)); //Como solo hay una tarea con ese titulo, solo espero una
			//No puedo probar la linea de arriba, porque al importar el paquete necesario me salta que es accesible desde más de un módulo
	}

	@Test
	public void updateTaskTest() throws Exception {
		
		Tasks ta=buildTask();
		ObjectMapper map= new ObjectMapper();
		String jsonString=map.writeValueAsString(updateTask());
		when(tasksListService.getTaskByID(anyInt())).thenReturn(buildTask());
		
		this.mockMvc.perform(put("/tasks/"+ta.getId()).contentType(MediaType.APPLICATION_JSON)
			.content(jsonString)).andDo(print()).andExpect(status().isOk())
			.andExpect(content().json("{\"id\":10,\"title\":\"PruebaTest15\",\"description\":\"Esta es una prueba de test 15\",\"hecho\":false}"));   //Sacado de la consola cuando haces el andDo(print()), pero lo he actualizado yo el title y description.
	}

	@Test
	public void deleteTaskControllerTest() throws Exception {
		//Este metodo ya de por si es muy simple
		
		Tasks ta=buildTask();
		//when(ta.getId()).thenReturn(ta.getId()); //Realmente esta linea no tiene sentido
		doNothing().when(repository).deleteById(ta.getId());
		this.mockMvc.perform(delete("/tasks").contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\" : \"10\"}")).andDo(print()).andExpect(status().isCreated())
				.andExpect(content().string("Task deleted"));
	}

}




