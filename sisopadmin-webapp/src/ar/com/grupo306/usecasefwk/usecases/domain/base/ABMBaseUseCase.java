package ar.com.grupo306.usecasefwk.usecases.domain.base;

import java.util.List;

import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.RemoveMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.ViewMode;

/**
 * Caso de uso de alta, baja y modificación de entidades.
 *
 * @author Leandro
 */
public abstract class ABMBaseUseCase extends BaseUseCase {
	
	public void availableModes(List<UseCaseModelMode> availableModes) {
		availableModes.add(new CreateMode());
		availableModes.add(new EditMode());
		availableModes.add(new RemoveMode());
		availableModes.add(new ViewMode());
	}

}