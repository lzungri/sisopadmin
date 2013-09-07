/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.tps;

import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;

/**
 * @author Sole
 *
 */
public class ABMFaseUseCase extends ABMBaseUseCase {

	public String getLongDescription() {		
		return "Permite Cargar, Visualizar, Modificar y Eliminar Trabajos Prácticos ";
	}

	public String getShortDescription() {
		return "ABM de Fases de TP" ;
	}

	public Class useCaseModelClass() {		
		return ABMFaseUseCaseModel.class;
	}

	public boolean isVisibleOnMenu() {		
		return false;
	}

}
