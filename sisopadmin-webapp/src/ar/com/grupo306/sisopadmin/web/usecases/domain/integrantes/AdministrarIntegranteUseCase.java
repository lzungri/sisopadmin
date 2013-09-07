package ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes;

import ar.com.grupo306.usecasefwk.usecases.domain.base.AdminBaseUseCase;

public class AdministrarIntegranteUseCase extends AdminBaseUseCase{

	public String getLongDescription() {
		return "Permite realizar la administraci�n de los integrantes de la c�tedra";
	}

	public String getShortDescription() {
		return "Administraci�n de Integrante de la C�tedra";
	}

	public Class useCaseModelClass() {
		return AdministrarIntegranteUseCaseModel.class;
	}
	
	
}
