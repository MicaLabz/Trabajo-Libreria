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
import com.Libreria1.app.entidades.Editorial;
import com.Libreria1.app.entidades.Libro;
import com.Libreria1.app.repositorios.AutorRepositorio;
import com.Libreria1.app.repositorios.EditorialRepositorio;
import com.Libreria1.app.repositorios.LibroRepositorio;
import com.Libreria1.app.servicios.AutorServicio;
import com.Libreria1.app.servicios.EditorialServicio;
import com.Libreria1.app.servicios.LibroServicio;

@Controller
@RequestMapping("/libro")
public class LibroControlador {
	
        @Autowired	
        private LibroServicio libroServicio;
        
        @Autowired 
        private AutorServicio autorServicio;
        
        @Autowired
        private EditorialServicio editorialServicio;
        
        @Autowired
        private AutorRepositorio autorRepositorio;
        
        @Autowired
        private EditorialRepositorio editorialRepositorio;
	
		@GetMapping("/lista")
		public String lista(ModelMap modelo){
			
			List<Libro> todosLibros = libroServicio.buscarLibros();

			modelo.addAttribute("libros", todosLibros);

			return "lista-libro";
		}
		
		@GetMapping("/modificar/{id}")
		public String modificar(ModelMap modelo, @PathVariable String id) {
			try {
			     Libro libro = libroServicio.obtenerLibro(id);
			     modelo.addAttribute("libro",libro);
			     List<Autor> autores = autorRepositorio.findAll();
				 modelo.put("autores", autores);
				 List <Editorial> editoriales = editorialRepositorio.findAll();
				 modelo.put("editoriales", editoriales);
			     return "modificar-libro";
			}catch(Exception e) {
				modelo.put("error", "falta algun dato");
				return "modificar-libro";
			}
		}
		
		
		@PostMapping("/modificar/{id}")
		public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam Integer ejemplaresRestantes,/*dudas*/ @RequestParam String nombre, @RequestParam String nombre1) {
			try {
				 Autor autor = autorServicio.obtenerAutorPorNombre(nombre);
				 String idAutor = autor.getId();
				 Editorial editorial = editorialServicio.obtenerEditorialPorNombre(nombre1);
				 String idEditorial = editorial.getId();
				 libroServicio.modificarLibro(id, isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes,/*dudas*/ idAutor, nombre, idEditorial, nombre1);
				 modelo.put("exito", "Ingreso exitoso!");
				 return "redirect:/libro/lista";
			}catch(Exception e) {
				System.out.println(e.getMessage());
				System.out.println(e.getStackTrace());
				 modelo.put("error", "Falta algun dato");
				 return "modificar-libro";
			}
		}

		@GetMapping("/ingreso")
		public String ingreso(ModelMap modelo) {
			List<Autor> autores = autorRepositorio.findAll();
			modelo.put("autores", autores);
			List <Editorial> editoriales = editorialRepositorio.findAll();
			modelo.put("editoriales", editoriales);
			return "ingreso-libro";
		}

		@PostMapping("/ingreso")
		public String ingresarLibro(ModelMap modelo, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam Integer ejemplaresRestantes, @RequestParam String nombre, @RequestParam String nombre1) throws Exception {
			try {
				  Autor aut = autorServicio.obtenerAutorPorNombre(nombre);
				  Editorial edit = editorialServicio.obtenerEditorialPorNombre(nombre1);
				  String idEditorial = edit.getId();
				  String idAutor = aut.getId();
				  libroServicio.ingresarLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, idAutor , idEditorial);
			      modelo.put("exito", "Ingreso exitoso!");
			      return "redirect:/libro/lista";
				  
				  
			}catch(Exception e) {
				List<Autor> autores = autorRepositorio.findAll();
				modelo.put("autores", autores);
				List <Editorial> editoriales = editorialRepositorio.findAll();
				modelo.put("editoriales", editoriales);
				System.out.println(e.fillInStackTrace());
				System.out.println(e.getCause());
				System.out.println(e.getStackTrace());
				  modelo.put("error", "Falta algun dato");
				  return "redirect:/libro/ingreso";
			}
		}

		@GetMapping("/baja/{id}")
		public String baja(@PathVariable String id) {
			try {
			     libroServicio.darBajaLibro(id);
			     return "redirect:/libro/lista";
			
		    }catch(Exception e) {
		    	System.out.println(e.getMessage());
			     return "redirect:/libro/lista"; 
		    }
		
        }
		
		@GetMapping("/alta/{id}")
		public String alta(@PathVariable String id) {
			
			try {
				libroServicio.darAltaLibro(id);
				return "redirect:/libro/lista";
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return "redirect:/libro/lista";
			}
		}
		
		@GetMapping("/eliminar/{id}")
		public String eliminarEditorial(@PathVariable String id) {
			
			try {
				libroServicio.eliminarLibro(id);
				return "redirect:/libro/lista";
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return "redirect:/libro/lista";
			}
		}
}		