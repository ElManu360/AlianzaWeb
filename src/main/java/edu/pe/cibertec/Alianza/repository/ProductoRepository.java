package edu.pe.cibertec.Alianza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.pe.cibertec.Alianza.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}
