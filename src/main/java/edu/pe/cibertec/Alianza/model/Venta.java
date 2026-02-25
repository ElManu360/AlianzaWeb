package edu.pe.cibertec.Alianza.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_venta;

    // Relación con Cliente (FK: id_cliente_fk)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente_fk", nullable = false) 
    private Cliente cliente;

    @Column(name = "fecha_venta")
    private LocalDateTime fechaVenta;

    @Column(name = "monto_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotal;

    // Mapeo de ENUM CHECK ('Producto', 'Entrada')
    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo; 
    
    // id_referencia_fk: Almacena el ID del Carrito o el ID de CompraEntrada
    @Column(name = "id_referencia_fk", nullable = false)
    private Integer idReferenciaFk; 

    // Mapeo de ENUM CHECK ('Completada', 'Cancelada', 'Reembolso')
    @Column(name = "estado", length = 20)
    private String estado = "Completada";

    // --- Constructor, Getters y Setters ---

    public Venta() {
        this.fechaVenta = LocalDateTime.now();
    }

    public Integer getId_venta() {
        return id_venta;
    }

    public void setId_venta(Integer id_venta) {
        this.id_venta = id_venta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getIdReferenciaFk() {
        return idReferenciaFk;
    }

    public void setIdReferenciaFk(Integer idReferenciaFk) {
        this.idReferenciaFk = idReferenciaFk;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}