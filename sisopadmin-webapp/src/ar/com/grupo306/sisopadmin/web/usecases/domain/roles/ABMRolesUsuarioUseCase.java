package ar.com.grupo306.sisopadmin.web.usecases.domain.roles;

import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;

/**
 * Caso de uso de para Asignar Roles a usuarios.
 * 
 * @author Damian
 */

public class ABMRolesUsuarioUseCase  extends ABMBaseUseCase {
	
	public Class useCaseModelClass() {
		return ABMRolesUsuarioUseCaseModel.class;
	}

	public boolean isBaseUseCase() {
		return false;
	}
	
	public boolean isVisibleOnMenu() {
		return false;
	}

	public String getShortDescription() {
		return "ABM de Roles del Usuario";
	}

	public String getLongDescription() {
		return "Permite Modificar la asignación de Roles del Usuario";
	}
}
