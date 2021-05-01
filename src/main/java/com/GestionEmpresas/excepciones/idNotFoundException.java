package com.GestionEmpresas.excepciones;

public class idNotFoundException extends RuntimeException  {

	private static final long serialVersionUID = 4682794690751149105L;

	public idNotFoundException(Long id) {
		super("No se ha encontrado el id: "+ id);
	}
}
