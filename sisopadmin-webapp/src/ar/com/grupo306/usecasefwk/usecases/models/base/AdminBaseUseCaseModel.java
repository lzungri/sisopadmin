package ar.com.grupo306.usecasefwk.usecases.models.base;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.Predicate;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
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
 * Modelo base para los casos de uso de adminitración de entidades.
 *
 * @author Leandro
 */
public abstract class AdminBaseUseCaseModel extends BaseUseCaseModel {
	public static String SEARCH_FILTER_KEY = "useCaseFWK_searchFilter";
	
	// Entidades a presentar en pantalla.
	private List<? extends ModelObject> entities = CollectionFactory.createList(ModelObject.class);
	
	// Entidades seleccionadas por el usuario.
	private List<? extends ModelObject> selectedEntities = CollectionFactory.createList(ModelObject.class);
	
	// Indices de las entidades seleccionadas.
	private String[] index;
	
	private Predicate searchFilter;
	
	
	
	/**
	 * ABM asociado a la entidad.
	 */
	protected abstract Class<? extends ABMBaseUseCase> abmEntityUseCase();
	
	/**
	 * Clase de la entidad a administrar.
	 */
	protected abstract Class<? extends ModelObject> entityClass();
	
	/**
	 * Método que efectivamente realiza la búsqueda de las entidades.
	 * Si se quiere aplicar seguridad se debe sobreescribir el find().
	 * 
	 * @param useCaseContext
	 */
	protected abstract List<? extends ModelObject> doFind(UseCaseContext useCaseContext);
	
	/**
	 * Indica si el UseCase debe popularse al iniciar.
	 * Por defecto false. 
	 */
	protected boolean prePopulateUseCase() {
		return false;
	}
	
	/**
	 * Define el filtro que se aplicará a cada búsqueda.
	 * Por defecto no se aplica ninguno, es decir, se mostrarán todas las entidades 
	 * resultado de la búsqueda.
	 * 
	 * @param loggedUser: Usuario logueado o null en caso de no estarlo.
	 */
	protected Predicate searchFilter(final Usuario loggedUser) {
		return null;
	}
	
	/**
	 * Realiza la búsqueda de las entidades al inicio del UseCase.
	 * Sólo se realiza si prePopulateUseCase == true.
	 * 
	 * @param useCaseContext
	 */
	protected List<ModelObject> doFindOnInit(UseCaseContext useCaseContext) {
		// Por defecto busca todas las entidades existentes.
		return (List<ModelObject>) SisopAdminServiceProvider.getPersistenceService().findAll(this.entityClass());
	}
	
	/**
	 * Realiza la búsqueda de las entidades seleccionadas por el usuario.
	 * 
	 * La búsqueda por defecto se realizará en base al index de las entidades, esto es,
	 * la posición que ocupan en la lista presentada al usuario.
	 * 
	 * Es conveniente usar esto por una cuestión de seguridad (evitar el uso de ids).
	 * 
	 * @param useCaseContext
	 */
	protected List<? extends ModelObject> findEntitys(UseCaseContext useCaseContext) {
		return findEntitys(useCaseContext, this.entities, "useCaseModel.index", this.index);
	}
	
	

	
	@Transactional
	public void initUseCase(UseCaseContext useCaseContext) {
		super.initUseCase(useCaseContext);
		
		searchFilter = (Predicate) useCaseContext.getParameter(SEARCH_FILTER_KEY);
		// Si no se indica el filtro, se carga uno por defecto.
		if(searchFilter == null) {
			Usuario usuario = null;
			if(useCaseContext.getUserContext().isUserLogged()) {
				usuario = Usuario.findMeByLoginName(((LoggedUserContext) useCaseContext.getUserContext()).getUserLoginName());
			}
			searchFilter = searchFilter(usuario);
		}
		
		if(this.prePopulateUseCase()) {
			this.entities = this.doFindOnInit(useCaseContext);
			doFilter(entities);
		}
	}

	/**
	 * Método que deberá ser invocado desde el jsp para realizar la búsqueda.
	 * 
	 * @param useCaseContext
	 */
	@Transactional
	public void find(UseCaseContext useCaseContext) {
		this.entities = this.doFind(useCaseContext);
		doFilter(entities);
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
	 * Se dirige al CU de ABM enviando como parámetro el elemento seleccionado.
	 * 
	 * @param useCaseContext
	 * @param mode
	 */
	private void goToABMEntityUseCase(UseCaseContext useCaseContext, UseCaseModelMode mode) {
		Map parameters = CollectionFactory.createMap();
		parameters.put(ABMBaseUseCaseModel.ENTITY_KEY, getSelectedEntity(useCaseContext));		
		
		useCaseContext.goToChildUseCase(this.abmEntityUseCase(), mode, parameters);
	}
	
	/**
	 * Retorna la entidad seleccionada por el usuario.
	 * En caso de no haber seleccionado se arroja una BussinessException.
	 * 
	 * @param useCaseContext
	 */
	protected ModelObject getSelectedEntity(UseCaseContext useCaseContext) {
		List<? extends ModelObject> selectedEntities = this.findEntitys(useCaseContext);
		if(selectedEntities.isEmpty()) {
			throw ExceptionFactory.createBusinessException("Debe seleccionar al menos un elemento");
		}
		
		return selectedEntities.iterator().next();
	}
	
	/**
	 * El método sólo podrá ser ejecutado en el SelectionMode, por eso es seguro
	 * para el OperationMode, ya que nadie debería tener ese permiso.
	 * 
	 * @param useCaseContext
	 */
	@Secure ({OperationMode.class})
	@Description ("Permiso para seleccionar elementos.")
	public void select(UseCaseContext useCaseContext) {
		this.selectedEntities = this.findEntitys(useCaseContext);
		if(this.selectedEntities.size() > ((SelectionMode) getMode()).getMaxSelection() || this.selectedEntities.isEmpty()) {
			throw ExceptionFactory.createBusinessException("La cantidad de elementos seleccionados no es la correcta.");
		}
		
		useCaseContext.acceptUseCase();
	}
	
	/**
	 * Modifica la lista conservando aquellos elementos que satisfacen el filtro.
	 * 
	 * @param entities
	 */
	private void doFilter(List<? extends ModelObject> entities) {
		if(searchFilter != null) {
			for(Iterator<? extends ModelObject> it = entities.iterator(); it.hasNext(); ) {
				if(!searchFilter.evaluate(it.next()))
					it.remove();
			}
		}
	}
	
	public List<? extends ModelObject> getEntities() {
		return entities;
	}

	public void setEntities(List<? extends ModelObject> entities) {
		this.entities = entities;
	}

	public String[] getIndex() {
		return index;
	}

	public void setIndex(String[] index) {
		this.index = index;
	}

	public List<? extends ModelObject> getSelectedEntities() {
		return selectedEntities;
	}

	public void setSelectedEntities(List<? extends ModelObject> selectedEntities) {
		this.selectedEntities = selectedEntities;
	}

	public Predicate getSearchFilter() {
		return searchFilter;
	}

	public void setSearchFilter(Predicate filter) {
		this.searchFilter = filter;
	}

}