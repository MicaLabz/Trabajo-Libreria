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
import com.Libreria1.app.entidades.Cliente;
import com.Libreria1.app.servicios.ClienteServicio;

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {
	
	@Autowired
	private ClienteServicio clienteServicio;
	
	@GetMapping("/lista")
	public String lista(ModelMap modelo){
		
		List<Cliente> todosClientes = clienteServicio.buscarClientes();

		modelo.addAttribute("clientes", todosClientes);

		return "lista-cliente";
	}
	
	@GetMapping("/modificar/{id}")
	public String modificar(ModelMap modelo, @PathVariable String id) throws Exception {
		try {
		     Cliente cliente = clienteServicio.obtenerCliente(id);
		     modelo.addAttribute("cliente",cliente);
		     return "modificar-cliente";
		}catch(Exception e) {
			System.out.println(e.getMessage());
			modelo.put("error", "falta algun dato");
			return "modificar-cliente";
		}
	}
	
	
	@PostMapping("/modificar/{id}")
	public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam Long documento, String nombre, String apellido, String telefono) throws Exception {
		try {
		     clienteServicio.modificarCliente(id, documento,nombre, apellido, telefono);
		     modelo.put("exito", "Ingreso exitoso!");
		     return "redirect:/cliente/lista";
		}catch(Exception e) {
			 System.out.println(e.getMessage());
			 e.printStackTrace();
			 modelo.addAttribute("error", e.getMessage());
		}
		return "redirect:/cliente/lista";
	}

	@GetMapping("/ingreso")
	public String ingreso() {
		return "ingreso-cliente";
	}

	@PostMapping("/ingreso")
	public String ingresarCliente(ModelMap modelo, @RequestParam Long documento, String nombre, String apellido, String telefono) throws Exception  {
		try {
		      clienteServicio.ingresarCliente(documento,nombre, apellido, telefono);
		      modelo.put("exito", "Ingreso exitoso!");
		      return "redirect:/cliente/lista";
		}catch(Exception e) {
              System.out.println(e.getMessage());
              System.out.println(e.getStackTrace());
			  modelo.put("error", "Falta algun dato o no puede ingresar un Cliente con igual documento a otro");
			  return "ingreso-cliente";
		}
	}

	@GetMapping("/baja/{id}")
	public String baja(@PathVariable String id) {
		try {
		     clienteServicio.darBajaCliente(id);
		     return "redirect:/cliente/lista";
		
	    }catch(Exception e) {
	    	System.out.println(e.getMessage());
		    return "redirect:/cliente/lista"; 
	    }
	
    }
	
	@GetMapping("/alta/{id}")
	public String alta(@PathVariable String id) {
		
		try {
			clienteServicio.darAltaCliente(id);
			return "redirect:/cliente/lista";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/cliente/lista";
		}
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminarCliente(@PathVariable String id) throws Exception {
		
		try {
			clienteServicio.eliminarCliente(id);
			return "redirect:/cliente/lista";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "redirect:/cliente/lista";
		}
	}

}
