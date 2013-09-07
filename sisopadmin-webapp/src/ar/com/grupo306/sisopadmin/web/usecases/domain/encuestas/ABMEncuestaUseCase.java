package ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas;

import ar.com.grupo306.usecasefwk.usecases.domain.base.BaseUseCase;

public class ABMEncuestaUseCase extends BaseUseCase {

	public Class useCaseModelClass() {
		return ABMEncuestaUseCaseModel.class;
	}

	public boolean isBaseUseCase() {
		return false;
	}

	public String getShortDescription() {
		return "Cargar encuestas";
	}

	public String getLongDescription() {
		return "Cargar encuestas";
	}

}