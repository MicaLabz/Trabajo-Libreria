package com.Libreria1.app.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PrestamoPortalControlador {
	
	@GetMapping("/prestamo")
	public String portal(ModelMap modelo){
		
		return "prestamo-portal";
	}

}
