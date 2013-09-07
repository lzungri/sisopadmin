/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes;

import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;

/**
 * @author Sole
 *
 */
public class AsignarGruposAIntegranteUseCase extends ABMBaseUseCase {
	@Override
	public boolean isVisibleOnMenu() {
		return false;
	}

	public String getLongDescription() {
		return "Asignaci�n de los grupos que tiene un Integrante de la c�tedra, manualmente";
	}
	
	public String getShortDescription() {
		return "Asigna manualmente grupos a Integrante de la c�tedra";
	}

	public Class useCaseModelClass() {
		return AsignarGruposAIntegranteUseCaseModel.class;
	}

}
