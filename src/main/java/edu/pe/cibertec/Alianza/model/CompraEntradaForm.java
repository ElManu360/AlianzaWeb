package edu.pe.cibertec.Alianza.model;

// Esta clase NO es una entidad de BD, solo un formulario de datos.

public class CompraEntradaForm {
    
    private Integer idEntrada; // ID del ticket seleccionado (de la tabla Entrada)
    private Integer cantidad;
    private String metodoPago;

    // --- Constructor vacío (Necesario para Spring) ---
    public CompraEntradaForm() {}

    // --- Getters y Setters ---

    public Integer getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(Integer idEntrada) {
        this.idEntrada = idEntrada;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}