package edu.pe.cibertec.Alianza.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pe.cibertec.Alianza.model.VentaFinal;
import edu.pe.cibertec.Alianza.repository.VentaFinalRepository;

@RestController
@RequestMapping("/ventas")
public class VentaFinalController {

    @Autowired
    private VentaFinalRepository ventaRepo;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarVenta(@RequestBody List<VentaFinal> ventas) {
        try {
            // Guardar todas las líneas de venta
            ventaRepo.saveAll(ventas);
            return ResponseEntity.ok("Venta registrada correctamente");
        } catch (Exception e) {
            // Manejo de error, retorna 500 si algo falla
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al registrar la venta: " + e.getMessage());
        }
    }
}
