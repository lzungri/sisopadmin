package ar.com.grupo306.usecasefwk.usecases.domain.test;

import java.util.List;

import ar.com.grupo306.usecasefwk.usecases.domain.base.BaseUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.ViewMode;
import ar.com.grupo306.usecasefwk.usecases.models.test.CUTresUseCaseModel;

/**
 * @author Leandro
 */
public class CUTresUseCase extends BaseUseCase {

	public Class useCaseModelClass() {
		return CUTresUseCaseModel.class;
	}

	public String getShortDescription() {
		return "Caso de uso tres";
	}

	public String getLongDescription() {
		return "Caso de uso tres";
	}

	public void availableModes(List<UseCaseModelMode> availableModes) {
		availableModes.add(new EditMode());
		availableModes.add(new CreateMode());
		availableModes.add(new ViewMode());
	}
	
}