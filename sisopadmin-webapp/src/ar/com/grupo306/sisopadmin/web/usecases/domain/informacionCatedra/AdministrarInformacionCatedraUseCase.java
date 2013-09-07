package ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra;

import ar.com.grupo306.usecasefwk.usecases.domain.base.BaseUseCase;

/**
 * Caso de uso de para Administrar Informaci�n de la C�tedra (CU 3.1.1) 
 *  
 * @author Damian
 */

public class AdministrarInformacionCatedraUseCase  extends BaseUseCase{
	
	public Class useCaseModelClass() {
		return AdministrarInformacionCatedraUseCaseModel.class;
	}

	public boolean isBaseUseCase() {
		return false;
	}

	public String getShortDescription() {
		return "Administrar Informaci�n de la C�tedra";
	}

	public String getLongDescription() {
		return "Permite Administrar la Informaci�n de la C�tedra";
	}
}
