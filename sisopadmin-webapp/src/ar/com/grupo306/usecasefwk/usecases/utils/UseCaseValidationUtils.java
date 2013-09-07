package ar.com.grupo306.usecasefwk.usecases.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;

/**
 * Utilidades para la validaci�n de campos de un UseCaseModel.
 *
 * @author Leandro
 */
public class UseCaseValidationUtils {
	private UseCaseContext useCaseContext;
	
	/**
	 * Ante el PRIMER error informado se arroja una BusinessException.
	 */
	public UseCaseValidationUtils() {
		super();
	}
	
	/**
	 * Los errores informados ser�n registrados en el UseCaseContext.
	 * 
	 * @param useCaseContext
	 */
	public UseCaseValidationUtils(UseCaseContext useCaseContext) {
		this.useCaseContext = useCaseContext;
	}
	
	/**
	 * Indica la existencia de errores durante la validaci�n.
	 */
	public boolean hasErrors() {
		return this.useCaseContext != null ? this.useCaseContext.hasErrors() : false;
	}
	

	
	// **********************
	// METODOS DE VALIDACION.
	// **********************
	
	/**
	 * Eval�a que una cadena no est� vac�a.
	 * 
	 * @param cadena
	 * @param errorMessage
	 */
	public void assertNotEmpty(String cadena, String errorMessage) {
		this.assertTrue(cadena != null && !cadena.trim().equals(""), errorMessage);
	}
	
	/**
	 * Eval�a que un value sea distinto de null.
	 * 
	 * @param value
	 * @param errorMessage
	 */
	public void assertNotNull(Object value, String errorMessage) {
		this.assertTrue(value != null, errorMessage);
	}
	
	/**
	 * Eval�a que un value sea  null.
	 * 
	 * @param value
	 * @param errorMessage
	 */
	public void assertNull(Object value, String errorMessage) {
		this.assertTrue(value == null, errorMessage);
	}	
	
	/**
	 * Si la condici�n es false, registra el errorMessage.
	 * 
	 * @param condition
	 * @param errorMessage
	 */
	public void assertTrue(boolean condition, String errorMessage) {
		if(!condition) {
			this.registerErrorOrThrow(errorMessage);
		}
	}
	
	/**
	 * Si la condici�n es true, registra el errorMessage.
	 * 
	 * @param condition
	 * @param errorMessage
	 */
	public void assertFalse(boolean condition, String errorMessage) {
		if(condition) {
			this.registerErrorOrThrow(errorMessage);
		}		
	}
	
	/**
	 * Chquea que represente un mail v�lido.
	 * 
	 * @param mail
	 * @param errorMessage
	 */
	public void assertValidMail(String mail, String errorMessage) {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
	    Matcher m = p.matcher(mail);
	    this.assertTrue(m.matches(), errorMessage);
	}
	
	/**
	 * Verifica que el string represente un entero v�lido.
	 * 
	 * @param intString
	 * @param errorMessage
	 */
	public void assertValidIntString(String intString, String errorMessage) {
		try{
			Integer.parseInt(intString);
		}
		catch(NumberFormatException ex){
			this.registerErrorOrThrow(errorMessage);
		}
	}
	
	
	
	private void registerErrorOrThrow(String errorMessage) {
		if(this.useCaseContext != null) {
			this.useCaseContext.addErrorMessage(errorMessage);
		}
		else {
			throw ExceptionFactory.createBusinessException(errorMessage);
		}
	}

	public UseCaseContext getUseCaseContext() {
		return useCaseContext;
	}
	
}