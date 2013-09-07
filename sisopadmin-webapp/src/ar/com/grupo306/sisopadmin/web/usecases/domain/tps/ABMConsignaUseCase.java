/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.tps;

import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;

/**
 * @author Sole
 *
 */
public class ABMConsignaUseCase extends ABMBaseUseCase {
	public String getLongDescription() {
		return "Visualizar, Cargar, Modficar y Eliminar Consignas";
	}
	
	public String getShortDescription() {
		return "ABM de Consignas";
	}

	public Class useCaseModelClass() {		
		return ABMConsignaUseCaseModel.class;
	}

	@Override
	public boolean isVisibleOnMenu() {
		return false;
	}
	

}
