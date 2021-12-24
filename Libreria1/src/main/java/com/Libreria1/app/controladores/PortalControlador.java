package com.Libreria1.app.controladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Libreria1.app.entidades.Autor;
import com.Libreria1.app.entidades.Cliente;
import com.Libreria1.app.entidades.Editorial;
import com.Libreria1.app.entidades.Libro;
import com.Libreria1.app.entidades.Prestamo;
import com.Libreria1.app.servicios.AutorServicio;
import com.Libreria1.app.servicios.ClienteServicio;
import com.Libreria1.app.servicios.EditorialServicio;
import com.Libreria1.app.servicios.LibroServicio;
import com.Libreria1.app.servicios.PrestamoServicio;

@Controller
@RequestMapping("/")
public class PortalControlador {
	
	@Autowired
	private AutorServicio autorServicio;
	
	@Autowired
	private EditorialServicio editorialServicio;
	
	@Autowired
	private LibroServicio libroServicio;
	
	@Autowired
	private ClienteServicio clienteServicio; 
	
	@Autowired
	private PrestamoServicio prestamoServicio;
	
	@GetMapping("/")
	public String index( ModelMap modelo) {
		
		List<Autor> autoresTodos = autorServicio.buscarAutores();
		
		modelo.addAttribute("autores", autoresTodos);
        
		List<Editorial> editorialTodos = editorialServicio.buscarEditoriales();
		
		modelo.addAttribute("editoriales", editorialTodos);
		
        List<Libro> librosTodos = libroServicio.buscarLibros();
		
		modelo.addAttribute("libros", librosTodos);
		
        List<Cliente> clientesTodos = clienteServicio.buscarClientes();
		
		modelo.addAttribute("clientes", clientesTodos);
        
		List<Prestamo> prestamosTodos = prestamoServicio.buscarPrestamos();
		
		modelo.addAttribute("prestamos", prestamosTodos);
		
		return "portal";
	}
	
	@GetMapping("/login")
	public String login(@RequestParam(required = false) String error, ModelMap modelo ) {
		 if(error != null) {
			 modelo.put("error", "Documento o clave incorrecta");
		 }
		 return "cliente/login";
	}
	
}	
	