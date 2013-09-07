package ar.com.grupo306.commons.exceptions.core;

/**
 * Excepci�n que indica la violaci�n de alguna regla de seguridad.
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