package ar.com.grupo306.usecasefwk.usecases.models.base;

import java.util.List;
import java.util.Map;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.OperationMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.RemoveMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.SelectionMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.ViewMode;


/**
 * Modelo base para los casos de uso de adminitraci�n de entidades.
 *
 * @author Leandro
 */
public abstract class AdminBaseUseCaseModel extends BaseUseCaseModel {
	// Entidades a presentar en pantalla.
	private List<ModelObject> entities = CollectionFactory.createList(ModelObject.class);
	
	// Entidades seleccionadas por el usuario.
	private List<ModelObject> selectedEntities = CollectionFactory.createList(ModelObject.class);
	
	// Indices de las entidades seleccionadas.
	private String[] index;
	
	
	
	/**
	 * ABM asociado a la entidad.
	 */
	protected abstract Class<? extends ABMBaseUseCase> abmEntityUseCase();
	
	/**
	 * Clase de la entidad a administrar.
	 */
	protected abstract Class<? extends ModelObject> entityClass();
	
	/**
	 * M�todo que efectivamente realiza la b�squeda de las entidades.
	 * Si se quiere aplicar seguridad se debe sobreescribir el find().
	 * 
	 * @param useCaseContext
	 */
	protected abstract List<ModelObject> doFind(UseCaseContext useCaseContext);
	
	/**
	 * Indica si el UseCase debe popularse al iniciar.
	 * Por defecto false. 
	 */
	protected boolean prePopulateUseCase() {
		return false;
	}
	
	/**
	 * Realiza la b�squeda de las entidades al inicio del UseCase.
	 * S�lo se realiza si prePopulateUseCase == true.
	 * 
	 * @param useCaseContext
	 */
	protected List<ModelObject> doFindOnInit(UseCaseContext useCaseContext) {
		// Por defecto busca todas las entidades existentes.
		return (List<ModelObject>) SisopAdminServiceProvider.getPersistenceService().findAll(this.entityClass());
	}
	
	/**
	 * Realiza la b�squeda de las entidades seleccionadas por el usuario.
	 * 
	 * La b�squeda por defecto se realizar� en base al index de las entidades, esto es,
	 * la posici�n que ocupan en la lista presentada al usuario.
	 * 
	 * Es conveniente usar esto por una cuesti�n de seguridad (evitar el uso de ids).
	 * 
	 * @param useCaseContext
	 */
	protected List<ModelObject> findEntitys(UseCaseContext useCaseContext) {
		List<ModelObject> selectedEntities = CollectionFactory.createList(ModelObject.class);
		
		// Cuando se destilda un checkbox, el browser no env�a dicho evento en el request
		// entonces es necesario consultar si est�n todos destildados.
		if(useCaseContext.getRequestParameter("useCaseModel.index") == null) 
			this.index = null;
		
		try {
			if(this.index != null) {
				for(String indexEntity : this.index) {
					selectedEntities.add(this.entities.get(new Integer(indexEntity)));
				}
			}
		}
		catch(IndexOutOfBoundsException excep) {
			throw ExceptionFactory.createBusinessException("Ha intentado seleccionar un elemento inexistente.");
		}
		
		return selectedEntities;
	}
	
	
	
	@Transactional
	public void initUseCase(UseCaseContext useCaseContext) {
		super.initUseCase(useCaseContext);
		
		if(this.prePopulateUseCase()) {
			this.entities = this.doFindOnInit(useCaseContext);
		}
	}

	/**
	 * M�todo que deber� ser invocado desde el jsp para realizar la b�squeda.
	 * 
	 * @param useCaseContext
	 */
	@Transactional
	public void find(UseCaseContext useCaseContext) {
		this.entities = this.doFind(useCaseContext);
	}

	public void create(UseCaseContext useCaseContext) {
		useCaseContext.goToChildUseCase(this.abmEntityUseCase(),new  CreateMode());
	}
	
	public void view(UseCaseContext useCaseContext) {
		this.goToABMEntityUseCase(useCaseContext, new ViewMode());
	}
	
	public void edit(UseCaseContext useCaseContext) {
		this.goToABMEntityUseCase(useCaseContext, new EditMode());
	}
		
	public void remove(UseCaseContext useCaseContext) {
		this.goToABMEntityUseCase(useCaseContext, new RemoveMode());
	}
	
	/**
	 * Se dirige al CU de ABM enviando como par�metro el elemento seleccionado.
	 * 
	 * @param useCaseContext
	 * @param mode
	 */
	private void goToABMEntityUseCase(UseCaseContext useCaseContext, UseCaseModelMode mode) {
		List<ModelObject> selectedEntities = this.findEntitys(useCaseContext);
		if(selectedEntities.isEmpty()) {
			throw ExceptionFactory.createBusinessException("Debe seleccionar al menos un elemento");
		}

		Map parameters = CollectionFactory.createMap();
		parameters.put(ABMBaseUseCaseModel.ENTITY_KEY, selectedEntities.iterator().next());		
		
		useCaseContext.goToChildUseCase(this.abmEntityUseCase(), mode, parameters);
	}
	
	/**
	 * El m�todo s�lo podr� ser ejecutado en el SelectionMode, por eso es seguro
	 * para el OperationMode, ya que nadie deber�a tener ese permiso.
	 * 
	 * @param useCaseContext
	 */
	@Secure ({OperationMode.class})
	@Description ("Permiso para seleccionar elementos.")
	public void select(UseCaseContext useCaseContext) {
		this.selectedEntities = this.findEntitys(useCaseContext);
		if(this.selectedEntities.size() > ((SelectionMode) getMode()).getMaxSelection()) {
			throw ExceptionFactory.createBusinessException("La cantidad de elementos seleccionados no es la correcta.");
		}
		
		useCaseContext.acceptUseCase();
	}
	
	
	
	public List<ModelObject> getEntities() {
		return entities;
	}

	public void setEntities(List<ModelObject> entities) {
		this.entities = entities;
	}

	public String[] getIndex() {
		return index;
	}

	public void setIndex(String[] index) {
		this.index = index;
	}

	public List<ModelObject> getSelectedEntities() {
		return selectedEntities;
	}

	public void setSelectedEntities(List<ModelObject> selectedEntities) {
		this.selectedEntities = selectedEntities;
	}

}