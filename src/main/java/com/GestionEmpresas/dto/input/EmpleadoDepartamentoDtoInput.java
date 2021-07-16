package com.GestionEmpresas.dto.input;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class EmpleadoDepartamentoDtoInput {
	private Long id;
	private String cargo;
	private Long codEmpleado;
	private Long codDepartamento;
}
