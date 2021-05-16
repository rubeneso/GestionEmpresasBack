package com.GestionEmpresas.dto.response;

import java.util.List;

import com.googlecode.jmapper.annotations.JMap;

import lombok.Data;

@Data
public class EmpleadoDto {

	@JMap
	private Long id;
	@JMap
	private String nombre;
	@JMap
	private String descripcion;
	@JMap
	private EmpresaDtoMinimum empresa;
	@JMap
	private List<EmpleadoDepartamentoDtoInfoEmpleado> empleadosDepartamentos;

}
