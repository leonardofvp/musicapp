package com.nocountry.s12.Exception;

public class UnauthorizedAccessException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnauthorizedAccessException() {
        super("Acceso no autorizado: El usuario no tiene permiso para realizar esta acción.");
    }
}
