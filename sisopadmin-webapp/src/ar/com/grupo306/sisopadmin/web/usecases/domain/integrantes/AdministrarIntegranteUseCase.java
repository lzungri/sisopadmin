package ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes;

import ar.com.grupo306.usecasefwk.usecases.domain.base.AdminBaseUseCase;

public class AdministrarIntegranteUseCase extends AdminBaseUseCase{

	public String getLongDescription() {
		return "Permite realizar la administración de los integrantes de la cátedra";
	}

	public String getShortDescription() {
		return "Administración de Integrante de la Cátedra";
	}

	public Class useCaseModelClass() {
		return AdministrarIntegranteUseCaseModel.class;
	}
	
	
}
