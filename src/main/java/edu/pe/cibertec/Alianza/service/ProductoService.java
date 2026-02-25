package edu.pe.cibertec.Alianza.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.pe.cibertec.Alianza.model.Producto;
import edu.pe.cibertec.Alianza.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Registrar nuevo producto
    @Transactional
    public Producto nuevoProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Listar todos los productos
    @Transactional(readOnly = true)
    public List<Producto> listarProductos() {
        List<Producto> lista = productoRepository.findAll();
        if (lista == null) {
            lista = new ArrayList<>();
        }
        System.out.println("Productos encontrados: " + lista.size());
        lista.forEach(p -> System.out.println(" - " + p.getNombre()));
        return lista;
    }

    // Buscar producto por ID
    @Transactional(readOnly = true)
    public Producto buscarPorId(Integer id) {
        return productoRepository.findById(id).orElse(new Producto());
    }

    // Actualizar producto existente
    @Transactional
    public boolean actualizarProducto(Producto producto) {
        if (producto == null || producto.getId_producto() == null || 
            !productoRepository.existsById(producto.getId_producto())) {
            return false;
        }
        productoRepository.save(producto);
        return true;
    }

    // Eliminar producto por ID
    @Transactional
    public boolean eliminarProducto(Integer id_producto) {
        try {
            if (!productoRepository.existsById(id_producto)) {
                return false;
            }
            productoRepository.deleteById(id_producto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
