package com.ipartek.controlador;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ipartek.pojos.Pelicula;
import com.ipartek.servicios.GeneroServicio;
import com.ipartek.servicios.PeliculaServicio;
import jakarta.servlet.http.HttpSession;

@Controller
public class MenuControlador {

	@Autowired
	private GeneroServicio generoServ;

	@Autowired
	private PeliculaServicio peliServ;

	@GetMapping("/MenuPrincipal")
	public String menuPeliculas(HttpSession session, Model model) {
		if (session.getAttribute("s_token") == null) {
			return "redirect:/";
		}
		String token = (String) session.getAttribute("s_token");

		model.addAttribute("obj_peli", new Pelicula());
		model.addAttribute("listaPelis", peliServ.obtenerPelis(token));
		model.addAttribute("listaGeneros", generoServ.obtenerGeneros(token));

		return "principal";
	}

	@GetMapping("/MenuGeneros")
	public String menuGeneros(HttpSession session, Model model) {
		if (session.getAttribute("s_token") == null) {
			return "redirect:/";
		}
		String token = (String) session.getAttribute("s_token");

		model.addAttribute("listaGeneros", generoServ.obtenerGeneros(token));

		return "generos";
	}

}
