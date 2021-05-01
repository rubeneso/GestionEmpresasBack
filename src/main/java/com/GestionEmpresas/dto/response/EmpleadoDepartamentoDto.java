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
	private DepartamentoDtoMinimum departamento;
	@JMap
	private EmpleadoDtoMinimum empleado;
	@JMap
	private String cargo;
}
