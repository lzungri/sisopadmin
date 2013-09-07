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
		return "Administraci�n de Trabajos Pr�cticos";
	}
	
	public String getShortDescription() {		
		return "Administrar Trabajos Pr�cticos";
	}
	
	public Class useCaseModelClass() {
		return AdministrarTPsUseCaseModel.class;
	}
}
