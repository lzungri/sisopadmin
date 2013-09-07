/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.tps;

import ar.com.grupo306.usecasefwk.usecases.domain.base.AdminBaseUseCase;

/**
 * @author Sole
 *
 */
public class AdministrarTPsUseCase extends AdminBaseUseCase{
	
	public String getLongDescription() {
		return "Administración de Trabajos Prácticos";
	}
	
	public String getShortDescription() {		
		return "Administrar Trabajos Prácticos";
	}
	
	public Class useCaseModelClass() {
		return AdministrarTPsUseCaseModel.class;
	}
}
