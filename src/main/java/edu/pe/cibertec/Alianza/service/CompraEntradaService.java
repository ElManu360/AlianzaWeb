package edu.pe.cibertec.Alianza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.pe.cibertec.Alianza.model.CompraEntrada;
import edu.pe.cibertec.Alianza.model.CompraEntradaForm; 
import edu.pe.cibertec.Alianza.model.Entrada;
import edu.pe.cibertec.Alianza.model.Usuario;
import edu.pe.cibertec.Alianza.model.Cliente;
import edu.pe.cibertec.Alianza.model.Venta;
import edu.pe.cibertec.Alianza.repository.CompraEntradaRepository;
import edu.pe.cibertec.Alianza.repository.EntradaRepository;
import edu.pe.cibertec.Alianza.repository.ClienteRepository;
import edu.pe.cibertec.Alianza.repository.VentaRepository;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

@Service
public class CompraEntradaService {

    // --- Repositorios y Servicios Necesarios ---
    @Autowired private CompraEntradaRepository compraEntradaRepository;
    @Autowired private EntradaRepository entradaRepository;
    @Autowired private ClienteRepository clienteRepository; 
    @Autowired private VentaRepository ventaRepository; 
    @Autowired
    private DataSource dataSource;

    @Transactional(readOnly = true)
    public List<Entrada> listarEntradasDisponibles() {
        // Asumiendo que quieres todas las entradas sin un filtro complejo por ahora
        // Si necesitas filtrar por stock > 0, deberías crear un método en EntradaRepository
        return entradaRepository.findAll();
    }
    
    /**
     * Método para obtener una entrada por su ID (usado en el controlador).
     */
    @Transactional(readOnly = true)
    public Entrada obtenerEntradaPorId(Integer id) {
        return entradaRepository.findById(id).orElse(null);
    }
    
    // --- Nota: El método listarEntradasDisponibles debe estar aquí o en otro Service ---
    
    // -----------------------------------------------------------------------
    // MÉTODO CRÍTICO: PROCESAR COMPRA (Soluciona el error de compilación)
    // -----------------------------------------------------------------------
    
    @Transactional 
    public CompraEntrada procesarCompra(CompraEntradaForm form, Usuario usuarioLogeado) {
        
        // 1. OBTENER ENTIDADES NECESARIAS
        Entrada entrada = obtenerEntradaPorId(form.getIdEntrada());
        // Buscamos el Cliente usando el Objeto Usuario logeado
        Cliente cliente = clienteRepository.findByUsuario(usuarioLogeado); 

        // 2. VALIDACIÓN CRÍTICA (Si cliente es NULL, lanzamos error y evitamos el rollback silencioso)
        if (entrada == null || cliente == null) {
            throw new RuntimeException("Cliente no autenticado o Entrada inválida. (CLIENTE_NULL)");
        }
        if (entrada.getCantidad_disponible() < form.getCantidad()) {
            throw new RuntimeException("Stock insuficiente para la cantidad solicitada.");
        }

        double precioUnitario = entrada.getPrecio();
        double montoTotal = precioUnitario * form.getCantidad();
        
     // 3. Descontar Stock y Guardar (DEBE FALLAR AQUÍ)
        entrada.setCantidad_disponible(entrada.getCantidad_disponible() - form.getCantidad());
        entradaRepository.save(entrada);
        
     // 4. CREAR Y GUARDAR COMPRAENTRADA (Ticket)
        CompraEntrada nuevaCompra = new CompraEntrada();

        // ESTAS LÍNEAS DEBEN ESTAR ACTIVAS Y USAR LOS SETTERS DE OBJETO
        nuevaCompra.setCliente(cliente); 
        nuevaCompra.setEntrada(entrada); // <<-- Asigna el objeto Entrada (FK id_entrada_fk)
        
        nuevaCompra.setCantidad(form.getCantidad());
        nuevaCompra.setPrecioUnitario(precioUnitario); 
        nuevaCompra.setTotal(montoTotal);
        nuevaCompra.setMetodo_pago(form.getMetodoPago());
        nuevaCompra.setNumeroAsiento("AL-" + entrada.getZona().substring(0, 1) + (int)(Math.random() * 9999));
        
        CompraEntrada compraGuardada = compraEntradaRepository.save(nuevaCompra);
        
     // 5. CREAR Y GUARDAR VENTA
        Venta ventaAdmin = new Venta();

        // ESTA LÍNEA DEBE ESTAR ACTIVA
        ventaAdmin.setCliente(cliente); // <<-- Asigna el objeto Cliente (FK id_cliente_fk)
        
        ventaAdmin.setMontoTotal(BigDecimal.valueOf(montoTotal)); 
        ventaAdmin.setTipo("Entrada");
        ventaAdmin.setIdReferenciaFk(compraGuardada.getId_compra()); 
        ventaAdmin.setEstado("Completada");
        
        ventaRepository.save(ventaAdmin);

        return compraGuardada;
    }
    
    // -----------------------------------------------------------------------
    // MÉTODOS AUXILIARES
    // -----------------------------------------------------------------------

    @Transactional 
	public CompraEntrada guardarCompra(CompraEntrada compraEntrada) {
        // Asume que el objeto ya viene con precioUnitario y cantidad llenos.
		double total = compraEntrada.getCantidad() * compraEntrada.getPrecioUnitario(); 
		compraEntrada.setTotal(total);
        
		return compraEntradaRepository.save(compraEntrada);
	}

   /* public byte[] generarBoletaPDF(int idCompra) {
        
        // Si la librería de Jasper no está en el pom, esta sección dará error.
        try (java.sql.Connection connection = dataSource.getConnection()) { // Usar try-with-resources
            
            // 1. Cargar el archivo .jrxml
            InputStream inputStream = getClass().getResourceAsStream("/static/reporteentrada.jrxml");
            
            if (inputStream == null) {
                 // Si el archivo no se encuentra, lanza una excepción clara
                 throw new RuntimeException("Error: Archivo de reporte no encontrado en /static/reporteentrada.jrxml");
            }
            
            // 2. Parámetros del Reporte (Incluyendo el ID de la boleta)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ID_COMPRA", idCompra); // Parámetro del filtro SQL
            
            // 3. Compilar y Llenar el Reporte
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            
            // 4. Exportar a PDF y devolver los bytes
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            // En caso de error (BD, Jasper, Archivo), lo lanzamos para que el controlador lo capture
            // La excepción ahora será visible en la consola.
            throw new RuntimeException("Fallo en la generación del PDF con JasperReports.", e);
        }
    }*/
}