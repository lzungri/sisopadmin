/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.tps;

import ar.com.grupo306.usecasefwk.usecases.domain.base.BaseUseCase;

/**
 * @author Sole
 *
 */
public class VerInformacionTPUseCase extends BaseUseCase {
	
	public String getLongDescription() {		
		return "Ver la informaci�n del Trabajo Pr�ctico";
	}

	public String getShortDescription() {
		return "Ver Informaci�n del TP";
	}
	
	public Class useCaseModelClass() {
		return VerInformacionTPUseCaseModel.class;
	}

	@Override
	public boolean isVisibleOnMenu() {
		return false;
	}

}
