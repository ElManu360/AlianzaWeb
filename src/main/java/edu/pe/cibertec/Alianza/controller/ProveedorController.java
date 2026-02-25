// En ProveedorController.java
package edu.pe.cibertec.Alianza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.pe.cibertec.Alianza.model.Usuario;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Proveedor")
public class ProveedorController {

    @GetMapping("/menu")
    public String portalProveedor(Model model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogeado");

        if (usuario == null) {
            return "redirect:/General/login";
        }

        // Cargar el nombre de usuario real (para mostrar en el navbar)
        model.addAttribute("nombreUsuarioLogeado", usuario.getNombreUsuario());

        // Retorna la vista específica del proveedor
        return "proveedores/principalp";
    }

    // Aquí irían los métodos para gestionar productos, pedidos, etc.
}