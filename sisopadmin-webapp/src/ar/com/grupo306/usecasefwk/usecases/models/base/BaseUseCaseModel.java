package ar.com.grupo306.usecasefwk.usecases.models.base;

import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.sisopadmin.config.Module;
import ar.com.grupo306.sisopadmin.config.SisopAdminConfig;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
import ar.com.grupo306.sisopadmin.web.context.NotLoggedUserContext;
import ar.com.grupo306.sisopadmin.web.context.UserContext;
import ar.com.grupo306.sisopadmin.web.usecases.domain.HomeUseCase;
import ar.com.grupo306.usecasefwk.constants.UseCaseFWKConstants;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.base.UseCase;
import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;
import ar.com.grupo306.usecasefwk.usecases.stack.UseCaseStack;
import ar.com.grupo306.usecasefwk.usecases.stack.elements.UseCaseElement;
import ar.com.grupo306.usecasefwk.usecases.stack.elements.UseCaseParametersElement;

/**
 * Implementación básica de un UseCaseModel.
 * 
 * @author lzungri
 */
public abstract class BaseUseCaseModel implements UseCaseModel {
	protected static Logger logger = Logger.getLogger(BaseUseCaseModel.class);
	
	/** UseCase actual del UseCaseModel. */
	private UseCase useCase;
	
	/** Modo actual asociado al UseCaseModel. */
	private UseCaseModelMode mode;
	

	
	public void initUseCase(UseCaseContext context) {
		UseCaseStack stack = context.getUseCaseStack();
		
		UseCaseElement useCaseElement = new UseCaseElement();

		if(stack.hasUseCaseParameters()) {
			useCaseElement.setParameters( ((UseCaseParametersElement) stack.pop()).getParameters() );
			
			// Si el UseCase es base, entonces se limpia el stack. Esto para evitar que casos de uso
			// que no sean base persistan en la sesión porque tienen por encima a uno base.
			if(this.useCase.isBaseUseCase()) {
				stack.clearAllPopupeables();
			}
		}
		else {
			// Se vacía la pila ya que la navegación ó representa el primer caso de uso 
			// del usuario ó se accedió a un nuevo workflow.
			// Esto significa que si existían CU previos no deben considerárselos.
			stack.clearAllPopupeables();
			useCaseElement.setParameters(CollectionFactory.createMap());
		}
		
		// Se configura si el CU es base para la sesión del usuario.
		useCaseElement.setCanBePopupeable(!this.useCase.isBaseUseCase());
		useCaseElement.setUseCase(this.useCase.getClass());
		useCaseElement.setUseCaseModel((UseCaseModel) context.getElement(UseCaseFWKConstants.USECASEMODEL_PROXIED_KEY));
		
		// Previo a apilar el CU se valida el tamaño máximo de la pila.
		if(stack.size() > new Integer(SisopAdminConfig.getInstance().getProperty(Module.COMMON, "usecasefwk.stack.maxsize"))) {
			stack.clearAllExceptFirst();
		}

		stack.push(useCaseElement);
	}
	
	public void acceptUseCase(UseCaseContext context) {
		UseCaseStack stack = context.getUseCaseStack();

		// Se extrae al CU hijo. Luego el garbage hará lo suyo.
		UseCaseElement childElement = (UseCaseElement) stack.pop();
		
		if(!stack.isEmpty()) {
			// Ahora la pila apunta al caso de uso padre.
			UseCaseElement parentElement = (UseCaseElement) stack.viewStackElement();
			parentElement.setReturnedUseCaseModel(childElement.getUseCaseModel());
			String returnMethod = childElement.getReturnMethod() != null ? childElement.getReturnMethod() : UseCaseModel.RETURN_FROM_CHILD_DEFAULT_METHOD; 
			
			context.executeReturnMethod(parentElement.getUseCase(), returnMethod);
		}
		else {
			logger.info("El usuario finalizo el caso de uso de aplicacion.");
			context.closeApplication();
		}
	}
	
	public void cancelUseCase(UseCaseContext context) {
		UseCaseStack stack = context.getUseCaseStack();
		
		// Se extrae al CU hijo. Luego el garbage hará lo suyo.
		UseCaseElement childElement = (UseCaseElement) stack.pop();
		
		if(!stack.isEmpty()) {
			// Ahora la pila apunta al caso de uso padre.
			UseCaseElement parentElement = (UseCaseElement) stack.viewStackElement();
			parentElement.setReturnedUseCaseModel(null);
			String returnMethod = childElement.getReturnMethod() != null ? childElement.getReturnMethod() : UseCaseModel.RETURN_FROM_CHILD_DEFAULT_METHOD;
			
			context.executeReturnMethod(parentElement.getUseCase(), returnMethod);
		}
		else {
			logger.info("El usuario finalizo el caso de uso de aplicacion.");
			context.closeApplication();
		}		
	}
	
	
	
	public void returnFromChildDefaultMethod(UseCaseContext context) {
		// Por defecto... nada.
	}
	
	
	
	/**
	 * La operación de deslogueo es común a todos los UseCaseModels, por lo tanto
	 * se la generaliza.
	 * 
	 * @param context
	 */
	public void logout(UseCaseContext context) {
		UserContext userContext = context.getUserContext();
		if(userContext.isUserLogged()) {
			// Cookie para el logout en el foro
			Cookie cookie = new Cookie("SISOP_ADMIN_FORO", "");
			cookie.setMaxAge(0);
			cookie.setPath("/");
			context.addCookie(cookie);

			context.setUserContext(NotLoggedUserContext.getInstance());
			
			// Al desloguearse se dirige al Caso de uso principal.
			context.goToChildUseCase(HomeUseCase.class);
			
			context.addMessage("El usuario '" + ((LoggedUserContext) userContext).getUserLoginName() + "' ha finalizado su sesión.");
		}
	}
	
	
	
	public UseCase getUseCase() {
		return useCase;
	}

	public void setUseCase(UseCase useCase) {
		this.useCase = useCase;
	}

	public UseCaseModelMode getMode() {
		return mode;
	}

	public void setMode(UseCaseModelMode mode) {
		List<UseCaseModelMode> modes = CollectionFactory.createList(UseCaseModelMode.class);
		this.useCase.availableModes(modes);
		
		// Si el modo es null y se definieron modos ó si el modo es !- de null pero no está
		// entre los availableModes entonces se arroja una ProgramException.
		if((mode == null && !modes.isEmpty()) || (mode != null && !modes.contains(mode))) {
			throw ExceptionFactory.createProgramException("El modo: " + mode + " no es valido para el UseCaseModel: " + this);
		}
		this.mode = mode;
	}
	
	public void initMode() {
		// Inicia el modo por defecto, para ello toma el primero de los availableModes
		// o en su defecto null.
		List<UseCaseModelMode> modes = CollectionFactory.createList(UseCaseModelMode.class);
		this.useCase.availableModes(modes);
		
		this.mode = modes.isEmpty() ? null : modes.iterator().next() ;
	}
	
	/**
	 * Retorna una Lista de ModelObject que resulta de filtrar la lista de entities en base a aquellas
	 * seleccionadas por el usuario.
	 * 
	 * @param useCaseContext
	 * @param entities: Lista total de entidades.
	 * @param requestParameter: Parámetro del request donde se obtiene la selección del usuario. 
	 * @param indexes: Variable donde se almacenan los indices seleccionados por el usuario.
	 */
	protected List<? extends ModelObject> findEntitys(UseCaseContext useCaseContext, List<? extends ModelObject> entities, String requestParameter, String[] indexes) {
		List<ModelObject> selectedEntities = CollectionFactory.createList(ModelObject.class);
		
		// Cuando se destilda un checkbox, el browser no envía dicho evento en el request
		// entonces es necesario consultar si están todos destildados.
		if(useCaseContext.getRequestParameter(requestParameter) == null) 
			indexes = null;
		
		try {
			if(indexes != null) {
				for(String indexEntity : indexes) {
					selectedEntities.add(entities.get(new Integer(indexEntity)));
				}
			}
		}
		catch(IndexOutOfBoundsException excep) {
			throw ExceptionFactory.createBusinessException("Ha intentado seleccionar un elemento inexistente.");
		}
		catch(NumberFormatException excep) {
			throw ExceptionFactory.createBusinessException("Ha intentado seleccionar un elemento inexistente.");
		}
		
		return selectedEntities;
	}
	
	/**
	 * Dada una List de entidades, retorna la indicada por la posición selectedIndex.
	 * En caso de no existir tal posición se informa mediante una excepción.
	 * 
	 * @param entities
	 * @param selectedIndex
	 */
	public <T extends Object> T findEntity(List<T> entities, Integer selectedIndex) {
		try {
			return entities.get(selectedIndex);
		}
		catch(IndexOutOfBoundsException excep) {
			throw ExceptionFactory.createBusinessException("Ha intentado operar sobre un elemento inexistente.");
		}
	}
	
	
	
	public String toString() {
		// TODO HACER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		return this.getClass().getName();
	}
	
}