package ar.com.grupo306.usecasefwk.usecases.domain.test;

import ar.com.grupo306.usecasefwk.usecases.domain.base.AdminBaseUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.test.CUUnoUseCaseModel;

/**
 * @author Leandro
 */
public class CUUnoUseCase extends AdminBaseUseCase {

	public Class useCaseModelClass() {
		return CUUnoUseCaseModel.class;
	}

	public String getShortDescription() {
		return "Caso de uso uno";
	}

	public String getLongDescription() {
		return "Caso de uso uno";
	}
	
}