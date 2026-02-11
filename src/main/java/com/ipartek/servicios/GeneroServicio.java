package com.ipartek.servicios;

import java.util.List;

import com.ipartek.pojos.Genero;

public interface GeneroServicio {

	List<Genero> obtenerGeneros(String token);
}
