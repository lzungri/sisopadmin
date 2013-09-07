package ar.com.grupo306.commons.exceptions.stack;

/**
 * Ante la extracción de un elemento de la pila indica que dicho elemento
 * no se encuentra.
 *
 * @author Leandro
 */
public class NoSuchElementInStackException extends RuntimeException {
	
	public NoSuchElementInStackException(String message) {
		super(message);
	}
	
	public NoSuchElementInStackException(String message, Throwable excep) {
		super();
	}

}