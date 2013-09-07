/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.grupos;

import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;

/**
 * @author Sole
 *
 */
public class ABMGruposUseCase extends ABMBaseUseCase {

	/* (non-Javadoc)
	 * @see ar.com.grupo306.usecasefwk.usecases.domain.base.UseCase#getLongDescription()
	 */
	public String getLongDescription() {
		return "Dar de Alta, modificar, visualizar y eliminar Grupos";
	}

	/* (non-Javadoc)
	 * Solo va a ser accesible desde el menú para que los alumnos creen su grupo
	 */
	public String getShortDescription() {
		return "Crear Grupo";
	}
	
	public Class useCaseModelClass() {
		return ABMGruposUseCaseModel.class;
	}

}
