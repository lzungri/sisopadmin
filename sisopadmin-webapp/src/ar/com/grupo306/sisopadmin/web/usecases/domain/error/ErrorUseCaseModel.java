package ar.com.grupo306.sisopadmin.web.usecases.domain.error;

import org.apache.commons.lang.exception.ExceptionUtils;

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
	
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);
		
		Exception exception = (Exception) context.getParameter(SisopAdminWebConstants.EXCEPTION_KEY);
		this.exceptionStackTrace.append(ExceptionUtils.getFullStackTrace(exception));
	}

	public StringBuffer getExceptionStackTrace() {
		return exceptionStackTrace;
	}

	public void setExceptionStackTrace(StringBuffer exceptionStackTrace) {
		this.exceptionStackTrace = exceptionStackTrace;
	}

}