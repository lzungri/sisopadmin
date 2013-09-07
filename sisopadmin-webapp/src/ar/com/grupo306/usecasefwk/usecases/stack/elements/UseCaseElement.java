package ar.com.grupo306.usecasefwk.usecases.stack.elements;

import java.util.Map;

import ar.com.grupo306.usecasefwk.constants.UseCaseFWKConstants;
import ar.com.grupo306.usecasefwk.usecases.domain.base.UseCase;
import ar.com.grupo306.usecasefwk.usecases.models.base.UseCaseModel;

/**
 * Elemento del stack que contiene información sobre un caso de uso.
 * 
 * @author lzungri
 */
public class UseCaseElement extends StackElement {
	private Class<? extends UseCase> useCase;
	private Map parameters;
	private UseCaseModel useCaseModel;
	private UseCaseModel returnedUseCaseModel;
	
	public UseCaseElement() {
		super();
	}
	
	public UseCaseElement(UseCaseModel useCaseModel, Map parameters) {
		this.useCaseModel = useCaseModel;
		this.parameters = parameters;
	}
	
	public String getReturnMethod() {
		return (String) this.parameters.get(UseCaseFWKConstants.PARAMETER_RETURN_METHOD);
	}
	
	public Object getParameter(String key) {
		return this.parameters.get(key);
	}

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}

	public UseCaseModel getReturnedUseCaseModel() {
		return returnedUseCaseModel;
	}

	public void setReturnedUseCaseModel(UseCaseModel returnedUseCaseModel) {
		this.returnedUseCaseModel = returnedUseCaseModel;
	}

	public UseCaseModel getUseCaseModel() {
		return useCaseModel;
	}

	public void setUseCaseModel(UseCaseModel useCaseModel) {
		this.useCaseModel = useCaseModel;
	}

	public Class<? extends UseCase> getUseCase() {
		return useCase;
	}

	public void setUseCase(Class<? extends UseCase> useCase) {
		this.useCase = useCase;
	}

}