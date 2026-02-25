package edu.pe.cibertec.Alianza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Carrito")
public class CarritoController {

    @GetMapping("/Carrito")
    public String mostrarProductos() {
        // CAMBIADO: Antes "Carrito/Carrito"
        return "usuarios/Carrito"; 
    }
}