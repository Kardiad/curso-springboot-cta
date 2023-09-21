package edu.cta.academy.repository.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

//Versiones de esta movida, por lo que veo si es 2.5 es como proyecto 2 versión 5.

@Entity
@Table(name="alumno") //hay que crear una tabla alumnos para que se pueda vincular a esta entidad.
public class Alumno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="nombre")
	private String name;
	
	@Column(name="apellidos")
	private String surname;
	
	@Column(name="correo_electronico")
	private String email;
	
	@Column(name="edad")
	private int age;
	
	@Column(name="fecha_alta")
	private LocalDateTime admision;

	@PrePersist // Esto se ejecuta en el insert, y se hará antes de insertarlo
	private void generateDate() {
		this.admision = LocalDateTime.now();
	}
	
	public Alumno() {
		super();
	}

	public Alumno(long id, String name, String surname, String email, int age) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.age = age;
		this.generateDate();
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public LocalDateTime getAdmision() {
		return admision;
	}

	public void setAdmision(LocalDateTime admision) {
		this.admision = admision;
	}
	
	
	
	
}
