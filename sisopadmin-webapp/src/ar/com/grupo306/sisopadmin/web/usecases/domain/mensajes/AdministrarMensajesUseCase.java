package ar.com.grupo306.sisopadmin.web.usecases.domain.mensajes;

import ar.com.grupo306.usecasefwk.usecases.domain.base.AdminBaseUseCase;

/**
 * @author Pablo
 */
public class AdministrarMensajesUseCase extends AdminBaseUseCase {

	public Class useCaseModelClass() {
		return AdministrarMensajesUseCaseModel.class;
	}

	public String getShortDescription() {
		return "Administrar Mensajes";
	}

	public String getLongDescription() {
		return "Comunicación entre usuarios";
	}
	
}