package ar.com.grupo306.usecasefwk.usecases.domain.base;

import java.util.List;

import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;


/**
 * Abstracción que representa un Caso de uso en la aplicación.
 *
 * @author Leandro
 */
public interface UseCase {

	/*------------------------------ CONFIGURACION DEL USE CASE ---------------------------*/
	
	/**
	 * Class que será utilizada como modelo(pantalla) para el caso de uso.
	 * Esta clase debe extender debe UseCaseModel.
	 */
	public Class useCaseModelClass();
	
	/**
	 * Indica si el CU es base para el usuario, por lo tanto debe persistir durante
	 * toda la sesión.<br>
	 * Ejemplo:
	 * <li> Casos de uso de Login.
	 * <li> Casos de uso de menú principal. 
	 */
	public boolean isBaseUseCase();
	
	/**
	 * TODO HACER!
	 * Creo que lo mejor sería que la auditoria se defina de manera dinámica, es
	 * decir, obteniéndola de la base o de un properties.
	 * Para ello se debería crear un CU de administración de CUs.
	 */
	public int auditLevel();
	
	/**
	 * TODO HACER!
	 * 	Creo que lo mejor sería que la seguridad se defina de manera dinámica, es
	 * decir, obteniéndola de la base o de un properties.
	 * Para ello se debería crear un CU de administración de CUs.
	 */
	public boolean securityEnabled();
	
	/**
	 * Clave de tiles que define la página (JSP) asociada al UseCase.
	 */
	public String tilesDefinitionKey();
	
	/**
	 * Nombre del formulario (Mapeado en struts-config.xml) que utilizará
	 * el UseCase.
	 */
	public String useCaseForm();
	
	/**
	 * Indica si el CU deberá aparecer en el menú de la aplicación.
	 */
	public boolean isVisibleOnMenu();
	
	/**
	 * Descripción corta del UseCase.
	 * Por ejemplo: "Administración de trabajos prácticos.".
	 */
	public String getShortDescription();
	
	/**
	 * Descripción larga del UseCase.
	 * Por ejemplo: "Permite el Alta, baja y modificación de trabajos prácticos...".
	 */
	public String getLongDescription();
	
	/**
	 * Indica los modos en los cuales se puede iniciar el UseCaseModel.
	 * Cuando se intente iniciar en un modo no válido se arrojará una ProgramException.
	 * 
	 * @param availableModes
	 */
	public void availableModes(List<UseCaseModelMode> availableModes);
	
}