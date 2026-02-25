package edu.pe.cibertec.Alianza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // <<--- ESTA ES LA IMPORTACIÓN CORRECTA DE SPRING UI
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import ch.qos.logback.core.model.Model; // <<--- ESTA LÍNEA DEBE SER ELIMINADA

import edu.pe.cibertec.Alianza.model.Login; // Asumiendo que tu DTO se llama 'Login'
import edu.pe.cibertec.Alianza.model.Registro;
import edu.pe.cibertec.Alianza.model.Usuario;
import edu.pe.cibertec.Alianza.service.UsuarioService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/General")
public class GeneralController {

    @Autowired
    private UsuarioService usuarioService; // Inyectamos el servicio

    // --- Mapeo de la página principal (menú) ---
    @GetMapping("/menu")
    public String home(Model model) {
        // Agrega cualquier dato necesario para General.html si es el caso
        return "General/General"; // Retorna la vista General/General.html
    }

    // --- Mapeo para mostrar el formulario de Login (GET) ---
    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        // Agregamos un objeto vacío de Login al modelo para que Thymeleaf lo use
        model.addAttribute("Login", new Login()); // Nombre del atributo en minúscula es buena práctica, pero 'Login' funcionará si tu HTML lo usa así.
        model.addAttribute("errorLogin", false); // Para ocultar el mensaje de error inicialmente
        return "General/login"; // Retorna la vista General/login.html
    }

    // --- Mapeo para procesar el formulario de Login (POST) ---
    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute("Login") Login login, Model model, HttpSession session) { // <<-- AÑADIR HttpSession

        Usuario usuario = usuarioService.verificarCredenciales(
                login.getNombreUsuario(),
                login.getPassword()
        );

        if (usuario != null) {

            // 1. GUARDAR EL USUARIO EN LA SESIÓN
            session.setAttribute("usuarioLogeado", usuario);

            // 2. Redirigir según el rol
            String rol = usuario.getRol().toUpperCase(); // Para estandarizar la comparación

            if ("ADMIN".equals(rol)) {
                return "redirect:/Admin/principal"; // Redirección al Controller Admin
            } else if ("USUARIO".equals(rol)) {
                return "redirect:/Usuario/menu"; // Redirección al Controller Usuario
            } else if ("PROVEEDOR".equals(rol)) {
                return "redirect:/Proveedor/menu"; // Redirección al Controller Proveedor
            }

            // Redirección por defecto si el rol no es reconocido
            return "redirect:/General/menu";

        } else {
            // ... (Lógica de error se mantiene igual) ...
            model.addAttribute("Login", login);
            model.addAttribute("errorLogin", true);
            model.addAttribute("mensajeError", "Usuario o contraseña incorrectos.");
            return "General/login";
        }
    }

    // --- Nuevo Método para Cerrar Sesión (Logout) ---
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalida (borra) todos los atributos de la sesión
        return "redirect:/General/menu"; // Redirige de vuelta al menú de invitado
    }
    
 // En GeneralController.java (Métodos de Registro)

 // --- Mapeo para mostrar el formulario de Registro (GET) ---
 @GetMapping("/registro")
 public String mostrarFormularioRegistro(Model model) {
     // CORRECCIÓN CLAVE: Enviar el objeto DTO vacío al modelo con el nombre correcto
     model.addAttribute("registroRequest", new Registro()); 
     return "General/registro"; // Retorna la vista General/registro.html
 }

 // --- Mapeo para procesar el formulario de Registro (POST) ---
 @PostMapping("/registro")
 public String procesarRegistro(@ModelAttribute("registroRequest") Registro request, Model model, RedirectAttributes redirectAttrs) {
     try {
         // Validación básica (puedes añadir más con @Valid)
         if (request.getPassword() == null || request.getPassword().length() < 6) {
             throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
         }
         
         // LLAMADA AL SERVICIO: Realiza la doble inserción (Usuario y Cliente) transaccionalmente
         usuarioService.registrarUsuarioYCliente(request);
         
         // ÉXITO: Redirige al login con mensaje flash
         redirectAttrs.addFlashAttribute("mensaje", "¡Registro exitoso! Ya eres un Íntimo. Por favor, inicia sesión.");
         redirectAttrs.addFlashAttribute("cssmensaje", "alert alert-success");
         return "redirect:/General/login"; 
         
     } catch (Exception ex) {
         
         // 1. Añadimos el objeto 'request' de vuelta al modelo 
         //    (para que el usuario no pierda lo que escribió)
         model.addAttribute("registroRequest", request);
         
         // 2. Añadimos el mensaje de error directamente al MODEL 
         //    (Para que Thymeleaf lo muestre en el retorno directo)
         String errorMessage = "Error al registrar: El usuario, email o DNI ya existen. Verifique sus datos.";
         if (ex.getMessage() != null && ex.getMessage().contains("IllegalArgument")) {
              errorMessage = ex.getMessage();
         }
         
         model.addAttribute("mensaje", errorMessage);
         model.addAttribute("cssmensaje", "alert alert-danger");
         
         // 3. Retornamos DIRECTAMENTE la vista, sin "redirect:".
         return "General/registro"; 
     }
 }
}