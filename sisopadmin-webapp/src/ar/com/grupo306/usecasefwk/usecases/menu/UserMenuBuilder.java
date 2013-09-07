package ar.com.grupo306.usecasefwk.usecases.menu;

import java.util.Collection;
import java.util.Stack;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Permiso;
import ar.com.grupo306.usecasefwk.usecases.domain.base.UseCase;
import ar.com.grupo306.usecasefwk.usecases.models.base.UseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.registry.CompositeRegistryElement;
import ar.com.grupo306.usecasefwk.usecases.registry.LeafRegistryElement;
import ar.com.grupo306.usecasefwk.usecases.registry.RegistryElement;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseUtils;

/**
 * Crea el menú asociado a un usuario.
 *
 * @author Leandro
 */
public class UserMenuBuilder implements MenuBuilder {
	/** Permisos que posee el usuario, en base a ellos se genera el menú. */
	Collection<Permiso> permisosUsuario = CollectionFactory.createCollection(Permiso.class);
	
	/**
	 * Estructura que referencia a los LeafRegistryElements del UseCaseRegistry
	 * habilitados para el usuario.
	 */
	private Collection<RegistryElement> registryElements = CollectionFactory.createCollection(RegistryElement.class);
	
	/**
	 * Composites en creación..
	 */
	private Stack<CompositeRegistryElement> composites = new Stack<CompositeRegistryElement>();
	
	

	/**
	 * El menú es generado en base a los permisos asociados al usuario.
	 */
	public UserMenuBuilder(Collection<Permiso> permisosUsuario) {
		this.permisosUsuario = permisosUsuario;
	}

	public void appendItem(UseCase useCase) {
		// Previo a agregar el UseCase al menú, se valida que el usuario posea los
		// permisos necesarios para invocarlo.
		if(this.hasPermission(useCase) && useCase.isVisibleOnMenu()) {
			if(this.composites.isEmpty()) {
				this.registryElements.add(new LeafRegistryElement(useCase));
			}
			else {
				this.composites.peek().addUseCase(useCase);
			}
		}
	}

	public void appendInitCompositeItem(String description, String url) {
		CompositeRegistryElement composite = new CompositeRegistryElement();
		composite.setDescription(description);
		composite.setUrl(url);
		
		// Se apila el último Composite creado.
		this.composites.push(composite);
	}

	public void appendCloseCompositeItem() {
		if(!this.composites.isEmpty()) {
			CompositeRegistryElement composite = this.composites.pop();
			if(!composite.getRegistryElements().isEmpty()) {
				 if(this.composites.isEmpty()) {
		                this.registryElements.add(composite);
		            }
		            else {
		                this.composites.peek().addUseCases(composite);
		            }
			}
		}
	}
	
	public void appendHeader() {
		// Nada.
	}

	public void appendTail() {
		// Nada.
	}
	
	public void hasNextElement() {
		//Nada
	}
	
	public Object build() {
		return this.registryElements;
	}

	private boolean hasPermission(UseCase useCase) {
		try {
			return UseCaseUtils.hasPermission(useCase, null, UseCaseModel.INIT_USE_CASE_METHOD, this.permisosUsuario);
		}
		catch(Exception excep) {
			throw ExceptionFactory.createProgramException("Error al intentar analizar la seguridad de un método", excep);
		}
	}

}