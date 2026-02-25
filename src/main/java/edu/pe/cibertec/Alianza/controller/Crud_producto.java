package edu.pe.cibertec.Alianza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.pe.cibertec.Alianza.model.Producto;
import edu.pe.cibertec.Alianza.service.ProductoService;

import java.util.List;

@Controller
@RequestMapping("/proveedores")
public class Crud_producto {

    @Autowired
    private ProductoService productoService;

    // Mostrar CRUD productos
    @GetMapping("/crud_producto")
    public String mostrarCrudProductos(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("listaProductos", productoService.listarProductos());
        return "proveedores/crud_producto";
    }

    // Registrar producto
    @PostMapping("/crud_producto")
    public String registrarProducto(@ModelAttribute Producto producto, RedirectAttributes redirectAttrs) {
        try {
            productoService.nuevoProducto(producto);
            redirectAttrs.addFlashAttribute("mensaje", "Producto registrado correctamente.");
            redirectAttrs.addFlashAttribute("cssmensaje", "alert alert-success");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensaje", "Error al registrar producto.");
            redirectAttrs.addFlashAttribute("cssmensaje", "alert alert-danger");
        }
        return "redirect:/proveedores/crud_producto";
    }

    // Editar producto
    @GetMapping("/editarProducto/{id_producto}")
    public String editarProducto(@PathVariable Integer id_producto, Model model) {
        Producto producto = productoService.buscarPorId(id_producto);
        model.addAttribute("producto", producto);
        model.addAttribute("listaProductos", productoService.listarProductos());
        return "proveedores/crud_producto";
    }

    @PostMapping("/editarProducto/{id_producto}")
    public String guardarEdicionProducto(@ModelAttribute Producto producto, RedirectAttributes redirectAttrs) {
        if (productoService.actualizarProducto(producto)) {
            redirectAttrs.addFlashAttribute("mensaje", "Producto actualizado correctamente.");
            redirectAttrs.addFlashAttribute("cssmensaje", "alert alert-success");
        } else {
            redirectAttrs.addFlashAttribute("mensaje", "Error al actualizar producto.");
            redirectAttrs.addFlashAttribute("cssmensaje", "alert alert-danger");
        }
        return "redirect:/proveedores/crud_producto";
    }

    // Eliminar producto
    @PostMapping("/eliminarProducto/{id_producto}")
    public String eliminarProducto(@PathVariable Integer id_producto, RedirectAttributes redirectAttrs) {
        if (productoService.eliminarProducto(id_producto)) {
            redirectAttrs.addFlashAttribute("mensaje", "Producto eliminado correctamente.");
            redirectAttrs.addFlashAttribute("cssmensaje", "alert alert-success");
        } else {
            redirectAttrs.addFlashAttribute("mensaje", "Error al eliminar producto.");
            redirectAttrs.addFlashAttribute("cssmensaje", "alert alert-danger");
        }
        return "redirect:/proveedores/crud_producto";
    }

    // Actualizar stock
    @PostMapping("/actualizarStock")
    public String actualizarStock(@RequestParam("id_producto") Integer idProducto,
                                  @RequestParam("stock") Integer stock) {
        Producto producto = productoService.buscarPorId(idProducto);
        if (producto != null) {
            producto.setStock(stock);
            productoService.nuevoProducto(producto);
        }
        return "redirect:/proveedores/crud_producto";
    }

 
}
