package ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes;

import ar.com.grupo306.usecasefwk.usecases.domain.base.BaseUseCase;

public class AsignarAyudantesACoordinadorUseCase extends BaseUseCase {

	public String getLongDescription() {
		return "Asignación de los ayudantes que dirige un Coordinador";
	}

	public String getShortDescription() {
		return "Asigna Ayudantes a un Coordinador";
	}

	public Class useCaseModelClass() {
		return AsignarAyudantesACoordinadorUseCaseModel.class;
	}

	@Override
	public boolean isVisibleOnMenu() {
		return false;
	}
}
