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
import com.Libreria1.app.entidades.Editorial;
import com.Libreria1.app.servicios.EditorialServicio;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
		
	        @Autowired	
	        private EditorialServicio editorialServicio;
		
			@GetMapping("/lista")
			public String lista(ModelMap modelo){
				
				List<Editorial> todosEditoriales = editorialServicio.buscarEditoriales();

				modelo.addAttribute("editoriales", todosEditoriales);

				return "lista-editorial";
			}
			
			@GetMapping("/modificar/{id}")
			public String modificar(ModelMap modelo, @PathVariable String id) {
				try {
				     Editorial editorial = editorialServicio.obtenerEditorial(id);
				     modelo.addAttribute("editorial",editorial);
				     return "modificar-editorial";
				}catch(Exception e) {
					modelo.put("error", "falta algun dato");
					return "modificar-editorial";
				}
			}
			
			
			@PostMapping("/modificar/{id}")
			public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam String nombre) {
				try {
				     editorialServicio.modificarEditorial(id, nombre);
				     return "redirect:/editorial/lista";
				}catch(Exception e) {
					 modelo.put("error", "Falta algun dato");
					 return "modificar-editorial";
				}
			}

			@GetMapping("/ingreso")
			public String ingreso() {
				return "ingreso-editorial";
			}

			@PostMapping("/ingreso")
			public String ingresarEditorial(ModelMap modelo, @RequestParam String nombre1) {
				try {
				      editorialServicio.ingresarEditorial(nombre1);
				      modelo.put("exito", "Ingreso exitoso!");
				      return "redirect:/editorial/lista";
				}catch(Exception e) {
					  modelo.put("error", "Falta algun dato");
					  return "ingreso-editorial";
				}
			}

			@GetMapping("/baja/{id}")
			public String baja(@PathVariable String id) {
				try {
				     editorialServicio.darBajaEditorial(id);
				     return "redirect:/editorial/lista";
				
			    }catch(Exception e) {
				     return "redirect:/"; 
			    }
			
	        }
	}		
