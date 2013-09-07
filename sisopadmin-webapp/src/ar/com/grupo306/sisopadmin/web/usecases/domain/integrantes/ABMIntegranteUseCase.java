 package ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes;

import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;

/**
 * Caso de uso de para Cargar(CU 1.1.2), Visualizar(CU 1.1.3), Modificar(CU 1.1.4) y 
 * Eliminar(CU 1.1.5) Integrantes de la Cátedra, que pueden ser tanto Ayudantes como 
 * Coordinadores 
 * *
 * @author Sole
 */

public class ABMIntegranteUseCase  extends ABMBaseUseCase{
	
	public boolean isVisibleOnMenu() {		
		return false;
	}

	public Class useCaseModelClass() {
		return ABMIntegranteUseCaseModel.class;
	}

	public boolean isBaseUseCase() {
		return false;
	}

	public String getShortDescription() {
		return "ABM integrantes de Cátedra";
	}

	public String getLongDescription() {
		return "Permite Cargar, Visualizar, Modificar y Eliminar Integrante de la Cátedra ";
	}
	
}
