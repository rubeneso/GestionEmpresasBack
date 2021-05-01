package com.GestionEmpresas.dto.input;

import com.GestionEmpresas.dto.response.DepartamentoDtoMinimum;
import com.GestionEmpresas.dto.response.EmpleadoDtoMinimum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpleadoDepartamentoDtoInput {
	private Long id;
	private String cargo;
	private EmpleadoDtoMinimum empleado;
	private DepartamentoDtoMinimum departamento;
}
