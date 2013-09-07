package ar.com.grupo306.usecasefwk.usecases.domain.test;

import java.util.List;

import ar.com.grupo306.usecasefwk.usecases.domain.base.BaseUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;
import ar.com.grupo306.usecasefwk.usecases.models.test.CUDosUseCaseModel;

/**
 * @author Leandro
 */
public class CUDosUseCase extends BaseUseCase {

	public Class useCaseModelClass() {
		return CUDosUseCaseModel.class;
	}

	public String getShortDescription() {
		return "Caso de uso dos";
	}

	public String getLongDescription() {
		return "Caso de uso dos";
	}

	public void availableModes(List<UseCaseModelMode> availableModes) {
		availableModes.add(new CreateMode());
		availableModes.add(new EditMode());
	}
	
}