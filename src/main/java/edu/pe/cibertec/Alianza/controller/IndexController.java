package edu.pe.cibertec.Alianza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String irAlLogin() {
        // Esto le dice: "Si entran a la raíz, mándalos a /General/login"
        return "redirect:/General/login";
    }
}