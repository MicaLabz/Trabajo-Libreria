package com.Libreria1.app.servicios;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.AccessOptions.SetOptions.Propagation;
import org.springframework.stereotype.Service;
import com.Libreria1.app.entidades.Autor;
import com.Libreria1.app.entidades.Libro;
import com.Libreria1.app.errores.ErrorServicio;
import com.Libreria1.app.repositorios.AutorRepositorio;
import com.Libreria1.app.repositorios.LibroRepositorio;

@Service
public class AutorServicio {
	
	@Autowired
	private AutorRepositorio autorRepositorio;
	
	@Autowired
	private LibroRepositorio libroRepositorio;
	
	@Autowired
	private LibroServicio libroServicio;
	
	@Transactional
	public Autor ingresarAutor(String nombre) throws Exception {
		
		if(nombre.isBlank()) {
			throw new Exception("Nombre del Autor es null");
		}
			Autor autor = new Autor();
			autor.setNombre(nombre);
			autor.setAlta(true);
			
			return autorRepositorio.save(autor);
		
	}
	
	@Transactional
	public Autor modificarAutor(String id, String nombre) throws Exception {
	    //List <Autor> autores = autorRepositorio.buscarPorNombre(nombre);
	    //System.out.println(autores.size());
	    //if (autores.size() > 1){
			//throw new Exception("No puede haber 2 autores con el mismo nombre");
		//}else {
			Autor autor = autorRepositorio.getById(id);
			autor.setNombre(nombre);
	        return autorRepositorio.save(autor);
		}

	
	
	@Transactional
	public Autor darBajaAutor(String id) {
		Libro libro = libroRepositorio.buscarLibroPorIdAutor(id);
		if(libro == null) {
			Autor autor = autorRepositorio.getById(id);
			autor.setAlta(false);
			return autor;
		}else {
		String idLibro = libro.getId();
		libroServicio.darBajaLibro(idLibro);
		Autor autor = autorRepositorio.getById(id);
		autor.setAlta(false);
		return autor;
		}
	}
	
	
	@Transactional
	public Autor darAltaAutor(String id) throws Exception{
		Libro libro = libroRepositorio.buscarLibroPorIdAutor(id);
		if(libro == null) {
			Autor autor = autorRepositorio.getById(id);
			autor.setAlta(true);
			return autor;
		}else {
		String idLibro = libro.getId();
		libroServicio.darAltaLibro(idLibro);
		Autor autor = autorRepositorio.getById(id);
		autor.setAlta(true);
		return autor;
		}
	}
	
	@Transactional
	public void eliminarAutor(String id) throws Exception{
		Libro libro = libroRepositorio.buscarLibroPorIdAutor(id);
		if(libro == null) {
			Autor autor = autorRepositorio.getById(id);
			autorRepositorio.delete(autor);
		}else {
		String idLibro = libro.getId();
		libroServicio.eliminarLibro(idLibro);
		Autor autor = autorRepositorio.getById(id);
		autorRepositorio.delete(autor);
	}
}
	
	
	
	@Transactional
	public List<Autor> buscarAutores() {
		return autorRepositorio.findAll();
	}
	
	@Transactional
	public List<Autor> listarActivos() {
		return autorRepositorio.buscarActivos();
	}	
	
	@Transactional
	public Autor obtenerAutor(String id) throws Exception{
		Optional<Autor> result = autorRepositorio.findById(id);
	       
	    if(result.isEmpty()) {
	    	throw new Exception("No se encontro");
	    }else {
		Autor autor = result.get();
		return autor;
	}
	}
	    
	public List <Autor> obtenerAutoresPorNombre(String nombre) throws Exception {
		Optional<List<Autor>> result = Optional.of(autorRepositorio.buscarPorNombre(nombre));
	       
	    if(result.isEmpty()) {
	    	System.out.println("sad");
	    	throw new Exception("No se encontro");
	    }else {
		List <Autor> autores1  = result.get();
		return autores1;
	}
	    
}
	
	public Autor obtenerAutorPorNombre(String nombre) throws Exception {
		Optional<Autor> result = autorRepositorio.buscarPorNombre1(nombre);
	       
	    if(result.isEmpty()) {
	    	System.out.println("sad");
	    	throw new Exception("No se encontro");
	    }else {
		Autor autor  = result.get();
		return autor;
	}
}
}