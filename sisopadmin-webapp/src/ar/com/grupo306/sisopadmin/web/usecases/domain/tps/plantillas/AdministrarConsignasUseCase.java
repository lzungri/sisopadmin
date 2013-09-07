/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas;

import ar.com.grupo306.usecasefwk.usecases.domain.base.AdminBaseUseCase;

/**
 * @author Sole
 *
 */
public class AdministrarConsignasUseCase extends AdminBaseUseCase {

	@Override
	public boolean isVisibleOnMenu() {		
		return false;
	}

	public String getLongDescription() {
		return "Seleccionar Consignas de una fase del TP";
	}
	
	public String getShortDescription() {		
		return "Seleccionar consignas";
	}

	
	public Class useCaseModelClass() {
		return AdministrarConsignasUseCaseModel.class;
	}

}
