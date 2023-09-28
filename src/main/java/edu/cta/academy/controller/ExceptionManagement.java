package edu.cta.academy.controller;

import java.io.IOException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 * Esta clase hace las veces de un genérico de Excepciones, es mi cajón de mierda
 * de errores. Esta clase hace de "listener" de excepciones, para mostrar ese 
 * pequeño trozo de mierda que sale de aqui, para centralizar los residuos del programa
 * véase, errores. 
 * */
@RestControllerAdvice(basePackages = {"edu.cta.academy"}) // Para toda excepción que sucede en el paquete papa, pues las gestionas tú
public class ExceptionManagement {

	//para cada tipo de error se ejecutará un método.
	@ExceptionHandler(StringIndexOutOfBoundsException.class)
	public ResponseEntity<?> errStringOutOfBoundsException(StringIndexOutOfBoundsException e){
		return ResponseEntity.internalServerError().body(e.getMessage());
	}
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<?> errEmptyResultDataAccessException(EmptyResultDataAccessException e){
		return ResponseEntity.internalServerError().body(e.getMessage());
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> errNullPointerException(NullPointerException e){
		return ResponseEntity.internalServerError().body(e.getMessage());
	}
	
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<?> errThrowable(Throwable e){
		return ResponseEntity.internalServerError().body(e.getMessage());
	}

}
