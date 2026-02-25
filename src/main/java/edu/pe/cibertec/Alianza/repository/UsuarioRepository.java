package edu.pe.cibertec.Alianza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.pe.cibertec.Alianza.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Método custom para buscar un usuario por su nombre_usuario (necesario para el login)
    Usuario findByNombreUsuario(String nombreUsuario);

    // Si quieres buscar por email también
    Usuario findByEmail(String email);
}
