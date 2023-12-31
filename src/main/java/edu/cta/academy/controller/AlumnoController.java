package edu.cta.academy.controller;

import java.io.IOException;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.cta.academy.DTO.Chiquitada;
import edu.cta.academy.repository.entity.Alumno;
import edu.cta.academy.service.AlumnoService;

/*
 * Esta clase se dedicará a recibir peticiones de cte.
 * */
@CrossOrigin(originPatterns = { "*" }, methods = { RequestMethod.GET })
@RestController
@RequestMapping("/students") // todo lo que es alumno es para aquí
public class AlumnoController {

	// Creamos un logger para el controller.
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	AlumnoService service;
	
	@Value("${instancia}")
	String nombre_instancia;
	
	@Autowired
	Environment env;

	// ResponseEntity: mensaje http de respuesta,
	// se puede cambiar el wildcard ? por una clase
	// que haga la respuesta adecuada si la quieres más cerrada
	@GetMapping("/test")
	public Alumno test() {
		// Esto es un estado de transient, es decir que no está asociado a la bbdd ni a
		// ningún registro
		// Estado del objeto es como se encuentra en el momento el objeto.
		// En estado de persistent el dato estaría vinculado a la bbdd
		Alumno student = new Alumno(1, "Jose", "D'Alembert Faquinenza", "jd'a@gmail.com", 28);
		return student;
	}

	@GetMapping("/list")
	public ResponseEntity<?> listStudent() {
		/*
		 * var nombre = "sdjfjhaskdjfhasdklfhsklfhaskl"; nombre.charAt(10000);
		 */
		log.debug("Atendido por "+nombre_instancia+" Puerto: "+env.getProperty("local.server.port"));
		return ResponseEntity.ok(this.service.allStudents());
	}

	// @Valid vale para validar según lo anotado en validations en el modelo

	@GetMapping("/{id}")
	public ResponseEntity<?> findOneStudent(@Valid @PathVariable long id) {

		return ResponseEntity.ok(this.service.findById(id).get());
	}

	@PutMapping("/modify/{id}")
	public ResponseEntity<?> editStudentById(@Valid @RequestBody Alumno student, @PathVariable long id,
			BindingResult br) {
		if (br.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(br.getAllErrors());
		}
		return ResponseEntity.ok(this.service.editStudentById(student, id).get());

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteStudent(@PathVariable String id) {
		// TODO Auto-generated method stub
		long id_parsed = Long.parseLong(id);
		this.service.deleteStudent(id_parsed);
		return ResponseEntity.status(HttpStatus.OK).body("Student with " + id_parsed + " id was deleted suscessfull");
	}

	@PostMapping("/insert")
	public ResponseEntity<?> insertStudent(@RequestBody Alumno student, BindingResult br) {
		if (br.hasErrors()) {
			br.getAllErrors().forEach(error -> log.error(error.toString()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(br.getAllErrors());
		} else {
			Alumno a = this.service.insertStudent(student);
			return ResponseEntity.status(HttpStatus.CREATED).body(a);
		}
	}

	@GetMapping("/between")
	public ResponseEntity<?> betweenAges(@RequestParam(required = true, name = "edadmin") int edadmin,
			@RequestParam(required = true, name = "edadmax") int edadmax) {
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> ita = null;// lista de alumnos
		ita = this.service.findByEdadBetween(edadmin, edadmax);
		responseEntity = ResponseEntity.ok(ita);// ita es el cuerpo
		return responseEntity;
	}

	@GetMapping("/contains")
	public ResponseEntity<?> listByName(@RequestParam(required = true, name = "nombre") String nombre) {
		return ResponseEntity.ok(this.service.findByNombreContaining(nombre));
	}

	@GetMapping("/findby/{pattern}")
	public ResponseEntity<?> getByPattern(String pattern) {
		return ResponseEntity.ok(this.service.findByNameOrSurname(pattern));
	}

	@GetMapping("/findclass")
	public ResponseEntity<?> getByPatt(@RequestParam(required = true, name = "pattern") String pattern) {
		return ResponseEntity.ok(this.service.findByNameOrSurnameNoNative(pattern));
	}

	@GetMapping("/stadistics")
	public ResponseEntity<?> getStadistics() {
		return ResponseEntity.ok(this.service.stadistics());
	}

	@GetMapping("/stadisticsdate")
	public ResponseEntity<?> getSudentsToday() {
		return ResponseEntity.ok(this.service.studentsRegisteredToday());
	}

	// https://docs.spring.io/spring-data/commons/docs/2.4.5/api/org/springframework/data/domain/Pageable.html
	// pageables
	// GET http://localhost:8081/students/pagedstudents?page=0&size=2
	@GetMapping("/pagedstudents")
	public ResponseEntity<?> paginatedStudent(Pageable pageable) {
		return ResponseEntity.ok(this.service.findAll(pageable));
	}

	// http://localhost:8081/students/pagedbyage?edadmin=2&edadmax=40&page=0&size=3
	@GetMapping("/pagedbyage")
	public ResponseEntity<?> paginatedByAges(@RequestParam(required = true, name = "edadmin") int edadmin,
			@RequestParam(required = true, name = "edadmax") int edadmax, Pageable pageable) {
		return ResponseEntity.ok(this.service.findByEdadBetween(edadmin, edadmax, pageable));
	}

	@GetMapping("/chiquitada")
	public ResponseEntity<?> chiquitada() {
		Optional<Chiquitada> ch = this.service.randomChiquito();
		return ResponseEntity.ok((ch.isPresent()) ? this.service.randomChiquito() : null);
	}
	
	/*
	 * get fotoPorId
	 * put editarAlumnoFoto
	 * post postAlumnoConFoto
	 * */
	
	// http://localhost:8081/students/insert-with-photo
	@PostMapping("/insert-with-photo")
	public ResponseEntity<?> insertWithPhoto(Alumno student, BindingResult br, MultipartFile f) throws IOException{
		Alumno a = null;
		if (br.hasErrors()) {
			br.getAllErrors().forEach(error -> log.error(error.toString()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(br.getAllErrors());
		} else {
			if(!f.isEmpty()) {
				log.debug("Hay foto");
				try {
					student.setPhoto(f.getBytes());
					a = this.service.insertStudent(student);									
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw e;
				}
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(a);
		}
	}
	
	//NO TE OLVIDES EL PATH VARIABLE Y QUE AQUÍ SE USA EL OBJETO DE NORMAL
	@GetMapping("/student-image/{id}")
	public ResponseEntity<?> studentImage(@PathVariable Long id){
		Optional<Alumno> s = this.service.findById(id);
		if(!s.isEmpty()) {
			Resource i = new ByteArrayResource(s.get().getPhoto());			
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(i);
		}
		return ResponseEntity.badRequest().body(null);
	}

}
