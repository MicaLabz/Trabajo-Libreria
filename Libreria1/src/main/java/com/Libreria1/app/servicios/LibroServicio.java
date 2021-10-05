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
	
	@Transactional
	public void ingresarLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, Boolean alta, String idAutor, String idEditorial) throws ErrorServicio {
		if(titulo == null || titulo.isEmpty()) {
			throw new ErrorServicio("Titulo del Libro es null");
		}
		Editorial editorial = editorialRepositorio.findById(idEditorial).get(); 
		Autor autor = autorRepositorio.findById(idAutor).get();
		
		Libro libro = new Libro();
		libro.setIsbn(isbn);
		libro.setTitulo(titulo);
		libro.setAnio(anio);
		libro.setEjemplares(ejemplares);
		libro.setEjemplaresPrestados(ejemplaresPrestados);
		libro.setEjemplaresRestantes(ejemplaresRestantes);
		libro.setAlta(alta);
		libro.setAutor(autor);
		libro.setEditorial(editorial);
		
		libroRepositorio.save(libro);
		
	}
	
	@Transactional
	public void modificarLibro(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, Boolean alta, String idAutor, String idEditorial ) throws ErrorServicio {
		if(titulo == null || titulo.isEmpty()) {
			throw new ErrorServicio("Titulo del Libro es null");
		}
		Editorial editorial = editorialRepositorio.findById(idEditorial).get(); 
		Autor autor = autorRepositorio.findById(idAutor).get();

		Optional<Libro> respuesta = libroRepositorio.findById(id);
		if (respuesta.isPresent()) {
			Libro libro = respuesta.get();
			libro.setIsbn(isbn);
			libro.setTitulo(titulo);
			libro.setAnio(anio);
			libro.setEjemplares(ejemplares);
			libro.setEjemplaresPrestados(ejemplaresPrestados);
			libro.setEjemplaresRestantes(ejemplaresRestantes);
			libro.setAlta(alta);
			libro.setAutor(autor);
			libro.setEditorial(editorial);

			libroRepositorio.save(libro);
		} else {
			throw new ErrorServicio ("No se encontro el Libro buscado");

		}

	}

	@Transactional
	public void darBajaLibro(String id) throws ErrorServicio {
		Optional<Libro> respuesta = libroRepositorio.findById(id);
		if (respuesta.isPresent()) {
			Libro libro = respuesta.get();
			libro.setAlta(false);

			libroRepositorio.save(libro);
		} else {
			throw new ErrorServicio("No se encontro el Libro buscado");
		}

	}
	
	@Transactional
	public List<Libro> buscarLibros() {
		return libroRepositorio.findAll();
		
	}

}
