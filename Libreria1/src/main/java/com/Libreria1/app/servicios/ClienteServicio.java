package com.Libreria1.app.servicios;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Libreria1.app.entidades.Cliente;
import com.Libreria1.app.entidades.Prestamo;
import com.Libreria1.app.repositorios.ClienteRepositorio;
import com.Libreria1.app.repositorios.PrestamoRepositorio;

@Service
public class ClienteServicio {
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	
	@Autowired
	private PrestamoRepositorio prestamoRepositorio;
	
	@Autowired
	private PrestamoServicio prestamoServicio;
	
	@Transactional
	public Cliente ingresarCliente(Long documento, String nombre, String apellido, String telefono) throws Exception {
		
		if(nombre.isBlank()) {
			throw new Exception("Nombre del Cliente es null");
		}
			Cliente cliente = new Cliente();
			cliente.setDocumento(documento);
			cliente.setNombre(nombre);
			cliente.setApellido(apellido);
			cliente.setTelefono(telefono);
			cliente.setAlta(true);
			
			return clienteRepositorio.save(cliente);
	}
	
	@Transactional
	public Cliente modificarCliente(String id, Long documento, String nombre, String apellido, String telefono ) throws Exception {
			Cliente cliente = clienteRepositorio.getById(id);
			cliente.setDocumento(documento);
			cliente.setNombre(nombre);
			cliente.setApellido(apellido);
			cliente.setTelefono(telefono);
	        return clienteRepositorio.save(cliente);
		}

	@Transactional
	public Cliente darBajaCliente(String id) {
		Prestamo prestamo = prestamoRepositorio.buscarPrestamoPorIdCliente(id);
		if(prestamo == null) {
			Cliente cliente = clienteRepositorio.getById(id);
			cliente.setAlta(false);
			return cliente;
		}else {
		String idPrestamo = prestamo.getId();
		prestamoServicio.darBajaPrestamo(idPrestamo);
		Cliente cliente = clienteRepositorio.getById(id);
		cliente.setAlta(false);
		return cliente;
		}
	}
	
	
	@Transactional
	public Cliente darAltaCliente(String id) throws Exception{
		Prestamo prestamo = prestamoRepositorio.buscarPrestamoPorIdCliente(id);
		if(prestamo == null) {
			Cliente cliente = clienteRepositorio.getById(id);
			cliente.setAlta(true);
			return cliente;
		}else {
		String idPrestamo = prestamo.getId();
		prestamoServicio.darAltaPrestamo(idPrestamo);
		Cliente cliente = clienteRepositorio.getById(id);
		cliente.setAlta(false);
		return cliente;
		}
	}
	
	@Transactional
	public void eliminarCliente(String id) throws Exception{
		Prestamo prestamo = prestamoRepositorio.buscarPrestamoPorIdCliente(id);
		if(prestamo == null) {
			Cliente cliente = clienteRepositorio.getById(id);
			clienteRepositorio.delete(cliente);
		}else {
		String idPrestamo = prestamo.getId();
		prestamoServicio.eliminarPrestamo(idPrestamo);
		Cliente cliente = clienteRepositorio.getById(id);
		clienteRepositorio.delete(cliente);
	}
}
	
	@Transactional
	public List<Cliente> buscarClientes() {
		return clienteRepositorio.findAll();
	}
	
	@Transactional
	public List<Cliente> listarActivos() {
		return clienteRepositorio.buscarActivos();
	}	
	
	@Transactional
	public Cliente obtenerCliente(String id) throws Exception{
		Optional<Cliente> result = clienteRepositorio.findById(id);
	       
	    if(result.isEmpty()) {
	    	throw new Exception("No se encontro");
	    }else {
		Cliente cliente = result.get();
		return cliente;
	}
	}
    
	public Cliente obtenerClientePorDocumento(Long documento) throws Exception {
		Optional<Cliente> result = clienteRepositorio.buscarPorDocumento(documento);
	       
	    if(result.isEmpty()) {
	    	throw new Exception("No se encontro");
	    }else {
		Cliente cliente  = result.get();
		return cliente;
	}
}
	
}
