package edu.pe.cibertec.Alianza.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.pe.cibertec.Alianza.model.Entrada;
import edu.pe.cibertec.Alianza.model.Usuario;
import edu.pe.cibertec.Alianza.service.EntradaService;
import edu.pe.cibertec.Alianza.service.UsuarioService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Admin") // Ruta base: localhost:port/Admin
public class AdminController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EntradaService entradaService;

    /**
     * Muestra la página principal (dashboard) del Administrador.
     * Mapea a: localhost:port/Admin/dashboard
     * * Nota: Esta ruta es la que usamos enGeneralController para redirigir
     * después de un login exitoso con rol 'Admin'.
     * @param model Objeto Model.
     * @return Retorna la vista: templates/admins/principala.html
     */
    @GetMapping("/principal")
    public String dashboard(Model model, HttpSession session ) {

    	Usuario usuario = (Usuario) session.getAttribute("usuarioLogeado");

        if (usuario == null) {
            // Si no hay usuario en sesión, redirigir al login
            return "redirect:/General/login";
        }
        // Aquí se podrían añadir atributos al modelo (ej. estadísticas, lista de usuarios, etc.)

        // La ruta de la plantilla es relativa a templates/
        return "admins/principala"; // <<-- DEBE SER "admins/principala"
    }

    // Aquí irían otros métodos para gestión de usuarios, proveedores, etc.
 // En AdminController.java

    @GetMapping("/ventas")
    public String gestionarVentas(Model model) {
        // Lógica para cargar el reporte de ventas
        return "admins/reporte_ventas";
    }


    @GetMapping("/entradas")
    public String gestionarEntradas(Model model) {
        // Lógica para el CRUD de entradas (stock)
    	model.addAttribute("entrada", new Entrada());
    	model.addAttribute("listaEntrada", entradaService.listarEntrada());

        return "admins/crud_entradas";
    }
    @PostMapping("/entradas")
    public String gestionarEntradas(@ModelAttribute Entrada entrada, RedirectAttributes redirectAttrs) {
    	try {
			entradaService.nuevaEntrada(entrada);
			redirectAttrs.addFlashAttribute("mensaje", "Entrada registrada correctamente.");
			redirectAttrs.addFlashAttribute("cssmensaje", "alert alert-success");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("mensaje", "Error al registrar.");
			redirectAttrs.addFlashAttribute("cssmensaje", "alert alert-danger");
		}
    	return "redirect:/Admin/entradas";
    }
    @GetMapping("/editarEntrada/{id_entrada}")
    public String editarEntrada(Model model, @PathVariable Integer id_entrada) {
    	Entrada entrada = entradaService.buscarPorId(id_entrada);
    	System.out.println("Producto a editar" + entrada.getId_entrada());

    	model.addAttribute("entrada", entrada);
    	model.addAttribute("listaEntrada", entradaService.listarEntrada());

    	return "admins/crud_entradas";
    }
    @PostMapping("/editarEntrada/{id_entrada}")
    public String editarEntrada(@ModelAttribute Entrada entrada, RedirectAttributes redirectAttrs) {
    	if(entradaService.actualizarEntrada(entrada)) {
			redirectAttrs.addFlashAttribute("mensaje", "Entrada actualizada correctamente.");
			redirectAttrs.addFlashAttribute("cssmensaje", "alert alert-success");
		} else {
			redirectAttrs.addFlashAttribute("mensaje", "Error al registrar.");
			redirectAttrs.addFlashAttribute("cssmensaje", "alert alert-danger");
		}
    	return "redirect:/Admin/entradas";
    }
    @PostMapping("/eliminarEntrada/{id_entrada}")
    public String eliminarEntrada(@PathVariable Integer id_entrada, RedirectAttributes redirectAttrs) {
    	if(entradaService.eliminarEntrada(id_entrada)) {
    		redirectAttrs.addFlashAttribute("mensaje", "Entrada eliminado correctamente.");
	        redirectAttrs.addFlashAttribute("cssmensaje", "alert alert-success");
	    } else {
	        redirectAttrs.addFlashAttribute("mensaje", "Error al eliminar.");
	        redirectAttrs.addFlashAttribute("cssmensaje", "alert alert-danger");
	    }
    	return "redirect:/Admin/entradas";
    }

    // Método para actualizar el campo cantidad en la tabla
    @PostMapping("actualizarCantidadEntrada")
    public String actualizarCantidadEntrada(@RequestParam("id_entrada") Integer idEntrada, @RequestParam("cantidad_disponible") Integer cantidad) {
    	Entrada entrada = entradaService.buscarPorId(idEntrada);
    	if (entrada != null) {
    		entrada.setCantidad_disponible(cantidad);
    		entradaService.nuevaEntrada(entrada);
    	}
    	return "redirect:/Admin/entradas";
    }


    @GetMapping("/proveedores")
    public String controlarProveedores(Model model) {
        // Lógica para ver el listado de proveedores
        return "admins/listado_proveedores";
    }


    @GetMapping("/usuarios")
    public String gestionarUsuarios(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogeado");

        if (usuario == null) {
            return "redirect:/General/login";
        }

        // Aquí se agregaría la lógica para listar todos los usuarios, roles y permisos.
        model.addAttribute("nombreUsuarioLogeado", usuario.getNombreUsuario());
        model.addAttribute("listaUsuarios", usuarioService.listarUsuarios());
        model.addAttribute("usuarioActual", usuario);

        // Retornar una nueva vista para el CRUD de usuarios
        return "admins/crud_usuarios";
    }

    // Método para actualizar el Rol y el Estado
    @PostMapping("/actualizarRolEstado")
    @ResponseBody
    public String actualizarRolEstado(@RequestBody Map<String, Object> datos) {
        try {
            Integer id = Integer.parseInt(datos.get("id_usuario").toString());
            String rol = (String) datos.get("rol");
            String estadoStr = String.valueOf(datos.get("estado")); // llega como texto

            Boolean estado = Boolean.parseBoolean(estadoStr); // convierte correctamente

            Usuario u = usuarioService.buscarPorId(id);
            if (u == null) {
                return "Usuario no encontrado";
            }

            u.setRol(rol);
            u.setEstado(estado);
            usuarioService.actualizarRolEstado(u);

            return "Datos actualizados correctamente ✅";
        } catch (Exception e) {
            return "Error al actualizar: " + e.getMessage();
        }
    }





}