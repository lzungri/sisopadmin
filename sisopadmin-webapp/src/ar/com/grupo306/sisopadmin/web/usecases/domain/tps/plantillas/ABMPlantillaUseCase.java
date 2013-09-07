/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas;

import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;

/**
 * @author Sole
 *
 */
public class ABMPlantillaUseCase extends ABMBaseUseCase {
	@Override
	public boolean isVisibleOnMenu() {
		return false;
	}

	public String getLongDescription() {
		return "Modificar, Visualizar, Cargar y Eliminar Plantillas de Corrección";
	}
	
	public String getShortDescription() {
		return "ABM de Plantillas de Corrección";
	}
	
	public Class useCaseModelClass() {
		return ABMPlantillaUseCaseModel.class;
	}

}
