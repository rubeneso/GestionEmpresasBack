package com.GestionEmpresas.dto.response;

import com.googlecode.jmapper.annotations.JMap;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmpleadoDepartamentoDtoInfoEmpleado {

	@JMap
	private DepartamentoDtoMin departamento;
	@JMap
	private String cargo;
}
