package ar.com.grupo306.usecasefwk.usecases.models.base;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.RemoveMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.ViewMode;


/**
 * Modelo base para los UseCaseModel de alta, baja y modificación de entidades.
 *
 * @author Leandro
 */
public abstract class ABMBaseUseCaseModel extends BaseUseCaseModel {
	/** Key donde se almacena el ModelObject utilizado como base para el ABM. */
	public static String ENTITY_KEY = "useCaseFWK_entity";
	
	/**
	 * Obtiene, en caso de existir, el ModelObject enviado desde el Caso de uso
	 * padre, es decir, aquél que lo invocó.
	 */
	@Secure ({CreateMode.class, EditMode.class, ViewMode.class, RemoveMode.class})
	@Description ("Inicio de caso de uso")
	@Transactional
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);
		
		ModelObject modelObject = (ModelObject) context.getParameter(ENTITY_KEY);
		
		if(modelObject != null) {
			// Por un tema de transacciones se trae nuevamente de la base. Es feo, pero muy efectivo.
			// Sólo se traerá si el modelObject tiene id.
			ModelObject modelObjectDB = null;
			if(modelObject.getId() != null) {
				modelObjectDB = SisopAdminServiceProvider.getPersistenceService().findInstanceById(modelObject.getClass(), modelObject.getId()); 
			}
			
			if(modelObjectDB != null) 
				this.processModelObject(context, modelObjectDB);
			else
				this.processModelObject(context, modelObject);
		}
	}
	
	/**
	 * Procesa el ModelObject envíado por parámetro desde el CU padre.
	 * Por defecto nada.
	 * 
	 * @param context
	 * @param modelObject
	 */
	protected void processModelObject(UseCaseContext context, ModelObject modelObject) {
		// Nada.
	}
	
}