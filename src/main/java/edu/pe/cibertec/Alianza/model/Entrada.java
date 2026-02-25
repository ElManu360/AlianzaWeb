package edu.pe.cibertec.Alianza.model;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Entrada")
public class Entrada {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_entrada;

    @Column(name = "partido", nullable = false, unique = true, length = 100)
    private String partido;

    @Column(name = "fecha_partido", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha_partido;

    @Column(name = "estadio", nullable = false, unique = true, length = 100)
    private String estadio;

    // Campo ENUM para la zona
    @Column(name = "zona", nullable = false)
    private String zona; // En Java, lo manejaremos como String por simplicidad con ENUM de MySQL

    @Column(name = "precio", nullable = false)
    private double precio;

    @Column(name = "cantidad_disponible", nullable = false)
    private Integer cantidad_disponible;

    @Column(name = "estado", nullable = false)
    private Boolean estado = true;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    public Entrada() {
    	this.fechaCreacion = LocalDateTime.now();
    }

	public Entrada(Integer id_entrada, String partido, Date fecha_partido, String estadio, String zona,
			double precio, Integer cantidad_disponible, Boolean estado, LocalDateTime fechaCreacion) {
		super();
		this.id_entrada = id_entrada;
		this.partido = partido;
		this.fecha_partido = fecha_partido;
		this.estadio = estadio;
		this.zona = zona;
		this.precio = precio;
		this.cantidad_disponible = cantidad_disponible;
		this.estado = estado;
		this.fechaCreacion = fechaCreacion;
	}

	public Integer getId_entrada() {
		return id_entrada;
	}

	public void setId_entrada(Integer id_entrada) {
		this.id_entrada = id_entrada;
	}

	public String getPartido() {
		return partido;
	}

	public void setPartido(String partido) {
		this.partido = partido;
	}

	public Date getFecha_partido() {
		return fecha_partido;
	}

	public void setFecha_partido(Date fecha_partido) {
		this.fecha_partido = fecha_partido;
	}

	public String getEstadio() {
		return estadio;
	}

	public void setEstadio(String estadio) {
		this.estadio = estadio;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Integer getCantidad_disponible() {
		return cantidad_disponible;
	}

	public void setCantidad_disponible(Integer cantidad_disponible) {
		this.cantidad_disponible = cantidad_disponible;
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
