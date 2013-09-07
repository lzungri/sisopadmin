package ar.com.grupo306.commons.exceptions.core;

/**
 * Representa una Falla producida por la ejecuci�n defectuosa de la aplicaci�n.
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