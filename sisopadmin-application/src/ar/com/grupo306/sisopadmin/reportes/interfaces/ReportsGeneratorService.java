package ar.com.grupo306.sisopadmin.reportes.interfaces;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperReport;

public interface ReportsGeneratorService {
	/**
	 * Genera el pdf con el .jasper ya compilado
	 * @param jasperPath
	 * @param pdfPath
	 * @param beansCollection
	 */
	public void generateReport(String jasperPath,String pdfPath,Collection beansCollection	);
	
	/**
	 * Genera el reporte a partir de un xml
	 * @param xmlPath
	 * @param pdfPath
	 * @param beansCollection
	 */
	public void generateReportFromXml(String xmlPath,String pdfPath,Collection beansCollection);
	
	/**
	 * Genera el reporte. Lo compila si es necesario.
	 * 
	 * @param reportName
	 * @param parameters
	 * @param dataSource
	 * @param outputFormat
	 */
	public ByteArrayOutputStream generateReport(String reportName, Map parameters, JRDataSource dataSource, String outputFormat);
	
	/**
	 * Retorna el compilado de un reporte. Si no existe lo compila.
	 * 
	 * @param reportName
	 */
	public JasperReport getCompiledReport(String reportName);
	
}
