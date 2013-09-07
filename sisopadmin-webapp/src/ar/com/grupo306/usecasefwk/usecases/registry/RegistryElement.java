package ar.com.grupo306.usecasefwk.usecases.registry;

import java.util.Collection;

import ar.com.grupo306.usecasefwk.usecases.menu.MenuBuilder;

/**
 * Elemento de la UseCaseRegistry
 *
 * @author Leandro
 */
public interface RegistryElement {
	
	/**
	 * Transforma los UseCase registrados.
	 * 
	 * @param useCase
	 */
	public void transform(UseCaseTransformer transformer, Collection transforms);
	
	
	/**
	 * Crea el menú y lo almacena en el Object menu.
	 * 
	 * @param menuCreator
	 * @param menu
	 */
	public void createMenu(MenuBuilder menuBuilder);

}