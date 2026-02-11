package com.ipartek.controlador;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ipartek.pojos.Pelicula;
import com.ipartek.servicios.GeneroServicio;
import com.ipartek.servicios.PeliculaServicio;

import jakarta.servlet.http.HttpSession;



@Controller
public class PeliculaControlador {

	@Autowired
	private PeliculaServicio peliServ;
	
	@Autowired
	private GeneroServicio generoServ;

	
	@PostMapping("/GuardarPelicula")
	public String insertaPeli(@ModelAttribute Pelicula peli,HttpSession session,RedirectAttributes flash) {
		
		if (session.getAttribute("s_token") == null){
			return "redirect:/";
		}
		String token = (String) session.getAttribute("s_token");
		peliServ.guardarPelicula(peli, token);
		flash.addFlashAttribute("s_mensaje","Pelicula \""+ peli.getTitulo()+"\" insertada correctamente");
		return "redirect:/MenuPrincipal#tabla";
	}
	
	
	@GetMapping("/BorrarPelicula")
	public String borrarPeli(@RequestParam Integer id,HttpSession session,RedirectAttributes flash) {
			
		if (id == null || !(session.getAttribute("s_rol").equals("ADMIN"))) {
			return "redirect:/MenuPrincipal"; 
		}
		
		if (session.getAttribute("s_token") == null){
			return "redirect:/";
		}
		
		String token = (String) session.getAttribute("s_token");
		
		peliServ.borrarPelicula(id, token);
		flash.addFlashAttribute("s_mensaje", "Pelicula eliminada correctamente");
			return "redirect:/MenuPrincipal#tabla";			
		
	}
	
	@GetMapping("/FrmModificarPelicula")
	public String frmPeli(@RequestParam Integer id,HttpSession session,Model model) {
		
		if (id == null || !(session.getAttribute("s_rol").equals("ADMIN"))) {
			return "redirect:/MenuPrincipal"; 
		}
		
		if (session.getAttribute("s_token") == null){
			return "redirect:/";
		}
		String token = (String) session.getAttribute("s_token");

			model.addAttribute("obj_peli",peliServ.obtenerPeliculaPorId(id, token));
			model.addAttribute("listaGeneros",generoServ.obtenerGeneros( token));
			return "frm_peli";			
		
	}
	
	@PostMapping("/ModificarPelicula")
	public String modificarPeli(@ModelAttribute Pelicula peli,HttpSession session, RedirectAttributes flash) {
		
		if (session.getAttribute("s_token") == null || !(session.getAttribute("s_rol").equals("ADMIN"))){
			return "redirect:/";
		}
		String token = (String) session.getAttribute("s_token");
		peliServ.modificarPelicula(peli, token);
		flash.addFlashAttribute("s_mensaje","Pelicula \""+ peli.getTitulo()+"\" modificada correctamente");
		return "redirect:/MenuPrincipal#tabla";
	}
	
	
}
