package ar.com.grupo306.sisopadmin.web.context;

import java.util.Set;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Permiso;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Rol;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;


/**
 * Contenedor de información sobre el usuario logueado en el sistema.
 * 
 * @author Leandro
 */
public class LoggedUserContext extends UserContext {
	
	/** loginName del usuario. */
	private String userLoginName;
	
	/** Eventos notificables al usuario. */
//	private Collection<EventoNotificable> eventosNotificables;
	
	public LoggedUserContext(String userLoginName) {
		this.userLoginName = userLoginName;
		
		Usuario usuario = Usuario.findMeByLoginName(userLoginName);
		
		Set<Permiso> permisos = CollectionFactory.createSet(Permiso.class);
		for(Rol rol : (Set<Rol>) usuario.getRoles()) {
			permisos.addAll(rol.getPermisos());
		}
		setPermisosUsuario(permisos);

		createMenu();
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

}