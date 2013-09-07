package ar.com.grupo306.sisopadmin.security.impl;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.security.interfaces.SecurityService;

/**
 * Implementaci�n del servicio de seguridad de la aplicaci�n.
 *
 * @author Leandro
 */
public class SecurityServiceImpl implements SecurityService {

	public boolean isValidUser(String userLoginName, String password) {
		Usuario usuario = Usuario.findMeByLoginName(userLoginName);
		
		return usuario != null && usuario.getPassword().equals(password);
	}

}