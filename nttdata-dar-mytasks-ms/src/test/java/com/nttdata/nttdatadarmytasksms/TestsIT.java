package com.nttdata.nttdatadarmytasksms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
//import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.nttdata.nttdatadarmytasksms.controller.Tasks;

@SpringBootTest
public class TestsIT {

	//Estos tests se tienen que hacer con el profile=dev y el programa corriendo
	@Test
	public void getTaskByIdTest() throws JSONException {
		
		String expected="{\r\n" + 
				"    \"id\": 2,\r\n" + 
				"    \"title\": \"Prueba2\",\r\n" + 
				"    \"description\": \"La descripcion de la prueba 2\",\r\n" + 
				"    \"hecho\": false\r\n" + 
				"}";
		
		TestRestTemplate restTemplate= new TestRestTemplate();
		ResponseEntity<String> response=restTemplate.getForEntity("http://localhost:8080/GetTaskByID/2", String.class);
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		JSONAssert.assertEquals(expected, response.getBody(), false);
		
		
	}
	
	
	@Test
	public void addBookIntegrationTest()
	{
		TestRestTemplate restTemplate =new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Tasks> request = new HttpEntity<Tasks>(buildTask(),headers);
		ResponseEntity<String>	response =	restTemplate.postForEntity("http://localhost:8080/addTask", request, String.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(buildTask().getId(),response.getHeaders().get("unique").get(0)); //get(0) porque el get("unique") devuelve una lista, entonces el que quieres esta en la primera posicion
		
		
	}
	
	
	
	public Tasks buildTask() {
		Tasks task=new Tasks();
		task.setId(10);
		task.setTitle("PruebaTest10");
		task.setDescription("Esta es una prueba de test");
		task.setHecho(false);
		return task;
	}

	
	
	
}