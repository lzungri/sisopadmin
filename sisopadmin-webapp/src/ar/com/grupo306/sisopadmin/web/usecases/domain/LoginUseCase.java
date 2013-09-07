package ar.com.grupo306.sisopadmin.web.usecases.domain;

import java.util.List;

import ar.com.grupo306.usecasefwk.constants.UseCaseFWKConstants;
import ar.com.grupo306.usecasefwk.usecases.domain.base.BaseUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.ViewMode;

/**
 * Caso de uso de Login de la aplicación de reservas.
 *
 * @author Leandro
 */
public class LoginUseCase extends BaseUseCase {

	public int auditLevel() {
		return UseCaseFWKConstants.AUDIT_LEVEL_HIGH;
	}

	public Class useCaseModelClass() {
		return LoginUseCaseModel.class;
	}

	public String getShortDescription() {
		return "Login";
	}

	public String getLongDescription() {
		return "Mediante esta función el usuario podrá loguearse en la aplicación.";
	}

	public boolean isVisibleOnMenu() {
		return false;
	}

	public void availableModes(List<UseCaseModelMode> availableModes) {
		availableModes.add(new EditMode());
		availableModes.add(new ViewMode());
	}
	
	
}
