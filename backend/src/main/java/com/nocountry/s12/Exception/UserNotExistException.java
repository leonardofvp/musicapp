package com.nocountry.s12.Exception;

public class UserNotExistException extends RuntimeException {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotExistException(){
        super("No existe un usuario con ese id/email");
    }
}
