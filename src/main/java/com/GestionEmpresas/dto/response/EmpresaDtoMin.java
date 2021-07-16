package com.GestionEmpresas.dto.response;

import com.googlecode.jmapper.annotations.JMap;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmpresaDtoMin {

	@JMap
	private Long id;
	@JMap
	private String nombre;
}
