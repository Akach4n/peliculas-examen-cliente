package com.ipartek.controlador;

import java.io.FileWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ipartek.componentes.JwtUtil;
import com.ipartek.pojos.Usuario;
import com.ipartek.servicios.UsuarioServicio;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class LoginControlador {
	
	@Autowired
	private UsuarioServicio usuarioServ;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@SuppressWarnings("unchecked")
	@PostMapping("/Login")
	public String validarLogin(@ModelAttribute Usuario obj_usuario, 
			HttpServletRequest request, HttpSession session, RedirectAttributes flash) {
	
		String token = usuarioServ.validarUsuario(obj_usuario.getUser(), obj_usuario.getPass());
		System.out.println(token);
		if (token==null) {
			token="";
		}

		if (!token.equals("")) {//si el token tiene datos
			
			session.setAttribute("s_token", token);
			Claims cl = jwtUtil.extractClaims((String) session.getAttribute("s_token"));
    		String rol = (String) cl.get("rol");
    		session.setAttribute("s_rol", rol);
			
			return "redirect:/MenuPrincipal";
		} else {
	            try {
					FileWriter fw = new FileWriter("c:\\alain\\logAccesosDenegados.txt", true);
					
					String hora=LocalDateTime.now().toString();
					String nombre=obj_usuario.getUser();
					String ipAcceso=request.getRemoteAddr();
			
					fw.write(hora+";"+nombre+";"+ipAcceso+"\n");
					
					fw.close();
				} catch (IOException e) {
					
					System.out.println("Hubo fallos al guardar el fichero-");
				}
	           
	         
	            HashMap<String, Integer> listaIntentosAcceso;
	            if (session.getAttribute("s_listaIntentosAcceso")!=null) {
	            	listaIntentosAcceso=(HashMap<String, Integer>) session.getAttribute("s_listaIntentosAcceso");
	            	
				}else {
					listaIntentosAcceso = new HashMap<String, Integer>();
				}

	            if(listaIntentosAcceso.containsKey(obj_usuario.getUser())) {
	            	int intentos= listaIntentosAcceso.get(obj_usuario.getUser());
	            	intentos++;
	            	listaIntentosAcceso.put(obj_usuario.getUser(),intentos);
	            	
	            	if (intentos==3) {
	            		String tokenBloquear= jwtUtil.generateToken("", "SYSTEM");
	            		
	            		usuarioServ.bloquearUsuario(obj_usuario, tokenBloquear);
					}
	            	
	            }else {
	            	listaIntentosAcceso.put(obj_usuario.getUser(),1);
	            }
	            	            
	            session.setAttribute("s_listaIntentosAcceso", listaIntentosAcceso);
	            System.out.println(listaIntentosAcceso);
	            
	            
	            flash.addFlashAttribute("s_mensaje", "Inicio de sesión incorrecto");
			return "redirect:/";	
		}		
	}
	
	@GetMapping("/Logout")
	public String cerrarSesion(HttpSession session) {
		session.invalidate();
		return "redirect:/";	
	}
	
}
