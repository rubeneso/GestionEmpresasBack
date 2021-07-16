package com.GestionEmpresas.dto.response;

import com.googlecode.jmapper.annotations.JMap;

import lombok.Data;

@Data
public class DepartamentoCargoDto {
	private Long codDepartamento;
	private String nombreDepartamento;
	@JMap
	private String cargo;
}
