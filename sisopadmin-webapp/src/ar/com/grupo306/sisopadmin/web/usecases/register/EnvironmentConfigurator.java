package ar.com.grupo306.sisopadmin.web.usecases.register;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import ar.com.grupo306.sisopadmin.config.Module;
import ar.com.grupo306.sisopadmin.config.SisopAdminConfig;

/**
 * Configura el entorno del Struts.
 * No debería estar en este package, pero sino habría que crear otra clase que se acople
 * desde el usecasefwk al struts.
 *
 * @author Leandro
 */
public class EnvironmentConfigurator implements PlugIn {

	public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {
		config.getControllerConfig().setTempDir(SisopAdminConfig.getInstance().getProperty(Module.COMMON, "tempDir.path"));
	}

	public void destroy() {
		// Nada.
	}
}