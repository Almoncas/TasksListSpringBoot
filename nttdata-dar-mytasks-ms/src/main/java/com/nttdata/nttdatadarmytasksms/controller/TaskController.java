package com.nttdata.nttdatadarmytasksms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.nttdatadarmytasksms.repository.TasksListRepository;
import com.nttdata.nttdatadarmytasksms.service.TasksListService;

import org.springframework.http.HttpHeaders;

//TODO Quitar los comentarios y poner más logs

@RestController //Mirara en todos los archivos donde haya @RestController para ver donde está el /addTask por ejemplo
public class TaskController {
	
	
	@Autowired
	private TasksListRepository repository;
	
	@Autowired
	private TasksListService tasksListService;
	
	private final static Logger logger=LoggerFactory.getLogger(TaskController.class);
	
	@PostMapping("/tasks")
	public ResponseEntity<AddResponse> addTaskImplementation(@RequestBody Tasks task) {
		
		AddResponse ad=new AddResponse();
		
		int id=task.getId();
		boolean check=tasksListService.checkTaskAlreadyExist(id);
		if(!check){	//Si es false
			
		//Puedo acceder a todos los parametros que le llegan en formato JSON
		//task.getId();
		//Pero realmente lo que quiero hacer es guardarlo
		logger.info("Task does not exist, so creating one");
		repository.save(task);
		
		HttpHeaders headers=new HttpHeaders();
		headers.add("unique", task.getTitle());
		
		ad.setMsg("Se ha añadido la tarea "+task.getTitle());
		ad.setTitle(task.getTitle());
		//return ad;
		return new ResponseEntity<AddResponse>(ad,headers,HttpStatus.CREATED); 
		//Todo lo que quieras incluir en la respuesta, tienes que incluirlo en la ResponseEntity
		
		//Spring boot ya se encarga de convertir el objeto JAVA a JSON, pero nosotros queremos enviar un mensaje personalizado, por eso lo de ad.
		//Tambien envía el codigo CREATED que indica que se ha creado bien, no errores.
		//El <AddResponse> es para cambiar el tipo de objeto, para que no salte un warning. 
		
		}else {
			//Si la tarea ya existe en la base de datos
			logger.info("Task exists, so skipping create a new one");
			
			ad.setMsg("La tarea con id:" + task.getId() + " ya existe en la base de datos. No se va a crear ninguna nueva");
			ad.setTitle(task.getTitle());
			
			return new ResponseEntity<AddResponse>(ad,HttpStatus.ACCEPTED); //Acepta la peticion, pero no ha creado nada 
		}
	}
	
	
	@GetMapping("/tasks/id/{id}") //Lo que vaya aqui entre llaves, es lo que tiene que ir en la siguiente linea en value="..."
	public Tasks getTaskById(@PathVariable(value="id")int id) {
		//Esto realmente se podria hacer sin el .get() y con el if() de /addTask
		
		Tasks task=repository.findById(id).get();
		return task; //Spring Boot ya se encarga de devolverlo en formato JSON
		
	}
	
	
	@GetMapping("/tasks")
	public List<Tasks> getTasksByTitle(@RequestParam(value="title")String title) {
		
		return repository.findAllByTitle(title);
		
	}
	
	//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
	
	
	@PutMapping("/tasks/{id}")
	public ResponseEntity<Tasks> updateTask(@PathVariable(value="id")int id,@RequestBody Tasks task) { //Porque pedimos la id de la task, y los datos nuevos a actualizar
		
		//Tasks existingTask=repository.findById(id).get();
		Tasks existingTask=tasksListService.getTaskByID(id);
		
		//Actualizar los datos antiguos por los nuevos
		existingTask.setTitle(task.getTitle());
		existingTask.setDescription(task.getDescription());
		repository.save(existingTask);
		
		logger.info("Se ha actualizado la tarea y se ha guardado");
		
		//Mandar una respuesta de vuelta
		return new ResponseEntity<Tasks>(existingTask,HttpStatus.OK);
		
		
	}

	
	@DeleteMapping("/tasks")
	public ResponseEntity<String> deleteTaskById(@RequestBody Tasks task) { 
		//Hecho de manera distinta al tutorial
		//Aqui solo le paso el id, en el tutorial buscas la entidad entera y la borras, pero de esa manera no me funciona
		
		//TODO todos los posibles errores de no encontrar la tarea, etc.
		int id= task.getId();
		repository.deleteById(id);
		return new ResponseEntity<>("Task deleted",HttpStatus.CREATED);//Esta vez no quiero responder con un Json, sino con un string.
		//Por eso no hace falta hacer la reconversion<>
		
	}
	
	
	@GetMapping("/tasks/all")
	public List<Tasks> getAllTasks(){
		
		return repository.findAll();
	}
	
	
	@PutMapping("/tasks/done/{id}")
	public ResponseEntity<Tasks> taskDone(@PathVariable(value="id")int id){
		
		Tasks task=repository.findById(id).get();
		
		//Actualizar el campo de HECHO
		task.setHecho(true);
		repository.save(task);
		logger.info("La tarea se ha marcado como hecha");
		
		return new ResponseEntity<Tasks>(task,HttpStatus.OK);
		
	}
	
	
	@PutMapping("/tasks/undone/{id}")
	public ResponseEntity<Tasks> taskUndone(@PathVariable(value="id")int id){
		
		Tasks task=repository.findById(id).get();
		
		//Actualizar el campo de HECHO
		task.setHecho(false);
		repository.save(task);
		logger.info("La tarea se ha marcado como no hecha");
		
		return new ResponseEntity<Tasks>(task,HttpStatus.OK);
		
	}
	
}

