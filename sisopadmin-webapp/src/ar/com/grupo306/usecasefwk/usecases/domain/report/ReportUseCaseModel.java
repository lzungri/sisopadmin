package ar.com.grupo306.usecasefwk.usecases.domain.report;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;

/**
 * Interfaz com�n de los UseCaseModel que generan reportes.
 * 
 * @author Sole
 */
public interface ReportUseCaseModel {
	
	/**
	 * Crea el datasource que utilizara el reporte para llenarse.
	 */
	public JRDataSource createReportDatasource();
	
	/**
	 * Par�metros que se le enviar�n al reporte.
	 */
	public Map createReportParameters();
	
	/**
	 * Nombre del reporte a generar.
	 */
	public String getReportName();
	
	public void generateReport(UseCaseContext context);

}