package edu.pe.cibertec.Alianza.model;

// Asumo que tu proyecto usa Lombok para Getters y Setters (basado en tu pom.xml)
import lombok.Data; 
import lombok.NoArgsConstructor; 

// Si no usas Lombok, DEBES descomentar esta línea y añadir manualmente los métodos.
// @Data 
// @NoArgsConstructor 
public class Registro {
    
    // --- Campos para la tabla Usuario ---
    private String nombreUsuario;
    private String password;
    private String email;

    // --- Campos para la tabla Cliente ---
    private String documentoIdentidad;
    private String nombres;
    private String apellidos;
    private String telefono;

    // =======================================================
    // 2. GETTERS, SETTERS y CONSTRUCTORES (Si NO usas Lombok)
    // =======================================================
    
    public Registro() {}

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