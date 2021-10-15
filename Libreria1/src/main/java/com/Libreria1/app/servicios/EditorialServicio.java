package com.Libreria1.app.servicios;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Libreria1.app.entidades.Autor;
import com.Libreria1.app.entidades.Editorial;
import com.Libreria1.app.entidades.Libro;
import com.Libreria1.app.errores.ErrorServicio;
import com.Libreria1.app.repositorios.EditorialRepositorio;
import com.Libreria1.app.repositorios.LibroRepositorio;

@Service
public class EditorialServicio {
	
	@Autowired
	private EditorialRepositorio editorialRepositorio;
	
	@Autowired
	private LibroRepositorio libroRepositorio;
	
	@Autowired
	private LibroServicio libroServicio;
	
	@Transactional
	public Editorial ingresarEditorial(String nombre) throws Exception{
		if(nombre.isBlank()) {
			throw new Exception("Nombre de la Editorial es null");
		}else {
			Editorial editorial = new Editorial();
			editorial.setNombre(nombre);
			editorial.setAlta(true);
			
			return editorialRepositorio.save(editorial);
		}
		
	}
	
	@Transactional
	public Editorial modificarEditorial(String id, String nombre) throws Exception {

			Editorial editorial = editorialRepositorio.getById(id);
			editorial.setNombre(nombre);
			return editorialRepositorio.save(editorial);
		}
	
	@Transactional
	public Editorial darBajaEditorial(String id) throws Exception {
		Libro libro = libroRepositorio.buscarLibroPorIdEditorial(id);
		if(libro == null) {
			Editorial editorial = editorialRepositorio.getById(id);
			editorial.setAlta(false);
			return editorial;
		}else {
		String idLibro = libro.getId();
		libroServicio.darBajaLibro(idLibro);
		Editorial editorial = editorialRepositorio.getById(id);
		editorial.setAlta(false);
		return editorial;
		}
	}
	
	@Transactional
	public Editorial darAltaEditorial(String id) throws Exception{
		Libro libro = libroRepositorio.buscarLibroPorIdEditorial(id);
		if(libro == null) {
			Editorial editorial = editorialRepositorio.getById(id);
			editorial.setAlta(true);
			return editorial;
		}else {
		String idLibro = libro.getId();
		libroServicio.darAltaLibro(idLibro);
		Editorial editorial = editorialRepositorio.getById(id);
		editorial.setAlta(true);
		return editorial;
		}
	}
	
	@Transactional
	public void eliminarEditorial(String id) throws Exception{
		Libro libro = libroRepositorio.buscarLibroPorIdEditorial(id);
		if(libro == null) {
			Editorial editorial = editorialRepositorio.getById(id);
			editorialRepositorio.delete(editorial);
		}else {
		String idLibro = libro.getId();
		libroServicio.eliminarLibro(idLibro);
		Editorial editorial = editorialRepositorio.getById(id);
		editorialRepositorio.delete(editorial);
	}
	}
	
	@Transactional
	public List<Editorial> buscarEditoriales() {
		return editorialRepositorio.findAll();
	}
	
	@Transactional
	public List<Editorial> listarActivos() {
		return editorialRepositorio.buscarActivos();
	}
	
	@Transactional
	public Editorial obtenerEditorial(String id) throws Exception{
		Optional<Editorial> result = editorialRepositorio.findById(id);
	       
	    if(result.isEmpty()) {
	    	throw new Exception("No se encontro");
	    }else {
		Editorial editorial = result.get();
		return editorial;
	}
	}
	
	public List <Editorial> obtenerEditorialesPorNombre(String nombre) throws Exception {
		Optional<List<Editorial>> result = Optional.of(editorialRepositorio.buscarPorNombre(nombre));
	       
	    if(result.isEmpty()) {
	    	System.out.println("sad");
	    	throw new Exception("No se encontro");
	    }else {
		List <Editorial> editoriales  = result.get();
		return editoriales;
	}
	}
	
	public Editorial obtenerEditorialPorNombre(String nombre) throws Exception {
		Optional<Editorial> result = editorialRepositorio.buscarPorNombre1(nombre);
	       
	    if(result.isEmpty()) {
	    	throw new Exception("No se encontro");
	    }else {
		Editorial editorial = result.get();
		return editorial;
	}
}
}