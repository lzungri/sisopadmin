package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas;

import ar.com.grupo306.usecasefwk.usecases.domain.base.BaseUseCase;

public class SelectFaseUseCase extends BaseUseCase {

	@Override
	public boolean isVisibleOnMenu() {
		return false;
	}

	public String getLongDescription() {
		return "Seleccionar Fases para plantillas de corrección";
	}

	public String getShortDescription() {
		return "Plantillas Corrección - Selección Fase";
	}

	public Class useCaseModelClass() {
		return SelectFaseUseCaseModel.class;
	}

}
