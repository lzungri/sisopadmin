package ar.com.grupo306.usecasefwk.struts.mapping;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.grupo306.usecasefwk.usecases.domain.base.UseCase;
import ar.com.grupo306.usecasefwk.usecases.registry.UseCaseTransformer;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseUtils;

/**
 * Encargada de generar una collection de ActionMappings en base a UseCases.
 *
 * @author Leandro
 */
public class ActionMappingUseCaseTransformer implements UseCaseTransformer {

	public Object transform(UseCase useCase) {
		ActionMapping actionMapping = new ActionMapping();
			
		actionMapping.setName(useCase.useCaseForm());
		actionMapping.setPath("/" + UseCaseUtils.getUseCasePath(useCase));
		actionMapping.setType(useCase.getClass().getName());
		actionMapping.setParameter("reqCode");
		actionMapping.setScope("request");
		
		ActionForward actionForward = new ActionForward();
		actionForward.setName("useCasePage");
		actionForward.setPath(useCase.tilesDefinitionKey());
		actionForward.setRedirect(false);
		
		actionMapping.addForwardConfig(actionForward);
			
		return actionMapping;
	}
	
}