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

@Service
public class EditorialServicio {
	
	@Autowired
	private EditorialRepositorio editorialRepositorio;
	
	@Transactional
	public Editorial ingresarEditorial(String nombre) throws ErrorServicio{
		if(nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("Nombre de la Editorial es null");
		}
			Editorial editorial = new Editorial();
			editorial.setNombre(nombre);
			editorial.setAlta(true);
			
			return editorialRepositorio.save(editorial);
		
	}
	
	@Transactional
	public Editorial modificarEditorial(String id, String nombre) throws Exception {

		Optional<Editorial> result = editorialRepositorio.findById(id);
		if (result.isEmpty()) {
			throw new Exception("Nombre de la Editorial es null");
		} else {
			Editorial editorial = result.get();
			editorial.setNombre(nombre);
			return editorialRepositorio.save(editorial);
		}
	}
	
	@Transactional
	public void darBajaEditorial(String id) throws Exception {
		Optional<Editorial> result = editorialRepositorio.findById(id);
	       
	    if(result.isEmpty()) {
	    	throw new Exception("No se encontro");
	    }else {
		Editorial editorial = result.get();
        editorial.setAlta(false);
		editorialRepositorio.save(editorial);
		editorialRepositorio.delete(editorial);
	    }
	}
	
	@Transactional
	public List<Editorial> buscarEditoriales() {
		return editorialRepositorio.findAll();
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
}