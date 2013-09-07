package ar.com.grupo306.commons.factorys;

import ar.com.grupo306.commons.exceptions.core.BusinessException;
import ar.com.grupo306.commons.exceptions.core.ProgramException;
import ar.com.grupo306.commons.exceptions.core.SisopAdminSecurityException;

/**
 * Establece una interfaz única para la creación de excepciones.
 * 
 * TODO Mejorar y completar.
 *
 * @author Leandro
 */
public class ExceptionFactory {
	
	public static BusinessException createBusinessException(String message, Throwable exceptionCause) {
		return new BusinessException(message, exceptionCause);
	}
	
	public static BusinessException createBusinessException(String message) {
		return new BusinessException(message);
	}	
	
	public static ProgramException createProgramException(String message, Throwable exceptionCause) {
		return new ProgramException(message, exceptionCause);
	}
	
	public static ProgramException createProgramException(String message) {
		return new ProgramException(message);
	}
	
	public static SisopAdminSecurityException createSisopAdminSecurityException(String message, Throwable exceptionCause) {
		return new SisopAdminSecurityException(message, exceptionCause);
	}

	public static SisopAdminSecurityException createSisopAdminSecurityException(String message) {
		return new SisopAdminSecurityException(message);
	}
}