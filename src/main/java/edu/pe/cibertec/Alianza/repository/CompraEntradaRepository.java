package edu.pe.cibertec.Alianza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.pe.cibertec.Alianza.model.CompraEntrada;

@Repository
public interface CompraEntradaRepository extends JpaRepository<CompraEntrada, Integer> {

    
}
