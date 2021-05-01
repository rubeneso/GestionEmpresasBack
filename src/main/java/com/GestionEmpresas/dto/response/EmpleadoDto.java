package com.GestionEmpresas.dto.response;

import java.util.List;

import com.googlecode.jmapper.annotations.JMap;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
