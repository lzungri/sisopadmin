package ar.com.grupo306.sisopadmin.web.usecases.domain;

import ar.com.grupo306.usecasefwk.usecases.domain.base.BaseUseCase;

/**
 * Caso de uso inicial de la aplicación.
 *
 * @author Leandro
 */
public class HomeUseCase extends BaseUseCase {

	public Class useCaseModelClass() {
		return HomeUseCaseModel.class;
	}

	public boolean isBaseUseCase() {
		return true;
	}
	
	public boolean isVisibleOnMenu() {
		return false;
	}

	public String getShortDescription() {
		return "Página de inicio";
	}

	public String getLongDescription() {
		return "Acceder a la pantalla principal de Sisop Admin.";
	}

}