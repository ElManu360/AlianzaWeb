package edu.pe.cibertec.Alianza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Producto")
public class ProductoController {

    @GetMapping("/Producto")
    public String mostrarProductos() {
        // CAMBIADO: Antes "Producto/Producto"
        return "usuarios/Producto"; 
    }

    @GetMapping("/categoria/camisetas")
    public String mostrarCamisetas() {
        return "usuarios/categoria/camisetas"; 
    }

    @GetMapping("/categoria/souvenirs")
    public String mostrarSouvenirs() {
        return "usuarios/categoria/souvenirs"; 
    }

    @GetMapping("/categoria/banderas")
    public String mostrarBanderas() {
        return "usuarios/categoria/banderas"; 
    }
}