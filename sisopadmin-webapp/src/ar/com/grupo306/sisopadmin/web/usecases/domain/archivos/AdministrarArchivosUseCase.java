package ar.com.grupo306.sisopadmin.web.usecases.domain.archivos;

import ar.com.grupo306.usecasefwk.usecases.domain.base.AdminBaseUseCase;

/**
 * Caso de uso de para Administrar Archivos de la C�tedra. 
 *  
 * @author Damian
 */

public class AdministrarArchivosUseCase  extends AdminBaseUseCase{
	
	public Class useCaseModelClass() {
		return AdministrarArchivosUseCaseModel.class;
	}

	public boolean isBaseUseCase() {
		return false;
	}

	public String getShortDescription() {
		return "Archivos de la C�tedra";
	}

	public String getLongDescription() {
		return "Permite administrar los Archivos de la C�tedra";
	}
}
