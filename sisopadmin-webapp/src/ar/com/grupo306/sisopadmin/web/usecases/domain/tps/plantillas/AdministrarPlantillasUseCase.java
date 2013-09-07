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
		return "Administrar plantillas de correcci�n de TPs";
	}
	
	public String getShortDescription() {
		return "Administrar plantillas de correcci�n";
	}
	
	public Class useCaseModelClass() {
		return AdministrarPlantillasUseCaseModel.class;
	}
	
	
}
