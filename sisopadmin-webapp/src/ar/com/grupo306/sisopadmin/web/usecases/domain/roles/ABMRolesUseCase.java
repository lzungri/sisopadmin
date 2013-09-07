package ar.com.grupo306.sisopadmin.web.usecases.domain.roles;

import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;

/**
 * Caso de uso de para 
 * 		CU 1.3.2 - Cargar Rol
 *  	CU 1.3.3 - Visualizar Rol
 *  	CU 1.3.4 - Modificar Rol
 *  	CU 1.3.5 - Eliminar Rol
 * 		CU 1.3.6 - Asignar Permisos a Rol
 * 
 * @author Damian
 */

public class ABMRolesUseCase  extends ABMBaseUseCase {
	
	public Class useCaseModelClass() {
		return ABMRolesUseCaseModel.class;
	}

	public boolean isBaseUseCase() {
		return false;
	}
	
	public boolean isVisibleOnMenu() {
		return false;
	}

	public String getShortDescription() {
		return "ABM de Roles de la Cátedra";
	}

	public String getLongDescription() {
		return "Permite Cargar, Visualizar, Modificar Permisos y Eliminar Roles de la Cátedra";
	}
}
