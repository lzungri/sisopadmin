package ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas;

import ar.com.grupo306.usecasefwk.usecases.domain.base.BaseUseCase;

public class AdministrarEncuestaUseCase extends BaseUseCase {

	public Class useCaseModelClass() {
		return AdministrarEncuestaUseCaseModel.class;
	}

	public boolean isBaseUseCase() {
		return false;
	}

	public String getShortDescription() {
		return "Caso de uso de administración de encuestas";
	}

	public String getLongDescription() {
		return "Caso de uso de administración de encuestas";
	}

}