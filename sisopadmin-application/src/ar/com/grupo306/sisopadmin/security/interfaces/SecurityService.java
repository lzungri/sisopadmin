package ar.com.grupo306.sisopadmin.security.interfaces;

import ar.com.grupo306.sisopadmin.service.ApplicationService;


/**
 * Servicio de seguridad para la aplicación de reservas.
 *
 * @author Leandro
 */
public interface SecurityService extends ApplicationService {
	
	/**
	 * Determina si el par usuario - password es válido para el logueo
	 * a la aplicación del usuario.
	 * 
	 * @param String: Nombre de logueo de usuario.
	 * @param String: Password del usuario.
	 * @return boolean
	 */
	public boolean isValidUser(String usuario, String password);
	
}