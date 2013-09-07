package ar.com.grupo306.sisopadmin.web.usecases.domain.roles;

import ar.com.grupo306.usecasefwk.usecases.domain.base.BaseUseCase;

/**
 * Caso de uso de para 
 * 		CU 1.3.1 - Administrar Roles 
 *  
 * @author Damian
 */

public class AdministrarRolesUseCase  extends BaseUseCase{
	
	public Class useCaseModelClass() {
		return AdministrarRolesUseCaseModel.class;
	}

	public boolean isBaseUseCase() {
		return false;
	}

	public String getShortDescription() {
		return "Administrar Roles de la Cátedra";
	}

	public String getLongDescription() {
		return "Permite Administrar los Roles de la Cátedra";
	}
}
