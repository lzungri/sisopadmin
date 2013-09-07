package ar.com.grupo306.usecasefwk.struts.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.grupo306.commons.builder.MapBuilder;
import ar.com.grupo306.commons.exceptions.core.BusinessException;
import ar.com.grupo306.commons.exceptions.core.ProgramException;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;
import ar.com.grupo306.sisopadmin.web.constants.SisopAdminWebConstants;
import ar.com.grupo306.sisopadmin.web.context.UserContext;
import ar.com.grupo306.sisopadmin.web.usecases.domain.error.ErrorUseCase;
import ar.com.grupo306.usecasefwk.constants.UseCaseFWKConstants;
import ar.com.grupo306.usecasefwk.struts.forms.UseCaseForm;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.base.UseCase;
import ar.com.grupo306.usecasefwk.usecases.models.base.UseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;
import ar.com.grupo306.usecasefwk.usecases.stack.UseCaseStack;
import ar.com.grupo306.usecasefwk.usecases.stack.elements.UseCaseElement;
import ar.com.grupo306.usecasefwk.usecases.stack.elements.UseCaseParametersElement;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseUtils;

/**
 * Implementación de UseCase en Struts.
 * 
 * Clase base para la creación de Casos de uso en la aplicación de SisopAdmin.
 * 
 * Tiene como responsabilidad:
 * <li> Especificar las características de un caso de uso.<br>
 * Por ejemplo:
 * <br>Auditoría que se aplicará sobre el caso de uso.
 * <br>Seguridad que se aplicará sobre el caso de uso. 
 * <li> Abstraer a los Casos de uso concretos (UseCaseModel) del uso del request.
 * 
 * @author lzungri
 */
public abstract class UseCaseAction extends ModularizedDispatchAction  implements UseCase {
	protected static Logger logger = Logger.getLogger(UseCaseAction.class);

	

	/**
	 * Método principal que centraliza la ejecución de los dispatchMethod.
	 * Se redefine para agregar las siguiente responsabilidades:
	 * <li> Administrar el manejo de las excepciones.
	 */
	protected ActionForward invokeDispatchMethod(Method method, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Se wrapea los datos específicos del webserver y el struts.
		UseCaseContext useCaseContext = new UseCaseContext(mapping, request);
		
		try {
			
			this.resolveAndInvokeMethod(useCaseContext, method, form, request);
			
			// Si el action definió explicitamente un forward => se redirecciona la página
			// hacia él, en caso contrario se realiza un refresh.
			if(!useCaseContext.hasActionForward())
				useCaseContext.refreshUseCase();
			return  useCaseContext.getActionForward();
			
		}
		catch(BusinessException excep) {
			useCaseContext.addErrorMessage(excep.getMessage());
			useCaseContext.refreshUseCase();
			
			return useCaseContext.getActionForward();
		}
		catch(Exception excep) {
			// Al ser un error grave se anula la pila de UseCases.
			useCaseContext.getUseCaseStack().clearAllPopupeables();
			// Se dirige al ErrorUseCase enviando la exception como parámetro.
			useCaseContext.goToChildUseCase(ErrorUseCase.class, new MapBuilder().put(SisopAdminWebConstants.EXCEPTION_KEY, excep).build());
			
			return useCaseContext.getActionForward();
		}
	}
	
	/**
	 * Deriva la ejecución del método al UseCaseModel.
	 * Se modularizó para una mejor organización del código.
	 */
	protected UseCaseContext resolveAndInvokeMethod(UseCaseContext useCaseContext, Method method, ActionForm form, HttpServletRequest request) throws Exception {
		try {
			UseCaseModel useCaseModel;
			// Si el método a ejecutar representa el inicio de un Caso de uso, entonces
			// el UseCaseAction se encarga de instanciar el UseCaseModel correspondiente.
			if("initUseCase".equals(method.getName())) {
				useCaseModel = this.createUseCaseModel(useCaseContext);
				
				if(!useCaseContext.getUseCaseStack().isEmpty()) {
					((UseCaseForm) form).setUseCaseModel(useCaseModel);
				}
			}
			else {
				useCaseModel = this.getUseCaseModel(useCaseContext.getUseCaseStack());
			}

			// Ante todo, la seguridad.
			if(!this.hasPermission(useCaseModel.getMode(), method, request)) {
				throw ExceptionFactory.createSisopAdminSecurityException("El usuario no posee los permisos necesarios para ejecutar la operacion solicitada.");
			}
			
			logger.info("Invocando el método '" + method.getName() + "' sobre el UseCaseModel '" + useCaseModel + "'...");
			// Se delega la ejecución del método sobre el UseCaseModel.
			method.invoke(useCaseModel, new Object[] {useCaseContext});
			
			return useCaseContext;
		}
		catch(InvocationTargetException excep) {
			// Se arroja el targetException para facilitar la captura en el método invocador.
			Throwable t = excep.getTargetException();
			throw (t instanceof Exception) ? (Exception) t: new ServletException(t);
		}
		catch(NoSuchMethodException excep) {
			throw ExceptionFactory.createProgramException("Error al chequear permisos de usuario sobre el método: " + method.getName() + " de la clase: " + this.getClass().getName(), excep);
		}
	}
	
	/**
	 * Analiza si el usuario posee los permisos para ejecutar dicho método para
	 * un determinado modo.
	 *  
	 * @param method
	 * @param request
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	protected boolean hasPermission(UseCaseModelMode mode, Method method, HttpServletRequest request) throws SecurityException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		UserContext userContext = (UserContext) request.getSession().getAttribute(SisopAdminWebConstants.SESSION_USER_CONTEXT);
		
		return UseCaseUtils.hasPermission(this, mode, method.getName(), userContext.getPermisosUsuario());
	}
	
	
	
	/**
	 * Crea el modelo del caso de uso.<br>
	 * Le aplica la configuración de auditoría y persistencia.
	 * 
	 * @param context
	 * @return UseCaseModel: Modelo creado y configurado.
	 */
	private UseCaseModel createUseCaseModel(UseCaseContext context) {
		try {
			UseCaseModel useCaseModel = (UseCaseModel) this.useCaseModelClass().newInstance();
			useCaseModel.setUseCase(this);
			useCaseModel.initMode();
			
			useCaseModel = (UseCaseModel) SisopAdminServiceProvider.getPersistenceManagerInterceptor().create(useCaseModel);
			// TODO HACER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			// useCaseModel = (UseCaseModel) SisopAdminServiceProvider.getAuditoryInterceptor().create(useCaseModel, this.auditLevel());			
			
			this.createUseCaseElement(context, useCaseModel);
			
			// En caso de haber indicado explícitamente el modo, se lo setea.
			// TODO QUE SEA POR QUERYSTRING TAMBIEN!!!!!!!!!!!!!!!!!!!!
			UseCaseModelMode mode = (UseCaseModelMode) context.getParameter(UseCaseFWKConstants.USECASEMODELMODE_KEY);
			if(mode != null) {
				useCaseModel.setMode(mode);
			}

			return useCaseModel;
		}
		catch(Exception excep) {
			throw new ProgramException("Falla producida al intentar crear el UseCaseModel.", excep);
		}
	}

	/**
	 * Crea el Elemento de stack que almacenará el UseCaseModel.
	 * 
	 * @param context
	 * @param useCaseModel
	 */
	private void createUseCaseElement(UseCaseContext context, UseCaseModel useCaseModel) {
		UseCaseStack stack = context.getUseCaseStack();
		
		UseCaseElement useCaseElement = new UseCaseElement();

		if(stack.hasUseCaseParameters()) {
			useCaseElement.setParameters( ((UseCaseParametersElement) stack.pop()).getParameters() );
		}
		else {
			// Se vacía la pila ya que la navegación ó representa el primer caso de uso 
			// del usuario ó se accedió a un nuevo workflow.
			// Esto significa que si existían CU previos no deben considerárselos.
			stack.clearAllPopupeables();
			useCaseElement.setParameters(CollectionFactory.createMap());
		}
		
		// Se configura si el CU es base para la sesión del usuario.
		useCaseElement.setCanBePopupeable(!this.isBaseUseCase());
		useCaseElement.setUseCase(this.getClass());
		useCaseElement.setUseCaseModel(useCaseModel);
		
		stack.push(useCaseElement);
	}
	

	
	/**
	 * Retorna la instancia del modelo que se utiliza en el caso de uso.
	 */
	private UseCaseModel getUseCaseModel(UseCaseStack stack) {
		return ((UseCaseElement) stack.viewStackElement()).getUseCaseModel();
	}
	
	/**
	 * La búsqueda del método se realizará sobre el UseCaseModel asociado al CU.
	 * El método se encuentra cacheado.
	 */
	protected Method getMethod(String name) throws NoSuchMethodException {
		synchronized(methods) {
            Method method = (Method) methods.get(name);

            if (method == null) {
           		method = this.useCaseModelClass().getMethod(name, new Class[] { UseCaseContext.class });        		
                methods.put(name, method);
            }
            return (method);
        }
	}

}