package ar.com.grupo306.usecasefwk.usecases.domain.base;

import java.util.List;

import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;


/**
 * Abstracci�n que representa un Caso de uso en la aplicaci�n.
 *
 * @author Leandro
 */
public interface UseCase {

	/*------------------------------ CONFIGURACION DEL USE CASE ---------------------------*/
	
	/**
	 * Class que ser� utilizada como modelo(pantalla) para el caso de uso.
	 * Esta clase debe extender debe UseCaseModel.
	 */
	public Class useCaseModelClass();
	
	/**
	 * Indica si el CU es base para el usuario, por lo tanto debe persistir durante
	 * toda la sesi�n.<br>
	 * Ejemplo:
	 * <li> Casos de uso de Login.
	 * <li> Casos de uso de men� principal. 
	 */
	public boolean isBaseUseCase();
	
	/**
	 * TODO HACER!
	 * Creo que lo mejor ser�a que la auditoria se defina de manera din�mica, es
	 * decir, obteni�ndola de la base o de un properties.
	 * Para ello se deber�a crear un CU de administraci�n de CUs.
	 */
	public int auditLevel();
	
	/**
	 * TODO HACER!
	 * 	Creo que lo mejor ser�a que la seguridad se defina de manera din�mica, es
	 * decir, obteni�ndola de la base o de un properties.
	 * Para ello se deber�a crear un CU de administraci�n de CUs.
	 */
	public boolean securityEnabled();
	
	/**
	 * Clave de tiles que define la p�gina (JSP) asociada al UseCase.
	 */
	public String tilesDefinitionKey();
	
	/**
	 * Nombre del formulario (Mapeado en struts-config.xml) que utilizar�
	 * el UseCase.
	 */
	public String useCaseForm();
	
	/**
	 * Indica si el CU deber� aparecer en el men� de la aplicaci�n.
	 */
	public boolean isVisibleOnMenu();
	
	/**
	 * Descripci�n corta del UseCase.
	 * Por ejemplo: "Administraci�n de trabajos pr�cticos.".
	 */
	public String getShortDescription();
	
	/**
	 * Descripci�n larga del UseCase.
	 * Por ejemplo: "Permite el Alta, baja y modificaci�n de trabajos pr�cticos...".
	 */
	public String getLongDescription();
	
	/**
	 * Indica los modos en los cuales se puede iniciar el UseCaseModel.
	 * Cuando se intente iniciar en un modo no v�lido se arrojar� una ProgramException.
	 * 
	 * @param availableModes
	 */
	public void availableModes(List<UseCaseModelMode> availableModes);
	
}