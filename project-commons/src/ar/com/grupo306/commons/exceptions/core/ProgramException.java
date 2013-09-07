package ar.com.grupo306.commons.exceptions.core;

/**
 * Representa una Falla producida por la ejecución defectuosa de la aplicación.
 *
 * @author Leandro
 */
public class ProgramException extends RuntimeException {
	
	public ProgramException(String message) {
		super(message);
	}
	
	public ProgramException(String message, Throwable excep) {
		super(message, excep);
	}

}