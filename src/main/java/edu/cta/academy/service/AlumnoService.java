package edu.cta.academy.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import edu.cta.academy.DTO.Chiquitada;
import edu.cta.academy.repository.entity.Alumno;

//¿Qué es lo que hacemos?

public interface AlumnoService {

	//ALTA: check
	//BAJA: check delete id
	//MODIFICACION check
	//CONSULTA:
		/*-> CONSULTA 1 check
		/ -> CONSULTAR TODOS check*/
	Iterable<Alumno> allStudents();
	Optional<Alumno> findOneStudent(long id);
	Optional<Alumno> editStudentById(Alumno student, long id);
	void deleteStudent(long id);
	Alumno insertStudent(Alumno student);
    Iterable<Alumno> findByEdadBetween(int edadmin, int edadmax);
    Iterable<Alumno> findByNombreContaining(String name);
    Iterable<Alumno> findByNameOrSurname(String pattern);
    
    Iterable<Alumno> findByNameOrSurnameNoNative(String pattern);
    Iterable<Alumno> studentsRegisteredToday();
    Map<String,Number> stadistics();
    Iterable<Alumno> findAll(Pageable pageable);
    Iterable<Alumno> findByEdadBetween(int edadmin, int edadmax, Pageable pageable);
    //Nota esto es otra chiquitada que no habría que hacerse, porque debería de hacerse otro microservice
    Optional<Chiquitada> randomChiquito();
}
