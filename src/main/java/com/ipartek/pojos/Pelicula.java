package com.ipartek.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pelicula {

	private Integer id;
	private String titulo;
	private String duracion;
	private Genero genero;
	
}