package ar.com.grupo306.usecasefwk.usecases.registry;

import java.util.Collection;
import java.util.Iterator;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.usecasefwk.usecases.domain.base.UseCase;
import ar.com.grupo306.usecasefwk.usecases.menu.MenuBuilder;

/**
 * Esta clase actúa como registro de casos de uso de la aplicación.
 * 
 * SINGLETON
 * 
 * @author Leandro
 */
public class UseCaseRegistry {
	private static UseCaseRegistry useCaseRegistry;
	
	private Collection<RegistryElement> registryElements = CollectionFactory.createCollection(RegistryElement.class);
	
	private UseCaseRegistry() {
		super();
	}
	
	public static synchronized UseCaseRegistry getInstance() {
		if(useCaseRegistry == null) {
			useCaseRegistry = new UseCaseRegistry();
		}
		return useCaseRegistry;
	}
	
	/**
	 * Registra un caso de uso.
	 * 
	 * @param useCase
	 */
	public UseCaseRegistry registerUseCase(UseCase useCase) {
		registryElements.add(new LeafRegistryElement(useCase));
		
		return this;
	}
	
	/**
	 * Registra una agrupación de casos de uso.
	 * 
	 * @param useCaseRegistry
	 */
	public UseCaseRegistry registerUseCases(CompositeRegistryElement compositeRegistry) {
		registryElements.add(compositeRegistry);
		
		return this;
	}
	
	/**
	 * Retorna los elementos registrados.
	 */
	public Collection<RegistryElement> getRegistryElements() {
		return this.registryElements;
	}
	
	/**
	 * Método que se encarga de transformar los UseCases registrados en base
	 * a un UseCaseTransformer.
	 * 
	 * @param actionMappingCreator
	 * @param actionMappings
	 * 
	 * @return Collection de UseCases transformados.
	 */
	public Collection transform(UseCaseTransformer useCaseTransformer) {
		Collection transforms = CollectionFactory.createCollection();
		
		for(RegistryElement registryElement : this.registryElements) {
			registryElement.transform(useCaseTransformer, transforms);
		}
		
		return transforms;
	}
	
	public void createMenu(MenuBuilder menuBuilder) {
		for(Iterator<RegistryElement> it = this.registryElements.iterator(); it.hasNext(); ) {
			it.next().createMenu(menuBuilder);
			
			if(it.hasNext())
				menuBuilder.hasNextElement();
		}
	}
	
}