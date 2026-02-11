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

import com.ipartek.pojos.Genero;

@Service
public class GeneroServicioImp implements GeneroServicio{

	private RestTemplate restTemplate = new RestTemplate();
	@Value("${api.url}")
    private String URL;
	
	
	@Override
	public List<Genero> obtenerGeneros(String token) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		
		HttpEntity<Genero> entity= new HttpEntity<>(headers);
		
		ResponseEntity<Genero[]> geneTemp=restTemplate.exchange(
				URL,
				HttpMethod.GET,
				entity,
				Genero[].class);
		
		return Arrays.asList(geneTemp.getBody());
		
	}
		
		

}
