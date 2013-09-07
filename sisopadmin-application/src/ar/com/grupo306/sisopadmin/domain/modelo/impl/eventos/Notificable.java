package ar.com.grupo306.sisopadmin.domain.modelo.impl.eventos;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;

/**
 * Representa una entidad capaz de recibir EventoNotificables.
 *
 * @author Leandro
 */
public interface Notificable {
	
	/**
	 * Indica si la notificaci�n es aplicable al usuario enviado por par�metro.
	 * 
	 * @param usuario
	 */
	public boolean matchesNotificationWith(Usuario usuario);

}