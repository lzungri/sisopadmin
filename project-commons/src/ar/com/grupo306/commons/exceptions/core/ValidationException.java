package ar.com.grupo306.commons.exceptions.core;

/**
 * 
 * @author Rafa
 *
 */

public class ValidationException extends RuntimeException {
	
	public ValidationException(String message) {
		super(message);
	}
	
	public ValidationException(String message, Throwable excep) {
		super(message, excep);
	}

}