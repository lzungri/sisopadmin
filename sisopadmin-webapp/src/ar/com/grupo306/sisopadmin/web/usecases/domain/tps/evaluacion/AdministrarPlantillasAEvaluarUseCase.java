package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion;

import ar.com.grupo306.usecasefwk.usecases.domain.base.BaseUseCase;

/**
 * CU para administrar las plantillas a evaluar de una fase.
 *
 * @author Leandro
 */
public class AdministrarPlantillasAEvaluarUseCase extends BaseUseCase {

	public String getLongDescription() {
		return "Permite gestionar las plantillas a evaluar de una fase determinada";
	}

	public String getShortDescription() {
		return "Evaluar fase en base plantillas";
	}

	public Class useCaseModelClass() {
		return AdministrarPlantillasAEvaluarUseCaseModel.class;
	}

}