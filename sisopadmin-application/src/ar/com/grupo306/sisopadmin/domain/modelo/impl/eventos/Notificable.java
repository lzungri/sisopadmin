package ar.com.grupo306.sisopadmin.domain.modelo.impl.eventos;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;

/**
 * Representa una entidad capaz de recibir EventoNotificables.
 *
 * @author Leandro
 */
public interface Notificable {
	
	/**
	 * Indica si la notificación es aplicable al usuario enviado por parámetro.
	 * 
	 * @param usuario
	 */
	public boolean matchesNotificationWith(Usuario usuario);

}