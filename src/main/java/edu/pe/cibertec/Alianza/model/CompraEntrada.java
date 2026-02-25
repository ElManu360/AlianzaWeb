package edu.pe.cibertec.Alianza.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CompraEntrada")
public class CompraEntrada {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_compra; // Se cambió a Integer (tipo estándar de PK)

    // FK 1: id_cliente_fk (Clave foránea al Cliente que compró)
    @ManyToOne
    @JoinColumn(name = "id_cliente_fk", nullable = false) // Mapeo a la columna de la BD
    private Cliente cliente; // <-- CORRECCIÓN CLAVE: Debe ser Cliente, no Usuario

	// FK 2: id_entrada_fk (Tipo de entrada comprada)
	@ManyToOne
	@JoinColumn(name = "id_entrada_fk", nullable = false) 
	private Entrada entrada;

	@Column(name = "cantidad", nullable = false)
	private Integer cantidad; // Se cambió a Integer

    // CRÍTICO: Campo persistente para el precio unitario (para reportes y cálculo)
    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario; // <-- AÑADIDO

	@Column(name = "total", nullable = false)
	private Double total; // Se cambió a Double

	@Column(name = "metodo_pago", nullable = false)
	private String metodo_pago;

	@Column(name = "numero_asiento", nullable = true)
	private String numeroAsiento; // Se corrige a CamelCase para el getter/setter

    @Column(name = "fecha_compra")
	private LocalDateTime fecha_compra;

	// --- Constructor vacío ---
	public CompraEntrada() {
		this.fecha_compra = LocalDateTime.now();
	}

    // =======================================================
    // GETTERS Y SETTERS (AJUSTADOS)
    // =======================================================

	public Integer getId_compra() {
		return id_compra;
	}

	public void setId_compra(Integer id_compra) {
		this.id_compra = id_compra;
	}

    public Cliente getCliente() { // <-- Getter corregido
        return cliente;
    }

    public void setCliente(Cliente cliente) { // <-- Setter corregido
        this.cliente = cliente;
    }

	public Entrada getEntrada() {
		return entrada;
	}

	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

    public Double getPrecioUnitario() { // <-- Getter del campo AÑADIDO
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) { // <-- Setter del campo AÑADIDO
        this.precioUnitario = precioUnitario;
    }

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getMetodo_pago() {
		return metodo_pago;
	}

	public void setMetodo_pago(String metodo_pago) {
		this.metodo_pago = metodo_pago;
	}

	// Corregido el getter para usar CamelCase
	public String getNumeroAsiento() { 
		return numeroAsiento;
	}

	// Corregido el setter para usar CamelCase
	public void setNumeroAsiento(String numeroAsiento) { 
		this.numeroAsiento = numeroAsiento;
	}

	public LocalDateTime getFecha_compra() {
		return fecha_compra;
	}

	public void setFecha_compra(LocalDateTime fecha_compra) {
		this.fecha_compra = fecha_compra;
	}
    
    // --- Método auxiliar eliminado, el precio unitario es un campo persistente ahora. ---
}