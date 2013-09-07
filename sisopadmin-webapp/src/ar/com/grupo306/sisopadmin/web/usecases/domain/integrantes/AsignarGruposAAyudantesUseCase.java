package ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes;

import ar.com.grupo306.usecasefwk.usecases.domain.base.BaseUseCase;

/**
 * @author Pablo
 */
public class AsignarGruposAAyudantesUseCase extends BaseUseCase {

	public Class useCaseModelClass() {
		return AsignarGruposAAyudantesUseCaseModel.class;
	}

	public String getShortDescription() {
		return "Asignar Grupos a Ayudantes";
	}

	public String getLongDescription() {
		return "Asignación automática de grupos a ayudantes";
	}
	
}