package com.Libreria1.app.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Libreria1.app.entidades.Cliente;
import com.Libreria1.app.entidades.Libro;
import com.Libreria1.app.entidades.Prestamo;
import com.Libreria1.app.repositorios.ClienteRepositorio;
import com.Libreria1.app.repositorios.LibroRepositorio;
import com.Libreria1.app.repositorios.PrestamoRepositorio;

@Service
public class PrestamoServicio {
	
	
	@Autowired
	LibroRepositorio libroRepositorio;
	
	@Autowired
	ClienteRepositorio clienteRepositorio;
	
	@Autowired
	PrestamoRepositorio prestamoRepositorio;
	
	@Autowired
	LibroServicio libroServicio;
	
	@Autowired
	ClienteServicio clienteServicio;
	
	@Transactional
	public Prestamo ingresarPrestamo(Date fechaPrestamo, Date fechaDevolucion, String idLibro,  String idCliente) throws Exception {
		
		Optional <Libro> result = libroRepositorio.findById(idLibro);
		Optional <Cliente> result1 = Optional.of(clienteRepositorio.getById(idCliente));
		if(result.isEmpty()) {
			throw new Exception("No se encontro ese Libro");
		}
		if(result1.isEmpty()) {
			throw new Exception("No se encontro ese Cliente");
		}
		Libro libro = result.get();
		Cliente cliente = result1.get();
		Prestamo prestamo = new Prestamo();
		prestamo.setFechaPrestamo(fechaPrestamo);
		prestamo.setFechaDevolucion(fechaDevolucion);
		prestamo.setAlta(true);
		prestamo.setLibro(libro);
		prestamo.setCliente(cliente);
		
		return prestamoRepositorio.save(prestamo);
		}
	
	
	@Transactional
	public Prestamo modificarPrestamo(String id, Date fechaPrestamo, Date fechaDevolucion, String idLibro, String idCliente ) throws Exception {
		
		Optional <Libro> result = libroRepositorio.findById(idLibro);
		Optional <Cliente> result1 = clienteRepositorio.findById(idCliente);
		Optional <Prestamo> result2 = prestamoRepositorio.findById(id);
		if (!(result.isPresent() || result1.isPresent() || result2.isPresent())) {
			throw new Exception("Datos no estan");
		}else {	
			Libro libro = result.get();
			//String idLibro = libro.getId();
			Long isbn = libro.getIsbn();
			String titulo = libro.getTitulo();
			Integer anio = libro.getAnio();
			Integer ejemplares = libro.getEjemplares();
			Integer ejemplaresPrestados = libro.getEjemplaresPrestados();
			Integer ejemplaresRestantes = libro.getEjemplaresRestantes();
			String IdAutor = libro.getAutor().getId();
			String nombreA = libro.getAutor().getNombre();
			String IdEditorial = libro.getEditorial().getId();
			String nombreE = libro.getEditorial().getNombre();
			libroServicio.modificarLibro(idLibro, isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, IdAutor, nombreA, IdEditorial, nombreE);
			Cliente cliente = result1.get();
			//String idCliente = cliente.getId();
			Long documento = cliente.getDocumento();
		    String nombre = cliente.getNombre();
		    String apellido = cliente.getApellido();
		    String telefono = cliente.getTelefono();
			clienteServicio.modificarCliente(idCliente, documento, nombre, apellido, telefono);
			Prestamo prestamo = result2.get();
			prestamo.setFechaPrestamo(fechaPrestamo);
			prestamo.setFechaDevolucion(fechaDevolucion);
			prestamo.setLibro(libro);
			prestamo.setCliente(cliente);

			return prestamoRepositorio.save(prestamo);

		}
	}

	@Transactional
	public Prestamo darBajaPrestamo(String id) {
		Prestamo prestamo = prestamoRepositorio.getById(id);
		prestamo.setAlta(false);
		return prestamoRepositorio.save(prestamo);
		
	}
	
	
	@Transactional
	public Prestamo darAltaPrestamo(String id) throws Exception{
        Prestamo prestamo = prestamoRepositorio.getById(id);
		prestamo.setAlta(true);
		return prestamoRepositorio.save(prestamo);
	}
	
	@Transactional
	public void eliminarPrestamo(String id) throws Exception{
          Prestamo prestamo = prestamoRepositorio.getById(id);
		  prestamoRepositorio.delete(prestamo);
	}
	
	@Transactional
	public List<Prestamo> buscarPrestamos() {
		return prestamoRepositorio.findAll();
		
	}
	
	@Transactional
	public List<Prestamo> listarActivos() {
		return prestamoRepositorio.buscarActivos();
	}
	
	@Transactional
	public Prestamo obtenerPrestamo(String id) throws Exception{
		Optional<Prestamo> result = prestamoRepositorio.findById(id);
	       
	    if(result.isEmpty()) {
	    	throw new Exception("No se encontro");
	    }else {
		Prestamo prestamo = result.get();
		return prestamo;
	}
	}

}
