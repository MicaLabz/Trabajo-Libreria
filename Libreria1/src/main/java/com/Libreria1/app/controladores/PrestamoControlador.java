package com.Libreria1.app.controladores;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.Libreria1.app.entidades.Cliente;
import com.Libreria1.app.entidades.Libro;
import com.Libreria1.app.entidades.Prestamo;
import com.Libreria1.app.repositorios.ClienteRepositorio;
import com.Libreria1.app.repositorios.LibroRepositorio;
import com.Libreria1.app.servicios.ClienteServicio;
import com.Libreria1.app.servicios.LibroServicio;
import com.Libreria1.app.servicios.PrestamoServicio;

@Controller
@RequestMapping("/prestamo")
public class PrestamoControlador {
	
	 @Autowired	
     private PrestamoServicio prestamoServicio;
     
     @Autowired 
     private LibroServicio libroServicio;
     
     @Autowired
     private ClienteServicio clienteServicio;
     
     @Autowired
     private LibroRepositorio libroRepositorio;
     
     @Autowired
     private ClienteRepositorio clienteRepositorio;
	
		@GetMapping("/lista")
		public String lista(ModelMap modelo){
			
			List<Prestamo> todosPrestamos = prestamoServicio.buscarPrestamos();

			modelo.addAttribute("prestamos", todosPrestamos);

			return "lista-prestamo";
		}
		
		@GetMapping("/modificar/{id}")
		public String modificar(ModelMap modelo, @PathVariable String id) {
			try {
			     Prestamo prestamo = prestamoServicio.obtenerPrestamo(id);
			     modelo.addAttribute("prestamo", prestamo);
			     List<Libro> libros = libroRepositorio.findAll();
				 modelo.put("libros", libros);
				 List <Cliente> clientes = clienteRepositorio.findAll();
				 modelo.put("clientes", clientes);
			     return "modificar-prestamo";
			}catch(Exception e) {
				modelo.put("error", "falta algun dato");
				return "modificar-prestamo";
			}
		}
		
		
		@PostMapping("/modificar/{id}")
		public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam String fechaPrestamo, @RequestParam String fechaDevolucion,/*dudas*/ @RequestParam String tituloLibro, @RequestParam Long documentoCliente) {
			try {
				  Libro libro = libroServicio.obtenerLibroPorTitulo(tituloLibro);
				  Cliente cliente = clienteServicio.obtenerClientePorDocumento(documentoCliente);
				  String idLibro = libro.getId();
				  String idCliente = cliente.getId();
				  SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
				  Date fechap, fechad;
				  fechap = formato.parse(fechaPrestamo);
				  fechad = formato.parse(fechaDevolucion);
				  prestamoServicio.modificarPrestamo(id, fechap, fechad, idLibro, idCliente);
				  modelo.put("exito", "Ingreso exitoso!");
				  return "redirect:/prestamo/lista";
			}catch(Exception e) {
				  System.out.println(e.getMessage());
				  System.out.println(e.getStackTrace());
				  modelo.put("error", "Falta algun dato");
				  return "modificar-prestamo";
			}
		}

		@GetMapping("/ingreso")
		public String ingreso(ModelMap modelo) {
			List<Libro> libros = libroRepositorio.findAll();
			modelo.put("libros", libros);
			List <Cliente> clientes = clienteRepositorio.findAll();
			modelo.put("clientes", clientes);
			return "ingreso-prestamo";
		}

		@PostMapping("/ingreso")
		public String ingresarPrestamo(ModelMap modelo, @RequestParam String fechaPrestamo, @RequestParam String fechaDevolucion, @RequestParam String tituloLibro, @RequestParam Long documentoCliente) throws Exception {
			try {
				  Libro libro = libroServicio.obtenerLibroPorTitulo(tituloLibro);
				  Cliente cliente = clienteServicio.obtenerClientePorDocumento(documentoCliente);
				  String idLibro = libro.getId();
				  String idCliente = cliente.getId();
				  SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
				  Date fechap, fechad;
				  fechap = formato.parse(fechaPrestamo);
				  fechad = formato.parse(fechaDevolucion);
				  prestamoServicio.ingresarPrestamo(fechap, fechad, idLibro , idCliente);
			      modelo.put("exito", "Ingreso exitoso!");
			      return "redirect:/prestamo/lista";
				  
				  
			}catch(Exception e) {
				List<Libro> libros = libroRepositorio.findAll();
				modelo.put("libros", libros);
				List <Cliente> clientes = clienteRepositorio.findAll();
				modelo.put("clientes", clientes);
				System.out.println(e.fillInStackTrace());
				System.out.println(e.getCause());
				System.out.println(e.getStackTrace());
				  modelo.put("error", "Falta algun dato");
				  return "redirect:/prestamo/ingreso";
			}
		}

		@GetMapping("/baja/{id}")
		public String baja(@PathVariable String id) {
			try {
			     prestamoServicio.darBajaPrestamo(id);
			     return "redirect:/prestamo/lista";
			
		    }catch(Exception e) {
		    	System.out.println(e.getMessage());
			     return "redirect:/prestamo/lista"; 
		    }
		
     }
		
		@GetMapping("/alta/{id}")
		public String alta(@PathVariable String id) {
			
			try {
				prestamoServicio.darAltaPrestamo(id);
				return "redirect:/prestamo/lista";
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return "redirect:/prestamo/lista";
			}
		}
		
		@GetMapping("/eliminar/{id}")
		public String eliminar(@PathVariable String id) {
			
			try {
				prestamoServicio.eliminarPrestamo(id);
				return "redirect:/prestamo/lista";
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return "redirect:/prestamo/lista";
			}
		}

}
