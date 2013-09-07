package ar.com.grupo306.usecasefwk.usecases.context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.sisopadmin.web.constants.SisopAdminWebConstants;
import ar.com.grupo306.sisopadmin.web.context.UserContext;
import ar.com.grupo306.usecasefwk.constants.UseCaseFWKConstants;
import ar.com.grupo306.usecasefwk.usecases.message.BaseMessage;
import ar.com.grupo306.usecasefwk.usecases.message.ErrorMessage;
import ar.com.grupo306.usecasefwk.usecases.models.base.UseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;
import ar.com.grupo306.usecasefwk.usecases.stack.UseCaseStack;
import ar.com.grupo306.usecasefwk.usecases.stack.elements.UseCaseElement;
import ar.com.grupo306.usecasefwk.usecases.stack.elements.UseCaseParametersElement;

/**
 * Contexto que reciben los casos de uso al ejecutarse un método sobre ellos.
 * 
 * @author lzungri
 */
public class UseCaseContext {
	private ActionMapping mapping;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private UseCaseStack useCaseStack;
	private ActionForward actionForward;
	
	public UseCaseContext(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response) {
		this.mapping = mapping;
		this.request = request;
		this.response = response;
		this.useCaseStack = (UseCaseStack) request.getSession().getAttribute(UseCaseFWKConstants.SESSION_USECASE_STACK);
	}
	
	/**
	 * Retorna la pila donde se almacenan los Casos de uso visitados.
	 * La pila es almacenada en la Session del usuario.
	 */
	public UseCaseStack getUseCaseStack() {
		return this.useCaseStack;
	}
	
	private UseCaseElement getUseCaseElement() {
		return (UseCaseElement) this.useCaseStack.viewStackElement();
	}
	

	
	/**
	 * Ejecuta un caso de uso hijo.
	 * 
	 * @param useCaseClass: Clase del CU hijo a ejecutar.
	 * @param parameters: Parámetros que se le envían al CU hijo.
	 * @param returnMethod: Método que será invocado al finalizar la ejecución del
	 * CU hijo. En este método se recibirá al modelo de CU hijo para procesarlo.
	 */
	public void goToChildUseCase(Class useCaseClass, Map parameters, String returnMethod) {
		UseCaseParametersElement parametersElement = new UseCaseParametersElement();
		parameters.put(UseCaseFWKConstants.PARAMETER_RETURN_METHOD, returnMethod);
		parametersElement.setParameters(parameters);
		
		this.getUseCaseStack().push(parametersElement);
		
		/*
		 * El formato de los actions que representan casos de uso debe ser:
		 * XXXUseCase.java => action = xXXUseCase.
		 */
		this.actionForward = new ActionForward("/" + this.getActionNameFromClass(useCaseClass) + ".do?reqCode=initUseCase");
	}
	
	/**
	 * Ejecuta un caso de uso hijo sin pasar parámetros.
	 * 
	 * @param useCaseClass: Clase del CU hijo a ejecutar.
	 * @param returnMethod: Método que será invocado al finalizar la ejecución del
	 * CU hijo. En este método se recibirá al modelo de CU hijo para procesarlo.
	 */
	public void goToChildUseCase(Class useCaseClass, String returnMethod) {
		this.goToChildUseCase(useCaseClass, CollectionFactory.createMap(), returnMethod);
	}
	
	/**
	 * Ejecuta un caso de uso hijo.
	 * Al finalizar la ejecución del CU hijo se invocará al método por defecto del
	 * UseCaseModel, el cual puede ser redefinido en las subclases.
	 * 
	 * @param useCaseClass: Clase del CU hijo a ejecutar.
	 * @param parameters: Parámetros que se le envían al CU hijo.
	 */	
	public void goToChildUseCase(Class useCaseClass, Map parameters) {
		this.goToChildUseCase(useCaseClass, parameters, UseCaseModel.RETURN_FROM_CHILD_DEFAULT_METHOD);
	}
	
	/**
	 * Ejecuta un caso de uso hijo con el modo envíado por parámetro.
	 * Al finalizar la ejecución del CU hijo se invocará al método por defecto del
	 * UseCaseModel, el cual puede ser redefinido en las subclases.
	 * 
	 * @param useCaseClass: Clase del CU hijo a ejecutar.
	 * @param mode: Modo del CU hijo.
	 */
	public void goToChildUseCase(Class useCaseClass, UseCaseModelMode mode) {
		this.goToChildUseCase(useCaseClass, mode, CollectionFactory.createMap());
	}
	
	/**
	 *	Ejecuta un caso de uso hijo con el modo envíado por parámetro.
	 * Al finalizar la ejecución del CU hijo se invocará al método por defecto del
	 * UseCaseModel, el cual puede ser redefinido en las subclases.
	 * 
	 * @param useCaseClass: Clase del CU hijo a ejecutar.
	 * @param mode: Modo del CU hijo.
	 * @param parameters: Parámetros que se le envían al CU hijo.
	 */
	public void goToChildUseCase(Class useCaseClass, UseCaseModelMode mode, Map parameters) {
		parameters.put(UseCaseFWKConstants.USECASEMODELMODE_KEY, mode);
		this.goToChildUseCase(useCaseClass, parameters);
	}
	
	/**
	 * Ejecuta un caso de uso hijo con el modo envíado por parámetro.
	 * 
	 * @param useCaseClass: Clase del CU hijo a ejecutar.
	 * @param mode: Modo del CU hijo.
	 * @param returnMethod: Método que será invocado al finalizar la ejecución del
	 * CU hijo. En este método se recibirá al modelo de CU hijo para procesarlo.
	 */	
	public void goToChildUseCase(Class useCaseClass, UseCaseModelMode mode, String returnMethod) {
		Map parameters = CollectionFactory.createMap();
		parameters.put(UseCaseFWKConstants.USECASEMODELMODE_KEY, mode);
		this.goToChildUseCase(useCaseClass, parameters, returnMethod);
	}
	
	/**
	 * Ejecuta un caso de uso hijo con el modo envíado por parámetro.
	 * 
	 * @param useCaseClass: Clase del CU hijo a ejecutar.
	 * @param mode: Modo del CU hijo.
	 * @param parameters: Parámetros que se le envían al CU hijo.
	 * @param returnMethod: Método que será invocado al finalizar la ejecución del
	 * CU hijo. En este método se recibirá al modelo de CU hijo para procesarlo.
	 */
	public void goToChildUseCase(Class useCaseClass, UseCaseModelMode mode, Map parameters, String returnMethod) {
		parameters.put(UseCaseFWKConstants.USECASEMODELMODE_KEY, mode);
		this.goToChildUseCase(useCaseClass, parameters, returnMethod);
	}
	
	/**
	 * Ejecuta un caso de uso hijo sin pasar parámetros.
	 * Al finalizar la ejecución del CU hijo se invocará al método por defecto del
	 * UseCaseModel, el cual puede ser redefinido en las subclases.
	 * 
	 * @param useCaseClass: Clase del CU hijo a ejecutar.
	 */
	public void goToChildUseCase(Class useCaseClass) {
		this.goToChildUseCase(useCaseClass, CollectionFactory.createMap(), UseCaseModel.RETURN_FROM_CHILD_DEFAULT_METHOD);
	}
	
	/**
	 * ACEPTA el caso de uso y setea el modelo generado sobre el CU padre.
	 * Para accedes desde el padre al modelo resultado del hijo se debe ejecutar
	 * UseCaseContext#getReturnedModel()
	 */
	public void acceptUseCase() {
		this.actionForward = this.createActionForward(this.getActionNameFromClass(this.getUseCaseElement().getUseCase()), "acceptUseCase");
	}
	
	/**
	 * CANCELA el caso de uso y setea en null el modelo de retorno del CU padre. 
	 */
	public void cancelUseCase() {
		this.actionForward = this.createActionForward(this.getActionNameFromClass(this.getUseCaseElement().getUseCase()), "cancelUseCase");
	}
	
	/**
	 * Actualiza el caso de uso para reflejar los cambios que se hayan introducido
	 * al procesar el modelo.
	 * Ejemplo: Al agregar info al caso de uso y ejecutar el refresh, ésta se mostrará en pantalla.
	 * 
	 * @return
	 */
	public void refreshUseCase() {
		this.actionForward =  mapping.findForward("useCasePage");		
	}
	
	/**
	 * Cierra la aplicación, esto significa que se elimina el stack de CUs (en caso
	 * de existir) y se redirecciona a la página de cierre.
	 */
	public void closeApplication() {
		this.useCaseStack.clear();
		this.actionForward =  mapping.findForward("closeApplication");
	}
	
	/**
	 * Al finalizar un CU hijo se ejecuta el método de retorno sobre el padre.
	 * 
	 * TODO REVISAR ESTO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * 
	 * @param useCaseClass: Clase del CU del padre.
	 * @param returnMethod: Método que se invocará en el padre al finalizar el hijo.
	 */
	public void executeReturnMethod(Class useCaseClass, String returnMethod) {
		this.actionForward = this.createActionForward(this.getActionNameFromClass(useCaseClass), returnMethod);
	}
	
	/**
	 * Retorna el UseCaseModel resultado de ejecutar el caso de uso hijo
	 * invocado previamente.
	 */
	public UseCaseModel getReturnedModel() {
		return this.getUseCaseElement().getReturnedUseCaseModel();
	}
	
	/**
	 * Retorna el parámetro identificado por la key.
	 * Los parámetros aquí retornados corresponden a los que envío el CU
	 * padre al iniciar al hijo.
	 * 
	 * @param key
	 */
	public Object getParameter(String key) {
		return this.getUseCaseElement().getParameter(key);
	}
	
	/**
	 * Retorna todos los parámetros que el CU padre envío al iniciar al CU hijo.
	 */
	public Map getParameters() {
		return this.getUseCaseElement().getParameters();
	}
	
	/**
	 * Agrega un mensaje de error que luego será presentado en pantalla.
	 * 
	 * @param errorMessage
	 */
	public void addErrorMessage(String errorMessage) {
		List<ErrorMessage> errorMessages = (List<ErrorMessage>) this.request.getAttribute(UseCaseFWKConstants.ERROR_MESSAGES_KEY);
		if(errorMessages == null) {
			errorMessages = CollectionFactory.createList(ErrorMessage.class);
			this.request.setAttribute(UseCaseFWKConstants.ERROR_MESSAGES_KEY, errorMessages);
		}
		
		errorMessages.add(new ErrorMessage(errorMessage));
	}
	
	/**
	 * Indica si se registraron errores en el contexto del UseCase.
	 */
	public boolean hasErrors() {
		List<ErrorMessage> errorMessages = (List<ErrorMessage>) this.request.getAttribute(UseCaseFWKConstants.ERROR_MESSAGES_KEY);
		return errorMessages != null && !errorMessages.isEmpty();
	}
	
	/**
	 * Agrega un mensaje que luego será presentado en pantalla.
	 * 
	 * @param message
	 */
	public void addMessage(String message) {
		List<BaseMessage> messages = (List<BaseMessage>) this.request.getAttribute(UseCaseFWKConstants.MESSAGES_KEY);
		if(messages == null) {
			messages = CollectionFactory.createList(BaseMessage.class);
			this.request.setAttribute(UseCaseFWKConstants.MESSAGES_KEY, messages);
		}
		
		messages.add(new BaseMessage(message));		
	}
	
	/**
	 * Agrega sobre el CU un elemento TEMPORAL que podrá ser accedido en la página
	 * mediante la key.
	 * La forma de acceder desde el jsp es: ${requestScope.<key>}
	 *  
	 * @param key: Key que identifica al elemento.
	 * @param element.
	 */
	public void addElement(String key, Object element) {
		this.request.setAttribute(key, element);
	}
	
	/**
	 * Retorna un elemento que se haya agregado mediante el llamado a
	 * UseCaseContext#addElement()
	 * 
	 * @param key: Key del elemento.
	 * @return Object.
	 */
	public Object getElement(String key) {
		return this.request.getAttribute(key);
	}
	
	/**
	 * Agrega un elemento que perdurará siempre que exista la sesión del usuario
	 * La forma de acceder desde el jsp es: ${sessionScope.<key>}
	 *  
	 * @param key: Key que identifica al elemento.
	 * @param element.
	 */
	public void addGlobalElement(String key, Object element) {
		this.request.getSession().setAttribute(key, element);
	}
	
	/**
	 * Quita un elemento que de la sesión del usuario
	 *  
	 * @param key: Key que identifica al elemento.
	 */
	public void removeGlobalElement(String key) {
		this.request.getSession().removeAttribute(key);
	}
	
	/**
	 * Retorna un elemento que se haya agregado mediante el llamado a
	 * UseCaseContext#addGlobalElement()
	 * 
	 * @param key: Key del elemento.
	 * @return Object.
	 */
	public Object getGlobalElement(String key) {
		return this.request.getSession().getAttribute(key);
	}
	
	/**
	 * Agrega un attachment a la respuesta al cliente.
	 * No se define contentType.
	 * 
	 * @param filePath: Path absoluto del archivo. 
	 */
	public void addFileAttachment(String filePath) {
		File file = new File(filePath);
		addFileAttachment(filePath, file.getName());
	}
	
	/**
	 * Agrega un attachment a la respuesta al cliente.
	 * No se define contentType.
	 * 
	 * @param filePath: Path absoluto del archivo.
	 * @param clientFileName: Nombre con el que el usuario descargará el archivo.
	 */
	public void addFileAttachment(String filePath, String clientFileName) {
		try {
			File file = new File(filePath);
			FileInputStream inputStream = new FileInputStream(file);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			
			IOUtils.copy(inputStream, outputStream);
			
			response.setHeader("Content-Disposition", "attachment; filename=" + clientFileName);
			
			response.getOutputStream().write(outputStream.toByteArray());
			// Le envía el stream al cliente.
			response.getOutputStream().flush();
			outputStream.close();
			inputStream.close();
		}
		catch(FileNotFoundException excep) {
			throw ExceptionFactory.createBusinessException("El archivo no se encuentra disponible en estos momentos.");
		}
		catch(IOException excep) {
			throw ExceptionFactory.createProgramException("Error al intentar escribir el output stream.", excep);
		}
	}
	
	/**
	 * Agrega un attachment a la respuesta al cliente.
	 * 
	 * @param fileName
	 * @param contentType
	 * @param outputStream
	 */
	public void addFileAttachment(String fileName, String contentType, ByteArrayOutputStream outputStream) {
		try {
			response.setContentType(contentType);
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			
			response.getOutputStream().write(outputStream.toByteArray());
			// Le envía el stream al cliente.
			response.getOutputStream().flush();
		}
		catch(IOException excep) {
			throw ExceptionFactory.createProgramException("Error al intentar escribir el output stream.", excep);
		}
	}
	
	public UserContext getUserContext() {
		return (UserContext) this.getGlobalElement(SisopAdminWebConstants.SESSION_USER_CONTEXT);
	}
	
	/**
	 * Coloca el contexto en la sesión del usuario.
	 * 
	 * @param userContext
	 */
	public void setUserContext(UserContext userContext) {
		this.addGlobalElement(SisopAdminWebConstants.SESSION_USER_CONTEXT, userContext);
	}
	
	/**
	 * Obtiene el parámetro desde el request. ACLARACION: Parámetro no hace
	 * referencia a aquél que se envía entre CUs, sino al que envía el browser
	 * ante un submit.
	 * 
	 * USAR CON MODERACION.
	 * 
	 * @param key
	 */
	public String getRequestParameter(String key) {
		return this.request.getParameter(key);
	}
	
	/**
	 * Obtiene el parámetro desde el request. ACLARACION: Parámetro no hace
	 * referencia a aquél que se envía entre CUs, sino al que envía el browser
	 * ante un submit.
	 * 
	 * USAR CON MODERACION.
	 * 
	 * @param key
	 */
	public String[] getRequestParameterValues(String key) {
		return this.request.getParameterValues(key);
	}
	
	public ActionForward getActionForward() {
		return actionForward;
	}
	
	/**
	 * Indica si el useCaseContext tiene asignada una redirección de página.
	 */
	public boolean hasActionForward() {
		return this.actionForward != null;
	}

	private String getActionNameFromClass(Class useCaseClass) {
		return StringUtils.uncapitalize(ClassUtils.getShortClassName(useCaseClass));
	}
	
	private ActionForward createActionForward(String action, String reqCode) {
		return new ActionForward("/" + action + ".do?reqCode=" + reqCode, false);
	}
	
	/**
	 * Añade una cookie al response.
	 * @param cookie
	 */
	public void addCookie(Cookie cookie){
		response.addCookie(cookie);
	}

}