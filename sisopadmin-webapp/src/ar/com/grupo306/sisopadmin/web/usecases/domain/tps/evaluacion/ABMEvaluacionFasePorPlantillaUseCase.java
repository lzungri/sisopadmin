package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion;

import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;

/**
 * CU para dar de ABM Evaluaciones de fase.
 *
 * @author Leandro
 */
public class ABMEvaluacionFasePorPlantillaUseCase extends ABMBaseUseCase {

	public String getLongDescription() {
		return "Permite crear, modificar, visualizar y eliminar evaluaciones de fase";
	}

	public String getShortDescription() {
		return "ABM de evaluaciones de fase";
	}

	public Class useCaseModelClass() {
		return ABMEvaluacionFasePorPlantillaUseCaseModel.class;
	}

	public boolean isVisibleOnMenu() {
		return false;
	}

}