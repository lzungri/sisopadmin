package ar.com.grupo306.sisopadmin.web.context;

import java.util.Set;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.comparators.EventoNotificableByFechaComparator;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.eventos.EventoNotificable;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Permiso;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Rol;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;


/**
 * Contenedor de informaci�n sobre el usuario logueado en el sistema.
 * 
 * @author Leandro
 */
public class LoggedUserContext extends UserContext {
	
	/** loginName del usuario. */
	private String userLoginName;
	
	/** Eventos notificables al usuario. */
	private Set<EventoNotificable> eventosNotificables = CollectionFactory.createTreeSet(EventoNotificable.class, new EventoNotificableByFechaComparator());
	
	public LoggedUserContext(String userLoginName) {
		this.userLoginName = userLoginName;
		
		Usuario usuario = Usuario.findMeByLoginName(userLoginName);
		
		// Permisos de usuario.
		Set<Permiso> permisos = CollectionFactory.createSet(Permiso.class);
		for(Rol rol : (Set<Rol>) usuario.getRoles()) {
			permisos.addAll(rol.getPermisos());
		}
		setPermisosUsuario(permisos);

		// Creaci�n del men�.
		createMenu();
		
		// B�squeda de los eventos notificables.
		for(EventoNotificable eventoNotificable : EventoNotificable.findAllActives()) {
			if(eventoNotificable.matchesWith(usuario)) {
				this.eventosNotificables.add(eventoNotificable);
			}
		}
	}
	
	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public boolean isUserLogged() {
		return true;
	}

	public Set<EventoNotificable> getEventosNotificables() {
		return eventosNotificables;
	}

	public void setEventosNotificables(Set<EventoNotificable> eventosNotificables) {
		this.eventosNotificables = eventosNotificables;
	}

}