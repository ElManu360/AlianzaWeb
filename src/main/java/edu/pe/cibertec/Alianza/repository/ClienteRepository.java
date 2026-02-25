package edu.pe.cibertec.Alianza.repository;

import edu.pe.cibertec.Alianza.model.Cliente;
import edu.pe.cibertec.Alianza.model.Usuario; // <-- NECESARIO
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    
    // Método para el registro:
    Cliente findByDocumentoIdentidad(String documentoIdentidad);
    
    // MÉTODO CRÍTICO PARA LA COMPRA: BUSCA CLIENTE A PARTIR DEL OBJETO USUARIO
    Cliente findByUsuario(Usuario usuario); 
}