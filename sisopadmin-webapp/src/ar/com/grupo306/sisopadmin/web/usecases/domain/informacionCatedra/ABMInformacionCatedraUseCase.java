package ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra;

import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;

/**
 * Caso de uso de para Cargar(CU 3.1.2), Eliminar(CU 3.1.3) y Modificar(CU 3.1.4) 
 * Informaci�n de la C�tedra
 * 
 * @author Damian
 */

public class ABMInformacionCatedraUseCase  extends ABMBaseUseCase {
	
	public Class useCaseModelClass() {
		return ABMInformacionCatedraUseCaseModel.class;
	}

	public boolean isBaseUseCase() {
		return false;
	}

	public String getShortDescription() {
		return "ABM de Informaci�n de la C�tedra";
	}

	public String getLongDescription() {
		return "Permite Cargar, Visualizar, Modificar y Eliminar Informaci�n de la C�tedra";
	}
}
