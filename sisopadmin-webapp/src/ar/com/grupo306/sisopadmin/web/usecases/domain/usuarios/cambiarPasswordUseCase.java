package ar.com.grupo306.sisopadmin.web.usecases.domain.usuarios;

import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;

/**
 * Caso de uso de para Cambiar el Password del usuario logueado.
 * 
 * @author Damian
 */

public class cambiarPasswordUseCase  extends ABMBaseUseCase {
	
	public Class useCaseModelClass() {
		return cambiarPasswordUseCaseModel.class;
	}

	public boolean isBaseUseCase() {
		return false;
	}

 	public boolean isVisibleOnMenu() {
 		return false;
 	}
	
	public String getShortDescription() {
		return "Cambiar Password";
	}

	public String getLongDescription() {
		return "Permite cambiar el password al usuario logueado.";
	}
}
