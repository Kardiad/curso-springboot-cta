package edu.cta.academy.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
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
	public Optional<Alumno> editStudentById(Alumno student, long id) {
		//TODO pendiente-personalizado porque no existe un update en el CrudRepository
		Optional<Alumno> optional = Optional.empty();
		
			//LEO EL REGISTRO ID
			optional = this.sudentRepository.findById(id);
		    //SI EXISTE, ACTUALIZO
			if (optional.isPresent()){
				//actualizo todos los campos, menos el id y la fecha
				Alumno alumnoLeido = optional.get();
				//alumnoLeido.setNombre(alumno.getNombre());
				//alumnoLeido está en estado Persitente - JPA --> si modifico su estado, se modifica en la BD
				BeanUtils.copyProperties(student, alumnoLeido, "id", "admision");
				//this.alumnoRepository.save(alumnoLeido);//No es necesario
				optional = Optional.of(alumnoLeido);//"relleno el huevo"
				
			}
		    //SI NO EXISTE, NO HAGO NADA
		
		return optional;
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
