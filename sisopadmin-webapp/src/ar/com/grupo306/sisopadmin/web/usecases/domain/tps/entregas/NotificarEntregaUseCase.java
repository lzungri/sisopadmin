package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas;

import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;

/**
 * CU por el cual el grupo notifica la realización de una entrega para una fase determinada. 
 *
 * @author Leandro
 */
public class NotificarEntregaUseCase extends ABMBaseUseCase {

	public String getLongDescription() {
		return "Notificar la realización de una entrega para una fase determinada";
	}

	public String getShortDescription() {
		return "Notificar entrega";
	}

	public Class useCaseModelClass() {
		return NotificarEntregaUseCaseModel.class;
	}

	public boolean isVisibleOnMenu() {
		return false;
	}

}