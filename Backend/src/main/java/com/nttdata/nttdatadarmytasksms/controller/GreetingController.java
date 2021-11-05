package com.nttdata.nttdatadarmytasksms.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden //Ocultar de la documentación de swagger
@RestController
public class GreetingController{
	 @Autowired
	 Greeting greeting; //Con autowired puedes crear objetos directamente
	 
	 AtomicLong counter=new AtomicLong();
	
	 
	@GetMapping("/greeting") //Porque se ha elegido Get, si fuera Post sería PostMapping
	public Greeting greeting(@RequestParam(value="name")String name) {
		
		greeting.setId(counter.incrementAndGet());
		greeting.setContent("Hey estoy aprendiendo de " + name);
		return greeting;
		
	}
	
	/*@GetMapping("/error")
	public String error() {
		return "prueba";
	}*/

}
