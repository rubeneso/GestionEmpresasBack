package com.GestionEmpresas.dto.input;

import java.util.Set;

import com.GestionEmpresas.dto.response.EmpresaDto;

import lombok.Data;

@Data
public class DepartamentoDtoInput {
	private Long id;
	private String nombre;
	private String descripcion;
	private Set<EmpresaDto> empresas;
	
	DepartamentoDtoInput() {}
}
