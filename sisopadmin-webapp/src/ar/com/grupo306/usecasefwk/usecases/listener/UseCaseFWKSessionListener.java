package ar.com.grupo306.usecasefwk.usecases.listener;


import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import ar.com.grupo306.usecasefwk.constants.UseCaseFWKConstants;
import ar.com.grupo306.usecasefwk.usecases.stack.UseCaseStack;

/**
 * Administra la creación y finalización de la sesión del usuario.
 *
 * @author Leandro
 */
public class UseCaseFWKSessionListener implements HttpSessionListener {
	protected static Logger logger = Logger.getLogger(UseCaseFWKSessionListener.class);

	public void sessionCreated(HttpSessionEvent sessionEvent) {
		// Se crea sobre la Session la pila de casos de uso.
		UseCaseStack stack = new UseCaseStack();
		sessionEvent.getSession().setAttribute(UseCaseFWKConstants.SESSION_USECASE_STACK, stack);
		
		logger.info("Se ha creado la Session de id: " + sessionEvent.getSession().getId());
	}

	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		logger.info("Se ha eliminado la Session de id: " + sessionEvent.getSession().getId());
	}

}