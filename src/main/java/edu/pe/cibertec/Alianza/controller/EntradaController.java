package edu.pe.cibertec.Alianza.controller;

import edu.pe.cibertec.Alianza.model.CompraEntrada;
import edu.pe.cibertec.Alianza.model.CompraEntradaForm;
import edu.pe.cibertec.Alianza.model.Entrada;
import edu.pe.cibertec.Alianza.model.Usuario;
import edu.pe.cibertec.Alianza.service.CompraEntradaService; // <<-- IMPORTAR EL SERVICIO CORRECTO
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/Entradas")
public class EntradaController {

    // CORRECCIÓN: Inyectamos el servicio que tiene la lógica de Stock y Compra
    @Autowired
    private CompraEntradaService compraEntradaService; 

    // --- Mapeo principal para la lista de entradas ---
    @GetMapping("/disponibles")
    public String mostrarEntradasDisponibles(Model model, HttpSession session) {
        
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogeado");
        if (usuario == null) {
            return "redirect:/General/login";
        }
        
        // CORRECCIÓN: Llamamos al método correcto del servicio de CompraEntrada
        List<Entrada> listaEntradas = compraEntradaService.listarEntradasDisponibles();
        
        model.addAttribute("listaEntradas", listaEntradas);
        model.addAttribute("nombreUsuarioLogeado", usuario.getNombreUsuario());
        
        // Retorna la vista (revisa si la ruta "entradas/CompraEntradas" es correcta)
        return "entradas/CompraEntrada"; // <<-- ¡Ruta corregida!
    }
    
    // --- Mapeo GET para mostrar el formulario de compra por ID ---
    @GetMapping("/comprar/{id}")
    public String mostrarFormularioCompra(@PathVariable("id") Integer idEntrada, Model model, HttpSession session) {
        
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogeado");
        if (usuario == null) {
            return "redirect:/General/login";
        }

        // CORRECCIÓN: Llamamos al método correcto del servicio de CompraEntrada
        Entrada entradaSeleccionada = compraEntradaService.obtenerEntradaPorId(idEntrada);
        
        if (entradaSeleccionada == null || entradaSeleccionada.getCantidad_disponible() <= 0) {
            return "redirect:/Entradas/disponibles"; 
        }
        
        model.addAttribute("entrada", entradaSeleccionada);
        
        // DTO de formulario
        CompraEntradaForm compraForm = new CompraEntradaForm();
        compraForm.setIdEntrada(idEntrada);
        model.addAttribute("compraForm", compraForm); 
        
        return "usuarios/formulario_compra_entrada"; 
    }


    /**
     * Procesa el formulario de compra y finaliza la transacción.
     */
    @PostMapping("/finalizar")
    public String finalizarCompra(@ModelAttribute("compraForm") CompraEntradaForm form, 
                                    Model model, HttpSession session, RedirectAttributes redirectAttrs) {
        
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogeado");
        if (usuario == null) {
            return "redirect:/General/login";
        }

        try {
            // --- CÓDIGO CRÍTICO QUE FALTABA ---
            
            // 1. Llamada al servicio transaccional para procesar la compra
            CompraEntrada compraFinal = compraEntradaService.procesarCompra(form, usuario);
            
            // 2. Mensaje de éxito
            redirectAttrs.addFlashAttribute("mensaje", "¡Compra exitosa! Su ticket para la Zona " + compraFinal.getEntrada().getZona() + " ha sido confirmado. Asiento: " + compraFinal.getNumeroAsiento());
            redirectAttrs.addFlashAttribute("cssmensaje", "alert alert-success");
            return "redirect:/Entradas/disponibles"; 

        } catch (RuntimeException ex) {
            // Manejo de errores del servicio (ej. stock insuficiente)
            redirectAttrs.addFlashAttribute("mensaje", "Error al procesar la compra: " + ex.getMessage());
            redirectAttrs.addFlashAttribute("cssmensaje", "alert alert-danger");
            return "redirect:/Entradas/disponibles"; 
        } catch (Exception ex) {
            // Manejo de otros errores
            redirectAttrs.addFlashAttribute("mensaje", "Error interno al guardar la transacción.");
            redirectAttrs.addFlashAttribute("cssmensaje", "alert alert-danger");
            return "redirect:/Entradas/disponibles"; 
        }
    }
    @GetMapping("/boleta/{idCompra}")
    public ResponseEntity<byte[]> generarBoleta(@PathVariable Integer idCompra) {
        
        byte[] pdfBytes;
        String filename;

        try {
            // 1. Llamar al servicio para generar el PDF
            // (Asumimos que el servicio ya tiene la lógica de Jasper o el marcador)
            pdfBytes = compraEntradaService.generarBoletaPDF(idCompra);

            // 2. Configurar el nombre del archivo
            filename = "Boleta_AlianzaLima_" + idCompra + ".pdf";

            // 3. Configurar la respuesta HTTP para la descarga
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData(filename, filename);
            headers.setContentLength(pdfBytes.length); // Es bueno especificar la longitud
            
            // Devolver la respuesta (bytes del PDF)
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            // En caso de que falle la generación (JasperReports no encuentra el archivo, etc.)
            System.err.println("Error generando PDF para compra ID " + idCompra + ": " + e.getMessage());
            
            // 4. Si falla, devolver una respuesta de error 500 o un PDF vacío
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}