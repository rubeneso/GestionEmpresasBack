package com.GestionEmpresas.dto.input;

import lombok.Data;

@Data
public class EmpleadoDtoInput {
	private Long id;
	private String nombre;
	private String descripcion;
	private Long codEmpresa;

}
