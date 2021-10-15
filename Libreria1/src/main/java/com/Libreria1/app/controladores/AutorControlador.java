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
import com.Libreria1.app.servicios.AutorServicio;
import com.Libreria1.app.servicios.LibroServicio;

@Controller
@RequestMapping("/autor")
public class AutorControlador {
	
        @Autowired	
        private AutorServicio autorServicio;
        
        @Autowired
        private LibroServicio libroServicio;
	
		@GetMapping("/lista")
		public String lista(ModelMap modelo){
			
			List<Autor> todosAutores = autorServicio.buscarAutores();

			modelo.addAttribute("autores", todosAutores);

			return "lista-autor";
		}
		
		@GetMapping("/modificar/{id}")
		public String modificar(ModelMap modelo, @PathVariable String id) throws Exception {
			try {
			     Autor autor = autorServicio.obtenerAutor(id);
			     modelo.addAttribute("autor",autor);
			     return "modificar-autor";
			}catch(Exception e) {
				System.out.println(e.getMessage());
				modelo.put("error", "falta algun dato");
				return "modificar-autor";
			}
		}
		
		
		@PostMapping("/modificar/{id}")
		public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam String nombre) throws Exception {
			try {
			     autorServicio.modificarAutor(id, nombre);
			     modelo.put("exito", "Ingreso exitoso!");
			     return "redirect:/autor/lista";
			}catch(Exception e) {
				 System.out.println(e.getMessage());
				 e.printStackTrace();
				 System.out.println("error10");
				 modelo.addAttribute("error", e.getMessage());
				 //throw new Exception("Falta algun dato o no puede ingresar un Autor con igual nombre a otro");
			}
			return "redirect:/autor/lista";
		}

		@GetMapping("/ingreso")
		public String ingreso() {
			return "ingreso-autor";
		}

		@PostMapping("/ingreso")
		public String ingresarAutor(ModelMap modelo, @RequestParam String nombre) throws Exception  {
			try {
			      autorServicio.ingresarAutor(nombre);
			      modelo.put("exito", "Ingreso exitoso!");
			      return "redirect:/autor/lista";
			}catch(Exception e) {
                  System.out.println(e.getMessage());
                  System.out.println(e.getStackTrace());
				  modelo.put("error", "Falta algun dato o no puede ingresar un Autor con igual nombre a otro");
				  return "ingreso-autor";
			}
		}

		@GetMapping("/baja/{id}")
		public String baja(@PathVariable String id) {
			try {
			     autorServicio.darBajaAutor(id);
			     return "redirect:/autor/lista";
			
		    }catch(Exception e) {
		    	System.out.println(e.getMessage());
			    return "redirect:/autor/lista"; 
		    }
		
        }
		
		@GetMapping("/alta/{id}")
		public String alta(@PathVariable String id) {
			
			try {
				autorServicio.darAltaAutor(id);
				return "redirect:/autor/lista";
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return "redirect:/autor/lista";
			}
		}
		
		@GetMapping("/eliminar/{id}")
		public String eliminarAutor(@PathVariable String id) throws Exception {
			
			try {
				autorServicio.eliminarAutor(id);
				return "redirect:/autor/lista";
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				return "redirect:/autor/lista";
			}
		}
		
}		
