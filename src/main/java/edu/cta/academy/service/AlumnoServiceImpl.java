package edu.cta.academy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.cta.academy.repository.AlumnoRepository;
import edu.cta.academy.repository.entity.Alumno;

/*
 * Esta clase se realizará la lógica de negocio
 * Aplicando el los métodos del servicio
 * */
@Service
public class AlumnoServiceImpl implements AlumnoService {
	
	//Spring auto instancia a alumnoRepo generando una inyección de dependencia
	@Autowired
	AlumnoRepository sudentRepository;

	@Override
	@Transactional(readOnly = true) //Permite acceso concurrente a la bbdd
	public Iterable<Alumno> allStudents() {
		return this.sudentRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true) //Permite acceso concurrente a la bbdd
	public Optional<Alumno> findOneStudent(long id) {
		return this.sudentRepository.findById(id);
	}

	@Override
	@Transactional
	public Optional<Alumno> editStudentById(Alumno student) {
		
		return Optional.empty();
	}

	@Override
	@Transactional
	public void deleteStudent(long id) {
		this.sudentRepository.deleteById(id);		
	}

	@Override
	@Transactional
	public Alumno insertStudent(Alumno student) {
		return this.sudentRepository.save(student);
	}
	
	
	
}
