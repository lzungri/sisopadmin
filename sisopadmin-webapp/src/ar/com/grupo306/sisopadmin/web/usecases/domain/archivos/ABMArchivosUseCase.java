package ar.com.grupo306.sisopadmin.web.usecases.domain.archivos;

import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;

/**
 * Caso de uso de para Cargar(CU 3.2.2) y  Eliminar(CU 3.2.3) Archivos
 * *
 * @author Damian
 */

public class ABMArchivosUseCase  extends ABMBaseUseCase{
	
	public Class useCaseModelClass() {
		return ABMArchivosUseCaseModel.class;
	}

	public boolean isBaseUseCase() {
		return false;
	}
	
	@Override
	public boolean isVisibleOnMenu() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getShortDescription() {
		return "ABM de Archivos";
	}

	public String getLongDescription() {
		return "Permite Cargar y Eliminar Archivos";
	}
}
