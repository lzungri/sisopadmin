/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.grupos;

import ar.com.grupo306.usecasefwk.usecases.domain.base.AdminBaseUseCase;

/**
 * @author Sole
 *
 */
public class AdministrarGruposUseCase extends AdminBaseUseCase {	
	public String getLongDescription() {		
		return "Administración de Grupos";
	}
	
	public String getShortDescription() {
		return "Administrar Grupos";
	}
	
	public Class useCaseModelClass() {
		return AdministrarGruposUseCaseModel.class;
	}

}
