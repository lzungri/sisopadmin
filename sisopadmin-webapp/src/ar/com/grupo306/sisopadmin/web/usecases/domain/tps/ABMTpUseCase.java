/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.tps;

import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;

/**
 * @author Sole
 *
 */
public class ABMTpUseCase extends ABMBaseUseCase {	
	public String getLongDescription() {
		return "Visualizar, Modificar y Eliminar Tps";
	}

	public String getShortDescription() {
		return "ABM de Tps";
	}

	public Class useCaseModelClass() {		
		return ABMTpUseCaseModel.class;
	}

	@Override
	public boolean isVisibleOnMenu() {
		return false;
	}
}
