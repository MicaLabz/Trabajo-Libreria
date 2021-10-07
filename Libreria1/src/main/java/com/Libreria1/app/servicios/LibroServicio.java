package com.Libreria1.app.servicios;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.Libreria1.app.entidades.Autor;
import com.Libreria1.app.entidades.Editorial;
import com.Libreria1.app.entidades.Libro;
import com.Libreria1.app.errores.ErrorServicio;
import com.Libreria1.app.repositorios.AutorRepositorio;
import com.Libreria1.app.repositorios.EditorialRepositorio;
import com.Libreria1.app.repositorios.LibroRepositorio;

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
	
	@Transactional
	public Libro ingresarLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String idAutor, String idEditorial) throws Exception {
		if(titulo == null || titulo.isEmpty()) {
			throw new ErrorServicio("Titulo del Libro es null");
		}
		Optional <Editorial> result = editorialRepositorio.findById(idEditorial);
		Optional <Autor> result1 = autorRepositorio.findById(idAutor);
		
		if(result.isEmpty() || result1.isEmpty()) {
			throw new Exception("No se encontro data");
		}else {
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
	}
	
	@Transactional
	public Libro modificarLibro(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String idAutor, String nombre, String idEditorial, String nombre1 ) throws Exception {
		
		Optional <Editorial> result = editorialRepositorio.findById(idEditorial);
		Optional <Autor> result1 = autorRepositorio.findById(idAutor);
		Optional<Libro> result2 = libroRepositorio.findById(id);
		
		if (result.isEmpty() || result1.isEmpty() || result2.isEmpty()) {
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
	public void darBajaLibro(String id) throws Exception {
		Optional<Libro> respuesta = libroRepositorio.findById(id);
		if (respuesta.isEmpty()) {
			throw new Exception("No se encontro");
		}else {
			Libro libro = respuesta.get();
			libro.setAlta(false);
            libroRepositorio.save(libro);
            libroRepositorio.delete(libro);
	}
	}
	
	@Transactional
	public List<Libro> buscarLibros() {
		return libroRepositorio.findAll();
		
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

}
