package com.Libreria1.app.servicios;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Libreria1.app.entidades.Autor;
import com.Libreria1.app.entidades.Editorial;
import com.Libreria1.app.entidades.Libro;
import com.Libreria1.app.entidades.Prestamo;
import com.Libreria1.app.repositorios.AutorRepositorio;
import com.Libreria1.app.repositorios.EditorialRepositorio;
import com.Libreria1.app.repositorios.LibroRepositorio;
import com.Libreria1.app.repositorios.PrestamoRepositorio;

@Service
public class LibroServicio {
	
	@Autowired
	private LibroRepositorio libroRepositorio;
	
	@Autowired
	private EditorialRepositorio editorialRepositorio;
	
	@Autowired
	private AutorRepositorio autorRepositorio;
	
	@Autowired
	private AutorServicio autorServicio;
	
	@Autowired
	private EditorialServicio editorialServicio;
	
	@Autowired
	private PrestamoRepositorio prestamoRepositorio;
	
	@Autowired
	private PrestamoServicio prestamoServicio;
	
	@Transactional
	public Libro ingresarLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String idAutor,  String idEditorial) throws Exception {
		
		Optional <Editorial> result = editorialRepositorio.findById(idEditorial);
		Optional <Autor> result1 = Optional.of(autorRepositorio.getById(idAutor));
		if(result1.isEmpty()) {
			throw new Exception("No se encontro ese Autor");
		}
		if(result.isEmpty()) {
			throw new Exception("No se encontro esa Editorial");
		}
		Editorial editorial = result.get();
		Autor autor = result1.get();
		Libro libro = new Libro();
		libro.setIsbn(isbn);
		libro.setTitulo(titulo);
		libro.setAnio(anio);
		libro.setEjemplares(ejemplares);
		libro.setEjemplaresPrestados(ejemplaresPrestados);
		libro.setEjemplaresRestantes(ejemplaresRestantes);
		libro.setAlta(true);
		libro.setAutor(autor);
		libro.setEditorial(editorial);
		
		return libroRepositorio.save(libro);
		}
	
	
	@Transactional
	public Libro modificarLibro(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String idAutor, String nombre, String idEditorial, String nombre1 ) throws Exception {
		
		Optional <Editorial> result = editorialRepositorio.findById(idEditorial);
		Optional <Autor> result1 = autorRepositorio.findById(idAutor);
		Optional<Libro> result2 = libroRepositorio.findById(id);
		if (!(result.isPresent() || result1.isPresent() || result2.isPresent())) {
			throw new Exception("Datos no estan");
		}else {	
			Editorial editorial = result.get();
			editorialServicio.modificarEditorial(idEditorial, nombre1);
			Autor autor = result1.get();
			autorServicio.modificarAutor(idAutor, nombre );
			Libro libro = result2.get();
			libro.setIsbn(isbn);
			libro.setTitulo(titulo);
			libro.setAnio(anio);
			libro.setEjemplares(ejemplares);
			libro.setEjemplaresPrestados(ejemplaresPrestados);
			libro.setEjemplaresRestantes(ejemplaresRestantes);
			libro.setAutor(autor);
			libro.setEditorial(editorial);

			return libroRepositorio.save(libro);

		}
	}

	@Transactional
	public Libro darBajaLibro(String id) {
		Libro libro = libroRepositorio.getById(id);
		libro.setAlta(false);
		return libroRepositorio.save(libro);
		
	}
	
	
	@Transactional
	public Libro darAltaLibro(String id) throws Exception{
        Libro libro = libroRepositorio.getById(id);
		libro.setAlta(true);
		return libroRepositorio.save(libro);
	}
	
	@Transactional
	public void eliminarLibro(String id) throws Exception{
		Prestamo prestamo = prestamoRepositorio.buscarPrestamoPorIdLibro(id);
		if(prestamo == null) {
			Libro libro  = libroRepositorio.getById(id);
			libroRepositorio.delete(libro);
		}else {
		String idPrestamo = prestamo.getId();
		prestamoServicio.eliminarPrestamo(idPrestamo);
		Libro libro = libroRepositorio.getById(id);
		libroRepositorio.delete(libro);
	}
	}
	
	@Transactional
	public List<Libro> buscarLibros() {
		return libroRepositorio.findAll();
		
	}
	
	@Transactional
	public List<Libro> listarActivos() {
		return libroRepositorio.buscarActivos();
	}
	
	@Transactional
	public Libro obtenerLibro(String id) throws Exception{
		Optional<Libro> result = libroRepositorio.findById(id);
	       
	    if(result.isEmpty()) {
	    	throw new Exception("No se encontro");
	    }else {
		Libro libro = result.get();
		return libro;
	}
	}
	
	@Transactional
	public Libro obtenerLibroPorTitulo(String titulo) throws Exception{
		Optional<Libro> result = libroRepositorio.buscarLibroPorTitulo(titulo);
	       
	    if(result.isEmpty()) {
	    	throw new Exception("No se encontro");
	    }else {
		Libro libro = result.get();
		return libro;
	}
	}
	
	

}
