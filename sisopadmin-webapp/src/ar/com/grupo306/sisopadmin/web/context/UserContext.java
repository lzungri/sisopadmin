package ar.com.grupo306.sisopadmin.web.context;

import java.util.Collection;
import java.util.Set;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Permiso;
import ar.com.grupo306.usecasefwk.usecases.menu.MenuBuilder;
import ar.com.grupo306.usecasefwk.usecases.menu.UserMenuBuilder;
import ar.com.grupo306.usecasefwk.usecases.registry.RegistryElement;
import ar.com.grupo306.usecasefwk.usecases.registry.UseCaseRegistry;

/**
 * Contenedor de informaci�n acerca del usuario interactuando con el sistema.
 * 
 * Act�a como cach� de cierta informaci�n del usuario, como por ejemplo el men�.
 *
 * @author Leandro
 */
public abstract class UserContext {
	/** Permisos asociados al usuario.	 */
	Set<Permiso> permisosUsuario = CollectionFactory.createSet(Permiso.class);
	
	/**
	 * Men� asociado al usuario. Se compone de RegistryElements.
	 * Es un subconjunto de la UseCaseRegistry.
	 */
	private Collection<RegistryElement> userRegistryElements;
	
	
	protected void createMenu() {
		MenuBuilder menuBuilder = new UserMenuBuilder(this.permisosUsuario);
		
		UseCaseRegistry.getInstance().createMenu(menuBuilder);
		this.userRegistryElements = (Collection<RegistryElement>) menuBuilder.build();
	}
	
	/**
	 * Indica si el usuario se encuentra actualmente logueado en la aplicaci�n.
	 */
	public abstract boolean isUserLogged();
	
	

	public Collection<RegistryElement> getUserRegistryElements() {
		return userRegistryElements;
	}
	
	public Collection<Permiso> getPermisosUsuario() {
		return permisosUsuario;
	}
	
	public void setPermisosUsuario(Set<Permiso> permisosUsuario) {
		this.permisosUsuario = permisosUsuario;
	}
	
}