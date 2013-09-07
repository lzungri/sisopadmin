package ar.com.grupo306.sisopadmin.web.usecases.domain.error;

import ar.com.grupo306.usecasefwk.usecases.domain.base.BaseUseCase;

/**
 * Caso de uso para presentar los errores en pantalla.
 *
 * @author Leandro
 */
public class ErrorUseCase extends BaseUseCase {

	public Class useCaseModelClass() {
		return ErrorUseCaseModel.class;
	}

	public String getShortDescription() {
		return "Caso de uso de error";
	}

	public String getLongDescription() {
		return "Presenta los errores en pantalla";
	}

	public boolean isVisibleOnMenu() {
		return false;
	}
	
}