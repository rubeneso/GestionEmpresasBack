package com.GestionEmpresas.dto.input;

import com.GestionEmpresas.dto.response.EmpresaDtoMinimumId;

import lombok.Data;

@Data
public class EmpleadoDtoInput {
	private Long id;
	private String nombre;
	private String descripcion;
	private EmpresaDtoMinimumId empresa;
	
	public EmpleadoDtoInput() {
		super();
	}

}
