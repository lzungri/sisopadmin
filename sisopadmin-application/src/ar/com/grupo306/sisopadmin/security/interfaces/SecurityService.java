package ar.com.grupo306.sisopadmin.security.interfaces;

import ar.com.grupo306.sisopadmin.service.ApplicationService;


/**
 * Servicio de seguridad para la aplicaci�n de reservas.
 *
 * @author Leandro
 */
public interface SecurityService extends ApplicationService {
	
	/**
	 * Determina si el par usuario - password es v�lido para el logueo
	 * a la aplicaci�n del usuario.
	 * 
	 * @param String: Nombre de logueo de usuario.
	 * @param String: Password del usuario.
	 * @return boolean
	 */
	public boolean isValidUser(String usuario, String password);
	
}