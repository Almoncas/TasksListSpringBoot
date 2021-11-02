package com.nttdata.nttdatadarmytasksms.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController{
	 @Autowired
	 private Greeting greeting; //Con autowired puedes crear objetos directamente
	 
	 private AtomicLong counter=new AtomicLong();
	
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
