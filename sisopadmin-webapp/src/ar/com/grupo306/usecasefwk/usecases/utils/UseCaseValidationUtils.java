package ar.com.grupo306.usecasefwk.usecases.utils;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts.upload.FormFile;

import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.sisopadmin.web.constants.SisopAdminWebConstants;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;

/**
 * Utilidades para la validación de campos de un UseCaseModel.
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
	 * Los errores informados serán registrados en el UseCaseContext.
	 * 
	 * @param useCaseContext
	 */
	public UseCaseValidationUtils(UseCaseContext useCaseContext) {
		this.useCaseContext = useCaseContext;
	}
	
	/**
	 * Indica la existencia de errores durante la validación.
	 */
	public boolean hasErrors() {
		return this.useCaseContext != null ? this.useCaseContext.hasErrors() : false;
	}
	

	
	// **********************
	// METODOS DE VALIDACION.
	// **********************
	
	/**
	 * Evalúa que una cadena no esté vacía.
	 * 
	 * @param cadena
	 * @param errorMessage
	 */
	public void assertNotEmpty(String cadena, String errorMessage) {
		this.assertTrue(cadena != null && !cadena.trim().equals(""), errorMessage);
	}
	
	
	/**
	 * Evalúa que un value sea distinto de null.
	 * 
	 * @param value
	 * @param errorMessage
	 */
	public void assertNotNull(Object value, String errorMessage) {
		this.assertTrue(value != null, errorMessage);
	}
	
	public void assertNotNegativeNumber(Integer value, String errorMessage){
		this.assertTrue(value == null || value >= 0 , errorMessage);
	}
	
	public void assertNotNegativeNumber(Long value, String errorMessage){
		this.assertTrue(value == null || value >= 0 , errorMessage);
	}
	
	/**
	 * Evalúa que un value sea  null.
	 * 
	 * @param value
	 * @param errorMessage
	 */
	public void assertNull(Object value, String errorMessage) {
		this.assertTrue(value == null, errorMessage);
	}	
	
	/**
	 * Si la condición es false, registra el errorMessage.
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
	 * Si la condición es true, registra el errorMessage.
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
	 * Valida que el contenido sea inferior a un valor fijo.
	 * 
	 * @param content
	 * @param errorMessage
	 */
	public void assertValidTextArea(String content, String errorMessage) {
		assertTrue(content == null || content.length() <= SisopAdminWebConstants.TEXTAREA_MAXLENGTH, errorMessage);
	}
	
	/**
	 * Chquea que represente un mail válido.
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
	 * Cehquea que represente un legajo válido.
	 * 
	 * @param mail
	 * @param errorMessage
	 */
	public void assertValidLegajo(String legajo, String errorMessage) {
		Pattern pLegajo = Pattern.compile("\\d{3}\\.\\d{3}-\\d");
	    Matcher mLegajo = pLegajo.matcher(legajo);	   
	    this.assertTrue(mLegajo.matches(), errorMessage);
	}			
	
	/**
	 * Verifica que el string represente un entero válido.
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

	/**
	 * Verifica que el string represente un porcentaje válido.
	 * 
	 * @param intString
	 * @param errorMessage
	 */
	public void assertValidPorcentualString(String intString, String errorMessage) {
		try{
			Integer value = Integer.parseInt(intString);
			if(value < 0 || value >100) {
				registerErrorOrThrow(errorMessage);
			}
		}
		catch(NumberFormatException ex){
			this.registerErrorOrThrow(errorMessage);
		}
	}
	
	/**
	 * Verifica que el string represente un entero válido o sea vacío.
	 * 
	 * @param intString
	 * @param errorMessage
	 */
	public void assertEmptyOrValidIntString(String intString, String errorMessage) {
		if(intString != null && !intString.trim().equals("")) {
			assertValidIntString(intString, errorMessage);
		}
	}
	
	/**
	 * Verifica que el string represente un porcentaje válido o sea vacío.
	 * 
	 * @param porcentual
	 * @param errorMessage
	 */
	public void assertValidNulleablePorcentual(String porcentual, String errorMessage) {
		if(porcentual != null && !porcentual.trim().equals("")) {
			assertValidPorcentualString(porcentual, errorMessage);
		}
	}
	
	/**
	 * Verifica que la fecha desde sea mayor que la fecha hasta.
	 * 
	 * @param fechaDesde
	 * @param fechaHasta
	 * @param errorMessage 
	*/
	public void assertFechaDesdeHasta(Date fechaDesde, Date fechaHasta, String errorMessage) {
		assertTrue(fechaDesde.compareTo(fechaHasta) < 0, errorMessage);
	}

	/**
	 * Verifica que la fecha de fin no sea null y además que sea posterior al dia de hoy.
	 * 
	 * @param fechaHasta
	 * @param errorMessage
	 *
	*/
	public void assertFechaFinValida(Date fechaHasta, String errorMessage) {
		assertFalse(fechaHasta==null || (fechaHasta!=null && (new Date()).after(fechaHasta)), errorMessage);
	}
	
	/**
	 * Verifica que la fecha desde sea mayor que la fecha hasta.
	 * Comprueba que no sean null.
	 * 
	 * @param fechaDesde
	 * @param fechaHasta
	 * @param errorMessage 
	*/
	public void assertFechaDesdeHastaSinNull(Date fechaDesde, Date fechaHasta, String errorMessage) {
		assertFalse(fechaHasta!=null && fechaDesde!=null && fechaDesde.after(fechaHasta), errorMessage);
	}
	
	
	/**
	 * Verifica que dos cadena sean iguales.
	 * 
	 * @param fechaDesde
	 * @param fechaHasta
	 * @param errorMessage 
	*/
	public void assertCadenasIguales(String cadena01,String cadena02, String errorMessage) {
		assertTrue(cadena01!=null && cadena02!=null && cadena01.compareTo(cadena02)==0, errorMessage);
	}

	/**
	 * Verifica que se haya adjuntado un archivo.
	 * 
	 * @param formFile
	 * @param errorMessage 
	 */
	public void assertIsUploaded(FormFile formFile, String errorMessage) {
		assertNotEmpty(formFile.getFileName(), errorMessage);
		assertTrue(formFile.getFileSize() > 0, "El archivo adjunto no posee datos.");
	}
	
	public void assertPasswordLength(String password, String errorMessage) {
		assertTrue(password == null || password.length() >= 5, errorMessage);
	}
	
	
	/**
	 * TODO !!!!!!!!!!!!!!!!!!!!
	 * Verificar que las cadenas de texto ingresadas por el usuario no afecten el correcto
	 * funcionamiento de la aplicación. 
	 * Por ej: caracteres especiales, sentencias sql, javascript (evitar el XSS), etc.
	 * 
	 * Se debería aplicar en todos los inputs de texto.
	 * @param texto
	 * @param errorMessage
	 */
	public void assertTextoSeguro(String texto, String errorMessage) {
		assertTrue(true, errorMessage);
	}
	
	/**
	 * TODO !!!!!!!!!!!!!!!!!!!!
	 * Verificar que las cadenas de texto que representan un HTML ingresadas por el usuario no afecten el correcto
	 * funcionamiento de la aplicación. 
	 * Por ej: caracteres especiales, sentencias sql, javascript (evitar el XSS), etc.
	 * 
	 * Se debería aplicar en todos los inputs de texto.
	 * @param texto
	 * @param errorMessage
	 */
	public void assertTextoHTMLSeguro(String texto, String errorMessage) {
		assertTrue(true, errorMessage);
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