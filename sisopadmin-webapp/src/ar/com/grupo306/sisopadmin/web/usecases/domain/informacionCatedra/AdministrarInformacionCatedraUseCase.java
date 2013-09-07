package ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra;

import ar.com.grupo306.usecasefwk.usecases.domain.base.BaseUseCase;

/**
 * Caso de uso de para Administrar Información de la Cátedra (CU 3.1.1) 
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
		return "Administrar Información de la Cátedra";
	}

	public String getLongDescription() {
		return "Permite Administrar la Información de la Cátedra";
	}
}
