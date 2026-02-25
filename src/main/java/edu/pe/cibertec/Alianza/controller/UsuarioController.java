package edu.pe.cibertec.Alianza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import edu.pe.cibertec.Alianza.model.Usuario;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Usuario")
public class UsuarioController {

    @GetMapping("/menu")
    public String menuUsuario(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogeado");

        if (usuario == null) {
            return "redirect:/General/login";
        }

        model.addAttribute("nombreUsuarioLogeado", usuario.getNombreUsuario());
        return "usuarios/principalu";
    }
}