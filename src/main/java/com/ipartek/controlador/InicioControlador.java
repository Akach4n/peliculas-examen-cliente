package com.ipartek.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ipartek.pojos.Usuario;

import jakarta.servlet.http.HttpSession;




@Controller
public class InicioControlador {
	
	@GetMapping("/")
	public String cargarInicio(Model model,HttpSession session) {
		
		if (session.getAttribute("s_token") != null) {
			return "redirect:/MenuPrincipal";
		}
		
		model.addAttribute("obj_usuario", new Usuario() );
		return "home";
	}
	

}
