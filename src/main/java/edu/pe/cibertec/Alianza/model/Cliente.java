package edu.pe.cibertec.Alianza.model;

import jakarta.persistence.*;
import lombok.Data; // Si usas Lombok
import lombok.NoArgsConstructor; // Si usas Lombok

// @Data // Opcional: Anotación de Lombok para Getters, Setters, ToString, etc.
// @NoArgsConstructor // Opcional: Anotación de Lombok para constructor vacío

@Entity
@Table(name = "Cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cliente;

    // Relación Uno a Uno: Mapea la FK 'id_usuario_fk' de la tabla Cliente
    // FetchType.LAZY: Carga el objeto Usuario solo cuando se accede a él
    @OneToOne(fetch = FetchType.LAZY)
 // Hibernate intentaría buscar id_usuario_fk, pero es mejor mapearlo explícitamente.
 @JoinColumn(name = "id_usuario_fk", unique = true) 
 private Usuario usuario;

    @Column(name = "documento_identidad", nullable = false, unique = true, length = 20)
    private String documentoIdentidad;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "telefono", length = 15)
    private String telefono;
    
    // =======================================================
    // 2. GETTERS, SETTERS y CONSTRUCTORES (Si NO usas Lombok)
    // =======================================================

    public Cliente() {}

    public Cliente(String documentoIdentidad, String nombres, String apellidos, String telefono, Usuario usuario) {
        this.documentoIdentidad = documentoIdentidad;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.usuario = usuario;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}