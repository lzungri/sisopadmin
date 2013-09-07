package ar.com.grupo306.usecasefwk.usecases.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import ar.com.grupo306.usecasefwk.usecases.domain.base.UseCase;
import ar.com.grupo306.usecasefwk.usecases.menu.MenuBuilder;

/**
 * Elemento compuesto.
 *
 * @author Leandro
 */
public class CompositeRegistryElement implements RegistryElement, Cloneable {
	private Collection<RegistryElement> registryElements = new ArrayList<RegistryElement>();
	
	private String description;
	private String url;
	
	public CompositeRegistryElement() {
		super();
	}
	
	/**
	 * @param description: Descripción a presentar del item compuesto..
	 */
	public CompositeRegistryElement(String description) {
		super();
		this.description = description;
	}
	
	/**
	 * @param description: Descripción a presentar del ítem compuesto..
	 * @param url: URL asociada al ítem compuesto.
	 */
	public CompositeRegistryElement(String description, String url) {
		super();
		this.description = description;
		this.url = url;
	}
	
	/**
	 * Agrega un caso de uso para registrar.
	 */
	public CompositeRegistryElement addUseCase(UseCase useCase) {
		this.registryElements.add(new LeafRegistryElement(useCase));
		
		return this;
	}
	
	public CompositeRegistryElement addUseCases(CompositeRegistryElement composite) {
		this.registryElements.add(composite);
		
		return this;
	}
	
	public void transform(UseCaseTransformer transformer, Collection transforms) {
		for(RegistryElement registryElement : this.registryElements) {
			registryElement.transform(transformer, transforms);			
		}
	}

	public void createMenu(MenuBuilder menuBuilder) {
		menuBuilder.appendInitCompositeItem(this.description, this.url);
			
		for(Iterator<RegistryElement> it = this.registryElements.iterator(); it.hasNext(); ) {
			it.next().createMenu(menuBuilder);
			
			if(it.hasNext())
				menuBuilder.hasNextElement();
		}
			
		menuBuilder.appendCloseCompositeItem();
	}

	public Collection<RegistryElement> getRegistryElements() {
		return registryElements;
	}

	public void setRegistryElements(Collection<RegistryElement> registryElements) {
		this.registryElements = registryElements;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}