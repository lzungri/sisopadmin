package ar.com.grupo306.commons.exceptions.core;

/**
 * Representa la existencia de una condici�n de negocio inv�lida.
 * Es utilizada para indicar que los datos, o en forma gen�rica el estado de la aplicaci�n,
 * no cumple con los esperados por el negocio.
 *
 * @author Leandro
 */
public class BusinessException extends RuntimeException {
	
	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(String message, Throwable excep) {
		super(message, excep);
	}

}