package edu.cta.academy.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.cta.academy.repository.entity.Alumno;


/*
 * Esta clase se dedicará a interactuar con la BBDD.
 * */
@Repository
public interface AlumnoRepository extends PagingAndSortingRepository<Alumno, Long>  {
	 //consulta los alumnos que estén en un rango de edad
    Iterable<Alumno> findByAgeBetween(int edadmin, int edadmax);
    
    //consultar los alumnos que contengan un nombre dado 
    Iterable<Alumno> findByNameContaining(String name);
    
    //Consulta Nativa
    
    @Query(value = "SELECT * FROM alumno a WHERE a.nombre LIKE %?1% OR a.apellidos LIKE %?1%", 
    		nativeQuery = true)
    Iterable<Alumno> encuentraPorNombreOApellido(String pattern);
    
    //JPQL - HQL
    
    @Query(value = "SELECT s FROM Alumno s WHERE s.name LIKE %?1% OR s.surname LIKE %?1%")
    Iterable<Alumno> encuentraPorNombreOApellidoNoNativo(String pattern);
    
    //1º Definir procedimientos X
    //2º Referenciamos procedimientos entidad student X
    //3º Hacemos métodos de alumno repository @Procedure que usen apartado 2
    
    @Procedure(name="Alumno.alumnosRegistradosHoy")
    Iterable<Alumno> obtenerAlumnosRegistradosHoy();
    
    @Procedure(name="Alumno.alumnosEdadMediaMinMax")
    Map<String, Number> getStadisticsOfStudents(int edadmin, int edadmax, float edadmedia);
    
    //Paginados fácil: https://docs.spring.io/spring-data/commons/docs/2.4.5/api/index.html?org/springframework/data/repository/CrudRepository.html
    
}
