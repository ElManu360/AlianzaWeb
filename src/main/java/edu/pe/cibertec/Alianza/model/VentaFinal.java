package edu.pe.cibertec.Alianza.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "venta_final")
public class VentaFinal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String producto;
    private Double precio;
    private Integer cantidad;
    private Double subtotal;
    private Double igv;
    private Double total;

    private LocalDateTime fechaRegistro = LocalDateTime.now();

    public VentaFinal() {} // Constructor vacío requerido por JPA

    public VentaFinal(String producto, Double precio, Integer cantidad, Double subtotal, Double igv, Double total) {
        this.producto = producto;
        this.precio = precio;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.igv = igv;
        this.total = total;
        this.fechaRegistro = LocalDateTime.now();
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
    public Double getIgv() { return igv; }
    public void setIgv(Double igv) { this.igv = igv; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
