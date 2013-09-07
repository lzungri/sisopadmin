package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion;

import ar.com.grupo306.usecasefwk.usecases.domain.base.AdminBaseUseCase;

/**
 * CU para gestionar los informes generados ante una evaluaci�n de fase.
 *
 * @author Leandro
 */
public class AdministrarInformesUseCase extends AdminBaseUseCase {

	public String getLongDescription() {
		return "Permite realizar la gesti�n de los informes generados ante una evaluaci�n de fase.";
	}

	public String getShortDescription() {
		return "Administrar informes de evaluaci�n";
	}

	public Class useCaseModelClass() {
		return AdministrarInformesUseCaseModel.class;
	}

}