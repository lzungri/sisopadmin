package ar.com.grupo306.usecasefwk.usecases.domain.base;

import java.util.List;

import ar.com.grupo306.usecasefwk.usecases.models.mode.OperationMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.SelectionMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;

/**
 * Caso de uso para la administración de entidades.
 *
 * @author Leandro
 */
public abstract class AdminBaseUseCase extends BaseUseCase {
	
	public void availableModes(List<UseCaseModelMode> availableModes) {
		availableModes.add(new OperationMode());
		availableModes.add(new SelectionMode());
	}

}