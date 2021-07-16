package com.GestionEmpresas.dto.response;

import com.googlecode.jmapper.annotations.JMap;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmpleadoDepartamentoDto {

	@JMap
	private Long id;
	@JMap
	private DepartamentoDtoMin departamento;
	@JMap
	private EmpleadoDtoMin empleado;
	@JMap
	private String cargo;
}
