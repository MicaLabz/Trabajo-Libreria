package com.Libreria1.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Libreria1.app.entidades.Autor;
import com.Libreria1.app.entidades.Libro;
import com.Libreria1.app.servicios.AutorServicio;
import com.Libreria1.app.servicios.LibroServicio;

@Controller
@RequestMapping("/libro")
public class LibroControlador {
	
        @Autowired	
        private LibroServicio libroServicio;
	
		@GetMapping("/lista")
		public String lista(ModelMap modelo){
			
			List<Libro> todosLibros = libroServicio.buscarLibros();

			modelo.addAttribute("libros", todosLibros);

			return "lista-libros";
		}
		
		@GetMapping("/modificar/{id}")
		public String modificar(ModelMap modelo, @PathVariable String id) {
			try {
			     Autor autor = autorServicio.obtenerAutor(id);
			     modelo.addAttribute("autor",autor);
			     return "modificar-autor";
			}catch(Exception e) {
				modelo.put("error", "falta algun dato");
				return "modificar-autor";
			}
		}
		
		
		@PostMapping("/modificar/{id}")
		public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam String nombre) {
			try {
			     autorServicio.modificarAutor(id, nombre);
			     return "redirect:/autor/lista";
			}catch(Exception e) {
				 modelo.put("error", "Falta algun dato");
				 return "modificar-autor";
			}
		}

		@GetMapping("/ingreso")
		public String ingreso() {
			return "ingreso-autor";
		}

		@PostMapping("/ingreso")
		public String ingresarAutor(ModelMap modelo, @RequestParam String nombre) {
			try {
			      autorServicio.ingresarAutor(nombre);
			      modelo.put("exito", "Ingreso exitoso!");
			      return "redirect:/autor/lista";
			}catch(Exception e) {
				  modelo.put("error", "Falta algun dato");
				  return "ingreso-autor";
			}
		}

		@GetMapping("/baja/{id}")
		public String baja(@PathVariable String id) {
			try {
			     autorServicio.darBajaAutor(id);
			     return "redirect:/autor/lista";
			
		    }catch(Exception e) {
			     return "redirect:/"; 
		    }
		
        }
}		

