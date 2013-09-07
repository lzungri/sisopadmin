package ar.com.grupo306.sisopadmin.web.usecases.domain.error;

import org.apache.commons.lang.exception.ExceptionUtils;

import ar.com.grupo306.sisopadmin.config.Module;
import ar.com.grupo306.sisopadmin.config.SisopAdminConfig;
import ar.com.grupo306.sisopadmin.web.constants.SisopAdminWebConstants;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.BaseUseCaseModel;

/**
 * Modelo para el Caso de uso de error.
 *
 * @author Leandro
 */
public class ErrorUseCaseModel extends BaseUseCaseModel {
	private StringBuffer exceptionStackTrace = new StringBuffer();;
	private boolean exceptionDebugging;
	
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);
		
		exceptionDebugging = "true".equals(SisopAdminConfig.getInstance().getProperty(Module.COMMON, "exception.debugging"));
		
		Exception exception = (Exception) context.getParameter(SisopAdminWebConstants.EXCEPTION_KEY);
		this.exceptionStackTrace.append(ExceptionUtils.getFullStackTrace(exception));
	}

	public StringBuffer getExceptionStackTrace() {
		return exceptionStackTrace;
	}

	public void setExceptionStackTrace(StringBuffer exceptionStackTrace) {
		this.exceptionStackTrace = exceptionStackTrace;
	}

	public boolean getExceptionDebugging() {
		return exceptionDebugging;
	}

	public void setExceptionDebugging(boolean exceptionDebugging) {
		this.exceptionDebugging = exceptionDebugging;
	}

}