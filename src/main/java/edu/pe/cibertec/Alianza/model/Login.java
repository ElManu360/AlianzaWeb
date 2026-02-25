package edu.pe.cibertec.Alianza.model;

public class Login {
	private String nombreUsuario;
    private String password;

    // --- Constructor vacío ---
    public Login() {}

    // --- Getters y Setters ---

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
}
