package com.Libreria1.app.servicios;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.AccessOptions.SetOptions.Propagation;
import org.springframework.stereotype.Service;
import com.Libreria1.app.entidades.Autor;
import com.Libreria1.app.errores.ErrorServicio;
import com.Libreria1.app.repositorios.AutorRepositorio;

@Service
public class AutorServicio {
	
	@Autowired
	private AutorRepositorio autorRepositorio;
	
	@Transactional
	public Autor ingresarAutor(String nombre) throws ErrorServicio {
		
		if(nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("Nombre del Autor es null");
		}
			Autor autor = new Autor();
			autor.setNombre(nombre);
			autor.setAlta(true);
			
			return autorRepositorio.save(autor);
		
	}
	
	@Transactional
	public Autor modificarAutor(String id, String nombre) throws Exception {
		Optional<Autor> result = autorRepositorio.findById(id);
		if(result.isEmpty()) {
			throw new Exception("Nombre del Autor es null");
		}else {
        Autor autor = result.get();
		autor.setNombre(nombre);
        return autorRepositorio.save(autor);

		}
	}
	
	
	@Transactional
	public void darBajaAutor(String id) throws Exception {
		Optional<Autor> result = autorRepositorio.findById(id);
       
	    if(result.isEmpty()) {
	    	throw new Exception("No se encontro");
	    }else {
		Autor autor = result.get();
        autor.setAlta(false);
		autorRepositorio.save(autor);
		autorRepositorio.delete(autor);
	}
	}
	
	@Transactional
	public List<Autor> buscarAutores() {
		return autorRepositorio.findAll();
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
}