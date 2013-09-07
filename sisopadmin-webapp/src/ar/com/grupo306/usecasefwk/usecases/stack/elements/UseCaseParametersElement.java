package ar.com.grupo306.usecasefwk.usecases.stack.elements;

import java.util.HashMap;
import java.util.Map;

/**
 * Elemento del stack que contiene información sobre los parámetros que
 * se envían de un caso de uso a otro.
 * 
 * @author lzungri
 */
public class UseCaseParametersElement extends StackElement {
	private Map parameters = new HashMap();
	
	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}
	
	public UseCaseParametersElement addParameter(String key, Object value) {
		this.parameters.put(key, value);
		
		return this;
	}

}