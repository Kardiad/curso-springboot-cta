package edu.cta.academy.service;

import java.util.Optional;

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
}
