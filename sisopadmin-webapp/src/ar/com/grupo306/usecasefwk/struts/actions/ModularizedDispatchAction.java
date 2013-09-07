package ar.com.grupo306.usecasefwk.struts.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 * Modulariza los métodos principales del DispatchAction.
 *
 * @author Leandro
 */
public class ModularizedDispatchAction extends DispatchAction {

    protected ActionForward dispatchMethod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String name) throws Exception {
		// Make sure we have a valid method name to call.
		// This may be null if the user hacks the query string.
		if (name == null) {
			return this.unspecified(mapping, form, request, response);
		}
		
		// Identify the method object to be dispatched to
		Method method = null;
		try {
			
			method = getMethod(name);
			
		} catch(NoSuchMethodException e) {
			String userMsg =
				messages.getMessage("dispatch.method.user", mapping.getPath());
			throw new NoSuchMethodException(userMsg);
		}
		
		ActionForward forward = null;
		try {
			
			forward = this.invokeDispatchMethod(method, mapping, form, request, response); 
		
		} catch(ClassCastException e) {
			throw e;
		
		} catch(IllegalAccessException e) {
			throw e;
		
		} catch(InvocationTargetException e) {
			// Rethrow the target exception if possible so that the
			// exception handling machinery can deal with it
			Throwable t = e.getTargetException();
			if (t instanceof Exception) {
				throw ((Exception) t);
			} else {
				throw new ServletException(t);
			}
		}

		// Return the returned ActionForward instance
		return (forward);
    }
    
    /**
     * Ejecuta el dispatchMethod del Dispatch.
     * 
     * @param method
     * @param mapping
     * @param form
     * @param request
     * @param response
     */
    protected ActionForward invokeDispatchMethod(Method method, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Object args[] = {mapping, form, request, response};
    	
    	return (ActionForward) method.invoke(this, args);
    }
	
}