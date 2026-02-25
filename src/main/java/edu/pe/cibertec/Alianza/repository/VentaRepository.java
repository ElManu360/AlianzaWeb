package edu.pe.cibertec.Alianza.repository;

import edu.pe.cibertec.Alianza.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
    // Métodos para generar reportes irían aquí más adelante (ej. buscar por fecha)
}