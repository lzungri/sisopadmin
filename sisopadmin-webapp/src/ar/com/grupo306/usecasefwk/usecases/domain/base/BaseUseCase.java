package ar.com.grupo306.usecasefwk.usecases.domain.base;

import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;

import ar.com.grupo306.usecasefwk.constants.UseCaseFWKConstants;
import ar.com.grupo306.usecasefwk.struts.actions.UseCaseAction;
import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;

/**
 * Establece una configuración por defecto para los casos de uso.
 * 
 * @author Leandro
 */
public abstract class BaseUseCase extends UseCaseAction {

	public String tilesDefinitionKey() {
		// Por defecto se utiliza el nombre corto de la clase. No el largo, por un 
		// aspecto de seguridad (ya que se visualiza en la URL).
		return StringUtils.uncapitalize(ClassUtils.getShortClassName(this.getClass()));
	}

	public String useCaseForm() {
		// Por defecto "useCaseForm".
		return "useCaseForm";
	}

	public boolean isBaseUseCase() {
		return false;
	}
	
	public boolean isVisibleOnMenu() {
		return true;
	}
	
	public int auditLevel() {
		return UseCaseFWKConstants.AUDIT_LEVEL_NULL;
	}

	public boolean securityEnabled() {
		return true;
	}
	
	public void availableModes(List<UseCaseModelMode> availableModes) {
		// Por defecto, ninguno.
	}
	
	/**
	 * Dos casos de uso serán iguales cuando sean de la misma clase.
	 *  ??????????????????
	 * 
	 * (Se podría usar el objeto Class en lugar de instancias de la clase).
	 */
	public boolean equals(Object object) {
		return this.getClass().equals(object.getClass());
	}

	public int hashCode() {
		return this.getClass().hashCode();
	}
	
	public String toString() {
		// TODO HACER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		return this.getClass().getName();
	}	

}