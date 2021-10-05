package com.Libreria1.app.controladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.Libreria1.app.entidades.Autor;
import com.Libreria1.app.entidades.Editorial;
import com.Libreria1.app.entidades.Libro;
import com.Libreria1.app.servicios.AutorServicio;
import com.Libreria1.app.servicios.EditorialServicio;
import com.Libreria1.app.servicios.LibroServicio;

@Controller
@RequestMapping("/")
public class PortalControlador {
	
	@Autowired
	private AutorServicio autorServicio;
	
	@Autowired
	private EditorialServicio editorialServicio;
	
	@Autowired
	private LibroServicio libroServicio;
	
	@GetMapping("/")
	public String index( ModelMap modelo) {
		
		List<Autor> autoresTodos = autorServicio.buscarAutores();
		
		modelo.addAttribute("autores", autoresTodos);
        
		List<Editorial> editorialTodos = editorialServicio.buscarEditoriales();
		
		modelo.addAttribute("editoriales", editorialTodos);
		
        List<Libro> librosTodos = libroServicio.buscarLibros();
		
		modelo.addAttribute("libros", librosTodos);
		
		return "portal";
	}
}	
	