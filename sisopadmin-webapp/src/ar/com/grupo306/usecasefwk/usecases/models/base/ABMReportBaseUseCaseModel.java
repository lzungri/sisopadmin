package ar.com.grupo306.usecasefwk.usecases.models.base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.report.ReportUseCaseModel;

/**
 * Model para los casos de uso de ABM que generan reportes.
 * Como no se tiene herencia múltiple se implementa la interfaz y se compone de un Report.
 *
 * @author Leandro
 */
public abstract class ABMReportBaseUseCaseModel extends ABMBaseUseCaseModel implements ReportUseCaseModel {
	private String outputFormat;

	public Map createReportParameters() {
		// Por defecto vacío.
		return CollectionFactory.createMap();
	}
	
	@Transactional
	public void generateReport(UseCaseContext context) {
		ByteArrayOutputStream oStream = SisopAdminServiceProvider.getReportsGeneratorService().generateReport(
				getReportName(),
				createReportParameters(),
				createReportDatasource(),
				outputFormat);
		
		context.addFileAttachment(getReportName() + "." + outputFormat, "application/" + outputFormat, oStream);
		
		try {
			oStream.close();
		} catch (IOException excep) {
			throw ExceptionFactory.createProgramException("Error al intentar cerrar el outputStream del reporte " + getReportName(), excep);
		}
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}

}