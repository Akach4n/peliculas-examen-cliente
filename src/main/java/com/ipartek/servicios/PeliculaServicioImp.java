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

import com.ipartek.pojos.Pelicula;

@Service
public class PeliculaServicioImp implements PeliculaServicio {
	
	private RestTemplate restTemplate= new RestTemplate();
	@Value("${servicio.peliculas.url}")
    private String URL;
	@Override
	public List<Pelicula> obtenerPelis(String token) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		
		HttpEntity<String> entity = new HttpEntity<>( headers);
		
		ResponseEntity<Pelicula[]> listaTemp=restTemplate.exchange(
				URL,
				HttpMethod.GET,
				entity,
				Pelicula[].class
				);
		
		return Arrays.asList(listaTemp.getBody());
		
	}

	@Override
	public Pelicula obtenerPeliculaPorId(Integer id, String jwtToken) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);
		
		HttpEntity<Pelicula> entity = new HttpEntity<> (headers);
		
		ResponseEntity<Pelicula> peliTemp=restTemplate.exchange(
				URL+id,
				HttpMethod.GET,
				entity,
				Pelicula.class
				);
		
		return peliTemp.getBody();
	
	}

	@Override
	public Pelicula guardarPelicula(Pelicula peli, String jwtToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);
		
		HttpEntity<Pelicula> entity = new HttpEntity<>(peli, headers);
		
		ResponseEntity<Pelicula> peliTemp=restTemplate.exchange(
				URL,
				HttpMethod.POST,
				entity,
				Pelicula.class
				);
		return peliTemp.getBody();
		
	}

	@Override
	public Pelicula modificarPelicula(Pelicula peli, String jwtToken) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);
		
		HttpEntity<Pelicula> entity = new HttpEntity<>(peli,headers);
		
		ResponseEntity<Pelicula> peliTemp = restTemplate.exchange(
				URL,
				HttpMethod.PUT,
				entity,
				Pelicula.class);
		
		return peliTemp.getBody();
		
		
	}

	@Override
	public void borrarPelicula(Integer id, String jwtToken) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);
		
		HttpEntity<Pelicula> entity = new HttpEntity<>(headers);
		
		restTemplate.exchange(
				URL + id,
				HttpMethod.DELETE,
				entity,
				Void.class);
		
		
		
	}

}
