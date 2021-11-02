package com.nttdata.nttdatadarmytasksms.controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
//import javax.persistence.Table;
import javax.persistence.Table;



@Entity
@Table(name="LISTOFTASKS")
public class Tasks {

	//By annotating each field with @JsonInclude(Include.NON_NULL) annotation, it will be not included in the JSON output if its null.
	
	@Id
	@Column(name="id")
	private int id;
	@Column(name="title")
	private String title;
	@Column(name="description")
	private String description;
	@Column(name="hecho")
	private boolean hecho;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isHecho() {
		return hecho;
	}
	public void setHecho(boolean hecho) {
		this.hecho = hecho;
	}

}
