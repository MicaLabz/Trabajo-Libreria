package com.Libreria1.app.controladores;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Libreria1.app.entidades.Autor;

@Controller
@RequestMapping("/")
public class AutorPortalControlador {
	
	@GetMapping("/autor")
	public String portal(ModelMap modelo){
		
		return "autor-portal";
	}
	

}
