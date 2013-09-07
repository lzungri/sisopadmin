package ar.com.grupo306.sisopadmin.web.listener;

import javax.servlet.http.HttpSessionEvent;

import ar.com.grupo306.sisopadmin.web.constants.SisopAdminWebConstants;
import ar.com.grupo306.sisopadmin.web.context.NotLoggedUserContext;
import ar.com.grupo306.usecasefwk.usecases.listener.UseCaseFWKSessionListener;

/**
 * Administra la session en la aplicaci�n de Sisop Admin.
 *
 * @author Leandro
 */
public class SisopAdminSessionListener extends UseCaseFWKSessionListener {

	public void sessionCreated(HttpSessionEvent sessionEvent) {
		super.sessionCreated(sessionEvent);
		
		// Se coloca sobre la session el UserContext correspondiente al usuario
		// a�n no logueado.
		sessionEvent.getSession().setAttribute(SisopAdminWebConstants.SESSION_USER_CONTEXT, NotLoggedUserContext.getInstance());
	}

}