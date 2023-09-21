package edu.cta.academy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.cta.academy.repository.entity.Alumno;


/*
 * Esta clase se dedicará a interactuar con la BBDD.
 * */
@Repository
public interface AlumnoRepository extends CrudRepository<Alumno, Long> {
	
	
	
}
