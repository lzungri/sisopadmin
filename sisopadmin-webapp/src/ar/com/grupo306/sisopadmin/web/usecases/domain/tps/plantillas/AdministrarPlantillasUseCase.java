/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas;

import ar.com.grupo306.usecasefwk.usecases.domain.base.AdminBaseUseCase;

/**
 * @author Sole
 *
 */
public class AdministrarPlantillasUseCase extends AdminBaseUseCase {
	
	public String getLongDescription() {		
		return "Administrar plantillas de corrección de TPs";
	}
	
	public String getShortDescription() {
		return "Administrar plantillas de corrección";
	}
	
	public Class useCaseModelClass() {
		return AdministrarPlantillasUseCaseModel.class;
	}
	
	
}
