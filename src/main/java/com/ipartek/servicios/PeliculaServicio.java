package com.ipartek.servicios;

import java.util.List;

import com.ipartek.pojos.Pelicula;

public interface PeliculaServicio {

	List<Pelicula> obtenerPelis(String token);
	Pelicula obtenerPeliculaPorId(Integer id, String jwtToken);
	Pelicula guardarPelicula(Pelicula peli, String jwtToken);
	Pelicula modificarPelicula(Pelicula peli, String jwtToken);
	void borrarPelicula(Integer id, String jwtToken);
}
