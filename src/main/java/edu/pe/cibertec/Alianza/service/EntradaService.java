package edu.pe.cibertec.Alianza.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.pe.cibertec.Alianza.model.CompraEntrada;
import edu.pe.cibertec.Alianza.model.CompraEntradaForm;
import edu.pe.cibertec.Alianza.model.Entrada;
import edu.pe.cibertec.Alianza.model.Usuario;
import edu.pe.cibertec.Alianza.repository.EntradaRepository;

@Service
public class EntradaService {

	@Autowired
	private EntradaRepository entradaRepository;
	@Autowired 
	private CompraEntradaService compraEntradaService; // Inyectar el nuevo servicio

	@Transactional
	public Entrada nuevaEntrada(Entrada entrada) {
		return entradaRepository.save(entrada);
	}

	@Transactional(readOnly = true)
	public List<Entrada> listarEntrada(){
		return entradaRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Entrada buscarPorId(Integer id) {
		return entradaRepository.findById(id).orElse(new Entrada());
	}

	@Transactional
	public boolean actualizarEntrada(Entrada entrada) {
		if(entrada == null || entrada.getId_entrada() == null || !entradaRepository.existsById(entrada.getId_entrada())) {
			return false;
		}
		entradaRepository.save(entrada);
		return true;
	}

	@Transactional
	public boolean eliminarEntrada(Integer id_entrada) {
		try {
			if(!entradaRepository.existsById(id_entrada)) {
				return false;
			}
			entradaRepository.deleteById(id_entrada);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	@Transactional // Aseguramos que la transacción incluya stock, compra y venta
	public CompraEntrada procesarCompra(CompraEntradaForm form, Usuario usuarioLogeado) {
	    
	    // 1. OBTENER Y VALIDAR DATOS (Stock y Cliente)
	    Entrada entrada = buscarPorId(form.getIdEntrada());
	    // Lógica para obtener el cliente, similar a la del registro
	    // Cliente cliente = clienteRepository.findByUsuario(usuarioLogeado); 

	    if (entrada == null || entrada.getCantidad_disponible() < form.getCantidad()) {
	        throw new RuntimeException("Stock insuficiente o entrada no disponible.");
	    }

	    // 2. CREAR EL OBJETO CompraEntrada A GUARDAR
	    CompraEntrada nuevaCompra = new CompraEntrada();
	    // (Asignar todos los campos del form y del usuario/cliente a nuevaCompra)
	    // newCompra.setCliente(cliente);
	    // newCompra.setEntrada(entrada);
	    // newCompra.setCantidad(form.getCantidad());
	    // newCompra.setPrecio_unitario(entrada.getPrecio());
	    // newCompra.setTotal(entrada.getPrecio() * form.getCantidad());

	    // 3. DESCONTAR STOCK (Actualiza la BD)
	    entrada.setCantidad_disponible(entrada.getCantidad_disponible() - form.getCantidad());
	    entradaRepository.save(entrada);
	    
	    // 4. DELEGAR PERSISTENCIA y OBTENER ID (Llamamos al servicio que enviaste)
	    CompraEntrada compraGuardada = compraEntradaService.guardarCompra(nuevaCompra);

	    // 5. REGISTRAR VENTA PARA EL ADMIN (Delegar a VentaService)
	    // ventaService.registrarVentaEntrada(compraGuardada); 
	    
	    return compraGuardada;
	}


}
