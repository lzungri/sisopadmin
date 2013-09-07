package ar.com.grupo306.usecasefwk.struts.plugin;

import java.util.Collection;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import ar.com.grupo306.usecasefwk.struts.mapping.ActionMappingUseCaseTransformer;
import ar.com.grupo306.usecasefwk.usecases.registry.UseCaseRegistry;

/**
 * Automatiza la creaci�n de los ActionMappings correspondientes a los casos
 * de uso de la aplicaci�n.
 * Evita el uso del struts-config.xml para definir los CUs.
 * 
 * Las aplicaciones que utilicen el UseCaseFWK deber�n subclasear esta clase para
 * registrar sus casos de uso particulares.
 *
 * @author Leandro
 */
public abstract class UseCaseRegister implements PlugIn {

	public void init(ActionServlet servlet, ModuleConfig moduleConfig) throws ServletException {
		UseCaseRegistry useCaseRegistry = UseCaseRegistry.getInstance(); 
		
		this.registerUseCases(useCaseRegistry);
		
		Collection<ActionMapping> transforms = useCaseRegistry.transform(new ActionMappingUseCaseTransformer());
		
		for(ActionMapping actionMapping : transforms) {
			actionMapping.setModuleConfig(moduleConfig);
			moduleConfig.addActionConfig(actionMapping);
		}		
	}

	public void destroy() {
		// Nada.
	}
	
	/**
	 * M�todo en el cual se registrar�n los casos de uso de la aplicaci�n.
	 * 
	 * @param useCaseRegistry
	 */
	public abstract void registerUseCases(UseCaseRegistry useCaseRegistry);
	
}