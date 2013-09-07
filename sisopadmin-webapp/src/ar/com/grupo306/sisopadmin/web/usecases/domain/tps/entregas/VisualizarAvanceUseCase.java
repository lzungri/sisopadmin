package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas;

import ar.com.grupo306.usecasefwk.usecases.domain.base.AdminBaseUseCase;

/**
 * @author Pablo
 */
public class VisualizarAvanceUseCase extends AdminBaseUseCase {

	public Class useCaseModelClass() {
		return VisualizarAvanceUseCaseModel.class;
	}

	public String getShortDescription() {
		return "Visualizar avance de grupo";
	}

	public String getLongDescription() {
		return "Visualizar avance de grupo (entregas realizadas y pendientes, visualización gráfica del status de las entregas).";
	}
	
}