
CREATE TABLE LISTOFTASKS(
	id INT PRIMARY KEY,
	title VARCHAR(100) NOT NULL,
	description VARCHAR(256),
	hecho ENUM('Pending', 'InProgress', 'Finished')
);



INSERT INTO LISTOFTASKS (id,title,description,hecho) VALUES
	(1,'Prueba1','La descripcion de la prueba 1','Pending'),
	(2,'Prueba2','La descripcion de la prueba 2','InProgress'),
	(3,'Prueba3','Esta descripcion si esta a TRUE el campo de done','Finished');
	
	
--Si la linea de properties la conviertes a true, tendrias que quitar el CREATE de data.sql
--Y creo que también quitar el @Table de Tasks.java
	
	
	


