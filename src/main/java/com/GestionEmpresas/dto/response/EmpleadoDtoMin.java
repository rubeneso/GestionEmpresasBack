package com.GestionEmpresas.dto.response;

import com.googlecode.jmapper.annotations.JMap;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmpleadoDtoMin {

	@JMap
	private Long id;
	@JMap
	private String nombre;
}
