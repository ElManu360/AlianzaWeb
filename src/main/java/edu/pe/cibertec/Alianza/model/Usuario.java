package edu.pe.cibertec.Alianza.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
	@Table(name = "Usuario")
	public class Usuario {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id_usuario;

	    // Campo para el login
	    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 50)
	    private String nombreUsuario;

	    // Campo de la contraseña (debe ser hasheada en la práctica)
	    @Column(name = "password", nullable = false, length = 255)
	    private String password;

	    @Column(name = "email", nullable = false, unique = true, length = 100)
	    private String email;

	    // Campo ENUM para el rol
	    @Column(name = "rol", nullable = false)
	    private String rol; // En Java, lo manejaremos como String por simplicidad con ENUM de MySQL

	    @Column(name = "estado", nullable = false)
	    private Boolean estado = true;

	    @Column(name = "fecha_creacion", nullable = false)
	    private LocalDateTime fechaCreacion;

	    // --- Constructor vacío ---
	    public Usuario() {
	        this.fechaCreacion = LocalDateTime.now();
	    }

	    // --- Constructor con campos requeridos (Opcional, pero útil) ---
	    public Usuario(String nombreUsuario, String password, String email, String rol) {
	        this.nombreUsuario = nombreUsuario;
	        this.password = password;
	        this.email = email;
	        this.rol = rol;
	        this.estado = true;
	        this.fechaCreacion = LocalDateTime.now();
	    }


	    // --- Getters y Setters ---

	    public Integer getId_usuario() {
	        return id_usuario;
	    }

	    public void setId_usuario(Integer id_usuario) {
	        this.id_usuario = id_usuario;
	    }

	    public String getNombreUsuario() {
	        return nombreUsuario;
	    }

	    public void setNombreUsuario(String nombreUsuario) {
	        this.nombreUsuario = nombreUsuario;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getRol() {
	        return rol;
	    }

	    public void setRol(String rol) {
	        this.rol = rol;
	    }

	    public Boolean getEstado() {
	        return estado;
	    }

	    public void setEstado(Boolean estado) {
	        this.estado = estado;
	    }

	    public LocalDateTime getFechaCreacion() {
	        return fechaCreacion;
	    }

	    public void setFechaCreacion(LocalDateTime fechaCreacion) {
	        this.fechaCreacion = fechaCreacion;
	    }
	}

