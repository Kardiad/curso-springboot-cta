package edu.cta.academy.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.cta.academy.repository.entity.Alumno;
import edu.cta.academy.service.AlumnoService;

/*
 * Esta clase se dedicará a recibir peticiones de cte.
 * */
@RestController
@RequestMapping("/alumno") //todo lo que es alumno es para aquí
public class AlumnoController {
	
	@Autowired
	AlumnoService service;

	//ResponseEntity: mensaje http de respuesta, 
	//se puede cambiar el wildcard ? por una clase 
	//que haga la respuesta adecuada si la quieres más cerrada
	@GetMapping
	public ResponseEntity<?> listStudent() {
		Iterable<Alumno> ita = this.service.allStudents();
		ResponseEntity<?> resp = null;
		return resp;
	}

	
	public Optional<Alumno> findOneStudent(long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	
	public Optional<Alumno> editStudentById(Alumno student) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	
	public void deleteStudent(long id) {
		// TODO Auto-generated method stub
		
	}

	
	public Alumno insertStudent(Alumno student) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
