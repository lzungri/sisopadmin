package ar.com.grupo306.sisopadmin.web.usecases.domain.mensajes;

import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;

/**
 * 
 * @author Pablo
 */

public class ABMMensajesUseCase  extends ABMBaseUseCase {
	
	public Class useCaseModelClass() {
		return ABMMensajesUseCaseModel.class;
	}

 	public boolean isVisibleOnMenu() {
 		return false;
 	}
	
	public String getShortDescription() {
		return "ABM de Mensaje";
	}

	public String getLongDescription() {
		return "Permite Cargar y Visualizar un Mensaje";
	}
}
