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
 * Contenedor de información acerca del usuario interactuando con el sistema.
 * 
 * Actúa como caché de cierta información del usuario, como por ejemplo el menú.
 *
 * @author Leandro
 */
public abstract class UserContext {
	/** Permisos asociados al usuario.	 */
	Set<Permiso> permisosUsuario = CollectionFactory.createSet(Permiso.class);
	
	/**
	 * Menú asociado al usuario. Se compone de RegistryElements.
	 * Es un subconjunto de la UseCaseRegistry.
	 */
	private Collection<RegistryElement> userRegistryElements;
	
	
	protected void createMenu() {
		MenuBuilder menuBuilder = new UserMenuBuilder(this.permisosUsuario);
		
		UseCaseRegistry.getInstance().createMenu(menuBuilder);
		this.userRegistryElements = (Collection<RegistryElement>) menuBuilder.build();
	}
	
	/**
	 * Indica si el usuario se encuentra actualmente logueado en la aplicación.
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