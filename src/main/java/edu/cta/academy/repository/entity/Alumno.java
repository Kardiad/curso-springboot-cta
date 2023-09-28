package edu.cta.academy.repository.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.ParameterMode;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;

//El out es necesario en los procedimientos porque sino peta en spring boot
//Versiones de esta movida, por lo que veo si es 2.5 es como proyecto 2 versión 5.

@Entity
@Table(name="alumno")//hay que crear una tabla alumnos para que se pueda vincular a esta entidad.
@NamedStoredProcedureQueries(
		{
			@NamedStoredProcedureQuery(name="Alumno.alumnosRegistradosHoy", procedureName = "obtenerAlumnosRegistradosHoy", resultClasses = edu.cta.academy.repository.entity.Alumno.class),
			@NamedStoredProcedureQuery(name="Alumno.alumnosEdadMediaMinMax", procedureName = "calcular_max_min_media_edad",
			parameters = {
					@StoredProcedureParameter(mode = ParameterMode.INOUT , type = Integer.class , name ="edadmax"),
					@StoredProcedureParameter(mode = ParameterMode.INOUT , type = Integer.class , name ="edadmin"),
					@StoredProcedureParameter(mode = ParameterMode.INOUT , type = Float.class , name ="edadmedia")
			})
		}
	)
public class Alumno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="nombre")
	@Size(min=3, max=30)
	private String name;
	
	@Column(name="apellidos")
	@NotEmpty
	@NotBlank // No valen strings vacíos
	private String surname;
	
	@Lob
	@JsonIgnore
	@Column(name="foto")
	private byte[] photo;
	
	@Column(name="correo_electronico")
	@Email
	private String email;
	
	@Column(name="edad")
	@Min(3)
	@Max(116)
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

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	/*
	 * Este método es para ver si tiene o no una foto asociada a su perfil, en caso de no tener será null su valor
	 * */
	public Integer getPhotoHashCode() {
		if(this.photo!=null) {
			return this.photo.hashCode();
		}
		return null;
	}
}
