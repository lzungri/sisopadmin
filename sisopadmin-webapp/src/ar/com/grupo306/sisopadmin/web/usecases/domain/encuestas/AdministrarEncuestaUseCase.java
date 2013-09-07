package ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas;

import ar.com.grupo306.usecasefwk.usecases.domain.base.AdminBaseUseCase;

public class AdministrarEncuestaUseCase extends AdminBaseUseCase {

	public Class useCaseModelClass() {
		return AdministrarEncuestaUseCaseModel.class;
	}

	public boolean isBaseUseCase() {
		return false;
	}

	public String getShortDescription() {
		return "Administrar encuestas";
	}

	public String getLongDescription() {
		return "Administración de encuestas";
	}

}