package com.ipartek.servicios;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ipartek.auxiliar.Auxiliar;
import com.ipartek.pojos.Rol;
import com.ipartek.pojos.Usuario;

@Service
public class UsuarioServicioImp implements UsuarioServicio{

	
	private RestTemplate restTemplate= new RestTemplate();
	@Value("${api.url}")
    private String URL_BASE;
	
	private String URL = URL_BASE + "usuarios/";
	
	
	@Override
	public String validarUsuario(String usu, String pas) {
		
		Usuario usuTemp= new Usuario(0, usu, pas, "", new Rol());
		String tokenObtenido=restTemplate.postForObject(URL+"validar/",usuTemp , String.class);
	
		return tokenObtenido;
	}
	
	
	@Override
	public Usuario bloquearUsuario(Usuario usu, String jwtToken) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);
		
		HttpEntity<Usuario> entity = new HttpEntity<>(usu, headers);
		
		ResponseEntity<Usuario> usuTemp=restTemplate.exchange(
				URL+"bloquear/",
				HttpMethod.PUT,
				entity,
				Usuario.class
				);

		return usuTemp.getBody();
//		restTemplate.put(URL+"bloquear/", usu, Usuario.class);
//
//		return new Usuario();
		
	}

	@Override
	public Boolean guardarUsuario(Usuario usu, String jwtToken) {
		String passReal=Auxiliar.hashear(usu.getPass()+usu.getSalt());
		usu.setPass(passReal);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);
		
		HttpEntity<Usuario> entity = new HttpEntity<>(usu, headers);
		
		ResponseEntity<Usuario> usuTemp=restTemplate.exchange(
				URL,
				HttpMethod.POST,
				entity,
				Usuario.class
				);
		
		if (usuTemp.getBody().getId()>0) {
			return true;	
		}else {
			return false;
		}
	}

	@Override
	public List<Usuario> obtenerTodosUsuarios(String jwtToken) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);
		
		HttpEntity<String> entity = new HttpEntity<>( headers);
		
		ResponseEntity<Usuario[]> listaTemp=restTemplate.exchange(
				URL,
				HttpMethod.GET,
				entity,
				Usuario[].class
				);
		
		return Arrays.asList(listaTemp.getBody());
		
	}


	@Override
	public Boolean borrarUsuario(Integer id, String jwtToken) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);
		
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		restTemplate.exchange(
				URL+id,
				HttpMethod.DELETE,
				entity,
				Void.class
		);
		
	
		
		//opcional
		Usuario usu= obtenerUsuarioPorId(id, jwtToken);
		if (usu!=null && usu.getId()>0 ) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Usuario obtenerUsuarioPorId(Integer id, String jwtToken) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);
		
		HttpEntity<Usuario> entity = new HttpEntity<> (headers);
		
		ResponseEntity<Usuario> usuTemp=restTemplate.exchange(
				URL+id,
				HttpMethod.GET,
				entity,
				Usuario.class
				);
		
		return usuTemp.getBody();
	
	}


	@Override
	public Usuario modificarUsuario(Usuario obj_usuario, String jwtToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);
		
		HttpEntity<Usuario> entity = new HttpEntity<> (obj_usuario,headers);
		
		ResponseEntity<Usuario> usuTemp=restTemplate.exchange(
				URL,
				HttpMethod.PUT,
				entity,
				Usuario.class
				);
		
		return usuTemp.getBody();
		
//		restTemplate.put(URL,obj_usuario, Usuario.class);
		
//		return obtenerUsuarioPorId(obj_usuario.getId(), jwtToken);
	}
	
	

}
