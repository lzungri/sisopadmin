package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas;

import ar.com.grupo306.usecasefwk.usecases.domain.base.AdminBaseUseCase;

/**
 * CU para la gestión de entregas de grupo.
 *
 * @author Leandro
 */
public class AdministrarEntregasUseCase extends AdminBaseUseCase {

	public String getLongDescription() {
		return "Permite gestionar las entregas realizadas por los grupos";
	}

	public String getShortDescription() {
		return "Administrar entregas";
	}

	public Class useCaseModelClass() {
		return AdministrarEntregasUseCaseModel.class;
	}

}