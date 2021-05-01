package com.GestionEmpresas.excepciones;

public class WrongArgsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1097344471051095398L;
	
	public WrongArgsException() {
		super("Los argumentos encontrados son incorrrectos");
	}
}
