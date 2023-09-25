package edu.cta.academy.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.cta.academy.repository.entity.Alumno;
import edu.cta.academy.service.AlumnoService;

/*
 * Esta clase se dedicará a recibir peticiones de cte.
 * */
@RestController
@RequestMapping("/students") //todo lo que es alumno es para aquí
public class AlumnoController {
	
	//Creamos un logger para el controller.
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	AlumnoService service;

	//ResponseEntity: mensaje http de respuesta, 
	//se puede cambiar el wildcard ? por una clase 
	//que haga la respuesta adecuada si la quieres más cerrada
	@GetMapping("/test")
	public Alumno test() {
		//Esto es un estado de transient, es decir que no está asociado a la bbdd ni a ningún registro
		//Estado del objeto es como se encuentra en el momento el objeto.
		//En estado de persistent el dato estaría vinculado a la bbdd
		Alumno student = new Alumno(1, "Jose", "D'Alembert Faquinenza", "jd'a@gmail.com", 28);
		return student;
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> listStudent() {
		/*var nombre = "sdjfjhaskdjfhasdklfhsklfhaskl";
		nombre.charAt(10000);*/
		return ResponseEntity.ok(this.service.allStudents());
	}

	//@Valid vale para validar según lo anotado en validations en el modelo
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findOneStudent(@Valid @PathVariable long id, BindingResult br) {
		if(br.hasErrors()) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(br.getAllErrors());
		}
		return ResponseEntity.ok(this.service.findOneStudent(id).get());			
	}

	@PutMapping("/modify/{id}")
	public ResponseEntity<?> editStudentById(@Valid @RequestBody Alumno student, @PathVariable long id) {
		// TODO Auto-generated method stub
		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> oa = null;//alumno
		
			oa =  this.service.editStudentById(student, id);
			if (oa.isEmpty()){
				//si no está--devolver el cuerpo vacío y 404 no content
				responseEntity = ResponseEntity
						.notFound()
						.build();
			} else {
				//el optional tiene un alumno //si está--devolver el alumno y 200 ok
				Alumno alumno_modificado = oa.get();
				responseEntity = ResponseEntity.ok(alumno_modificado);
			}
			
		return responseEntity;
		
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteStudent(@PathVariable String id) {
		// TODO Auto-generated method stub
		long id_parsed = Long.parseLong(id);			
		this.service.deleteStudent(id_parsed);
		return ResponseEntity.status(HttpStatus.OK).body("Student with "+id_parsed+" id was deleted suscessfull");
	}

	@PostMapping("/insert")
	public ResponseEntity<?> insertStudent(@RequestBody Alumno student, BindingResult br) {
		if(br.hasErrors()) {
			br.getAllErrors().forEach(error->log.error(error.toString()));
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(br.getAllErrors());
		}else {
			Alumno a = this.service.insertStudent(student);
			return ResponseEntity.status(HttpStatus.CREATED).body(a);	
		}
	}
	
	
	
}
