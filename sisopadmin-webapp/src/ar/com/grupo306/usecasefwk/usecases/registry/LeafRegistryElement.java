package ar.com.grupo306.usecasefwk.usecases.registry;

import java.util.Collection;

import ar.com.grupo306.usecasefwk.usecases.domain.base.UseCase;
import ar.com.grupo306.usecasefwk.usecases.menu.MenuBuilder;

/**
 * Elemento hoja.
 *
 * @author Leandro
 */
public class LeafRegistryElement implements RegistryElement {
	private UseCase useCase;
	
	public LeafRegistryElement(UseCase useCase) {
		this.useCase = useCase;
	}

	public void transform(UseCaseTransformer transformer, Collection transforms) {
		transforms.add(transformer.transform(this.useCase));
	}

	public void createMenu(MenuBuilder menuBuilder) {
		menuBuilder.appendItem(this.useCase);
	}

	public UseCase getUseCase() {
		return useCase;
	}

	public void setUseCase(UseCase useCase) {
		this.useCase = useCase;
	}
	
}