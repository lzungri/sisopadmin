package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion;

import ar.com.grupo306.usecasefwk.usecases.domain.base.AdminBaseUseCase;

/**
 * Caso de uso para administrar las evaluaciones realizadas por lo ayudantes.
 *
 * @author Leandro
 */
public class AdministrarEvaluacionesFaseUseCase extends AdminBaseUseCase {

	public String getLongDescription() {
		return "Permite realizar la gestión de evaluaciones de fase";
	}

	public String getShortDescription() {
		return "Administrar evaluaciones de fase";
	}

	public Class useCaseModelClass() {
		return AdministrarEvaluacionesFaseUseCaseModel.class;
	}

}