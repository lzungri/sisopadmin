package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion;

import ar.com.grupo306.usecasefwk.usecases.domain.base.AdminBaseUseCase;

/**
 * CU para gestionar los informes generados ante una evaluación de fase.
 *
 * @author Leandro
 */
public class AdministrarInformesUseCase extends AdminBaseUseCase {

	public String getLongDescription() {
		return "Permite realizar la gestión de los informes generados ante una evaluación de fase.";
	}

	public String getShortDescription() {
		return "Administrar informes de evaluación";
	}

	public Class useCaseModelClass() {
		return AdministrarInformesUseCaseModel.class;
	}

}