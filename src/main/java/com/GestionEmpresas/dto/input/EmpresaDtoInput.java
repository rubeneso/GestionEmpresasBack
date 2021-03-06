package com.GestionEmpresas.dto.input;

import java.util.Set;

import lombok.Data;

@Data
public class EmpresaDtoInput {
	private Long id;
	private String nombre;
	private String descripcion;
	private Double potenciaContratada;
	private Set<Long> codsDepartamentos;

}
