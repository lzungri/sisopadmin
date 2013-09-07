package ar.com.grupo306.sisopadmin.web.usecases.domain.archivos;

import ar.com.grupo306.usecasefwk.usecases.domain.base.BaseUseCase;

/**
 * Caso de uso de para Cargar(CU 3.2.2) y  Eliminar(CU 3.2.3) Archivos
 * *
 * @author Damian
 */

public class ABMArchivosUseCase  extends BaseUseCase{
	
	public Class useCaseModelClass() {
		return ABMArchivosUseCaseModel.class;
	}

	public boolean isBaseUseCase() {
		return false;
	}

	public String getShortDescription() {
		return "ABM de Archivos";
	}

	public String getLongDescription() {
		return "Permite Cargar y Eliminar Archivos";
	}
}
