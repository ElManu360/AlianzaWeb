package edu.pe.cibertec.Alianza.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.pe.cibertec.Alianza.model.Usuario;
import edu.pe.cibertec.Alianza.model.Cliente; // Importar Cliente
import edu.pe.cibertec.Alianza.model.Registro; // Importar el DTO/Modelo Registro
import edu.pe.cibertec.Alianza.repository.ClienteRepository;
import edu.pe.cibertec.Alianza.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
    private UsuarioRepository usuarioRepository;
	
	@Autowired
    private ClienteRepository clienteRepository;

    // --- Métodos CRUD/Transaccionales (Siguiendo tu patrón) ---

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        // Lógica de validación si es necesaria
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Integer id) {
    	return usuarioRepository.findById(id).orElse(new Usuario());
    }

    @Transactional
    public void actualizarRolEstado(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void eliminarUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }

    // --- Lógica Específica de Login ---

    @Transactional(readOnly = true)
    public Usuario obtenerUsuarioPorNombre(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    /**
     * Verifica las credenciales para el login.
     */
    @Transactional(readOnly = true)
    public Usuario verificarCredenciales(String nombreUsuario, String password) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);

        if (usuario != null && usuario.getEstado()) {
            if (usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        return null;
    }
    
    // --- LÓGICA DE REGISTRO DUAL (USUARIO Y CLIENTE) ---
    
    /**
     * Registra un nuevo Usuario con rol 'USUARIO' y crea su registro Cliente asociado.
     * @param request Datos completos del registro.
     * @return El objeto Usuario registrado.
     */
    @Transactional // ¡CRÍTICO! Asegura que ambas inserciones (Usuario y Cliente) sean atómicas
    public Usuario registrarUsuarioYCliente(Registro request) { 
        
        // 1. Crear y guardar el objeto Usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(request.getNombreUsuario());
        nuevoUsuario.setPassword(request.getPassword()); 
        nuevoUsuario.setEmail(request.getEmail());
        nuevoUsuario.setRol("Usuario"); 
        
        // Guardar el Usuario (esto genera el id_usuario)
        nuevoUsuario = usuarioRepository.save(nuevoUsuario);
        
        // 2. Crear y guardar el objeto Cliente, vinculado al Usuario
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setDocumentoIdentidad(request.getDocumentoIdentidad());
        nuevoCliente.setNombres(request.getNombres());
        nuevoCliente.setApellidos(request.getApellidos());
        nuevoCliente.setTelefono(request.getTelefono());
        
        // Establece la relación FK
        nuevoCliente.setUsuario(nuevoUsuario); 
        
        // Guardar el Cliente
        clienteRepository.save(nuevoCliente);
        
        return nuevoUsuario;
    }
    
    // --- FIN LÓGICA DE REGISTRO DUAL ---
}