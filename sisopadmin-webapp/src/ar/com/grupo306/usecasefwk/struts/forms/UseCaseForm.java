package ar.com.grupo306.usecasefwk.struts.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import ar.com.grupo306.usecasefwk.constants.UseCaseFWKConstants;
import ar.com.grupo306.usecasefwk.usecases.models.base.UseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.stack.UseCaseStack;
import ar.com.grupo306.usecasefwk.usecases.stack.elements.UseCaseElement;

/**
 * Form que se utilizará para transportar los datos entre los dispatch y jsps.
 * 
 * @author lzungri
 */
public class UseCaseForm extends ActionForm {
	
	private UseCaseModel useCaseModel;

	
	
	/**
	 * Se redefine el reset() para setear sobre el form del modelo del CU.
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		
		/*
		 * Si representa un inicio de caso de uso => el modelo aún no está procesado
		 * por lo tanto no se puede setear.
		 * Se lo setea el UseCaseAction luego.
		 */
		if(!this.isInitUseCase(request)) {
			UseCaseStack stack = (UseCaseStack) request.getSession().getAttribute(UseCaseFWKConstants.SESSION_USECASE_STACK);
			
			this.setUseCaseModel(((UseCaseElement) stack.viewStackElement()).getUseCaseModel());
		}
	}
	
	private boolean isInitUseCase(HttpServletRequest request) {
		String methodName = request.getParameter("reqCode");
		
		return methodName != null && methodName.equals("initUseCase");
	}

	public UseCaseModel getUseCaseModel() {
		return useCaseModel;
	}

	public void setUseCaseModel(UseCaseModel useCaseModel) {
		this.useCaseModel = useCaseModel;
	}

}