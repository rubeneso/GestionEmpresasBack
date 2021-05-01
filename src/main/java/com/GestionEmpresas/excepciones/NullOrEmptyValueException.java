package com.GestionEmpresas.excepciones;

public class NullOrEmptyValueException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -921531823395791455L;
	
	public NullOrEmptyValueException(String propiedad) {
		super("El valor de la propiedad " + propiedad + " no puede ser nulo ni estar vacio");
	}
}
