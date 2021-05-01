package com.GestionEmpresas.dto.response;

import java.util.Set;

import com.googlecode.jmapper.annotations.JMap;

import lombok.Data;

@Data
public class DepartamentoDto{
	
	@JMap
	private Long id;
	@JMap
	private String nombre;
	@JMap
	private String descripcion;
	@JMap
	private Set<EmpresaDtoMinimumNombre> empresas;
}
