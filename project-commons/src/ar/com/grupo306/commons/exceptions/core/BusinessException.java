package ar.com.grupo306.commons.exceptions.core;

/**
 * Representa la existencia de una condición de negocio inválida.
 * Es utilizada para indicar que los datos, o en forma genérica el estado de la aplicación,
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