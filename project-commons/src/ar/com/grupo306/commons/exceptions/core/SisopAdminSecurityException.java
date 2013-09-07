package ar.com.grupo306.commons.exceptions.core;

/**
 * Excepción que indica la violación de alguna regla de seguridad.
 *
 * @author Leandro
 */
public class SisopAdminSecurityException extends RuntimeException {
	
	public SisopAdminSecurityException(String message) {
		super(message);
	}
	
	public SisopAdminSecurityException(String message, Throwable excep) {
		super(message, excep);
	}

}