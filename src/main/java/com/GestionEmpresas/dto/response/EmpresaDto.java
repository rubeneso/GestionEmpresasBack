package com.GestionEmpresas.dto.response;

import java.util.Set;

import com.googlecode.jmapper.annotations.JMap;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmpresaDto {

	@JMap
	private Long id;
	@JMap
	private String nombre;
	@JMap
	private String descripcion;
	@JMap
	private Double potenciaContratada;
	
	private Set<Long> codsDepartamentos;
}
