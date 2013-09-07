package ar.com.grupo306.usecasefwk.usecases.models.base;

import java.util.List;

import org.apache.log4j.Logger;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
import ar.com.grupo306.sisopadmin.web.context.NotLoggedUserContext;
import ar.com.grupo306.sisopadmin.web.context.UserContext;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.base.UseCase;
import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;
import ar.com.grupo306.usecasefwk.usecases.stack.UseCaseStack;
import ar.com.grupo306.usecasefwk.usecases.stack.elements.UseCaseElement;

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
		// Por defecto, nada.
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
			context.setUserContext(NotLoggedUserContext.getInstance());
			
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
	
	
	
	public String toString() {
		// TODO HACER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		return this.getClass().getName();
	}
	
}