package ar.com.grupo306.sisopadmin.web.servlets;

import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ar.com.grupo306.usecasefwk.struts.converters.DateConverter;
import ar.com.grupo306.usecasefwk.struts.converters.DoubleLocaleConverter;


/**
 * Servlet encargado de inicializar la aplicación de Sisop Admin..
 *
 * @author Leandro
 */
public class SisopAdminApplicationStartup extends HttpServlet {

    public void init() {
        initLog4J();
        
        registerSisopAdminConverters();
    }

	/**
	 * Inicializa el mecanismo de Log4J,
	 */
    private void initLog4J() {
		String prefix = getServletContext().getRealPath("/");
        String file = getInitParameter("log4j-init-file");
        
        // if the log4j-init-file is not set, then no point in trying
        if (file != null) {
            PropertyConfigurator.configure(prefix + file);
        }
        
        Logger logger = Logger.getLogger(SisopAdminApplicationStartup.class);
        logger.info("Mecanismo de Log4j Iniciado correctamente...");
	}
    
    /**
     * Registra los convertes de struts propios de SisopAdmin.
     */
    private void registerSisopAdminConverters() {
    	ConvertUtils.register(new DateConverter(null), Date.class);
    	ConvertUtils.register(new DoubleLocaleConverter(null), Double.class);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) {
    	// Nada...
    }
}