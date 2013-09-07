package ar.com.grupo306.sisopadmin.web.servlets;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet para atender web servicios.
 * Se hízo para responder con fritas.
 *
 * @author Leandro
 */
public class WebServiceServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String serviceId = request.getParameter("serviceId");
		
		try {
			Method serviceMethod = this.getClass().getDeclaredMethod(serviceId, new Class [] {HttpServletRequest.class, HttpServletResponse.class});
			serviceMethod.invoke(this, new Object [] {request, response});
		}
		catch(Exception excep) {
			// Nada.
		}
	}
	
}