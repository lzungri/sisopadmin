package ar.com.grupo306.sisopadmin.reportes.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.sisopadmin.config.Module;
import ar.com.grupo306.sisopadmin.config.SisopAdminConfig;
import ar.com.grupo306.sisopadmin.reportes.interfaces.ReportsGeneratorService;
/**
 * Servicio para la generación de reportes
 * @author Rafa
 *
 */
public class ReportsGeneratorServiceImpl implements ReportsGeneratorService{
	/** Reportes cacheados */
	private Map<String, JasperReport> jasperReports = CollectionFactory.createMap(String.class, JasperReport.class);
	
	public void generateReport(String jasperPath, String pdfPath, Collection beansCollection) {
		InputStream stream = null;
		try {
			stream = new FileInputStream(jasperPath);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		JRDataSource dataSource = new JRBeanCollectionDataSource(beansCollection);
		JasperPrint jasperPrint = null;
		try {

			 jasperPrint = JasperFillManager.fillReport(stream, new HashMap(), dataSource);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
		try {
			
			JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath );
		} catch (JRException e1) {
			e1.printStackTrace();
		}
		
	}

	public void generateReportFromXml(String xmlPath, String pdfPath, Collection beansCollection) {
		JasperReport jasperReport = null;
		try{
			JasperDesign jasperDesign = JRXmlLoader.load(xmlPath);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		JRDataSource dataSource = new JRBeanCollectionDataSource(beansCollection);
		JasperPrint jasperPrint = null;
		try {

			 jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), dataSource);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
		try {
			
			JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath );
		} catch (JRException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public ByteArrayOutputStream generateReport(String reportName, Map parameters, JRDataSource dataSource, String outputFormat) {
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(getCompiledReport(reportName), parameters, dataSource);
			
			if(outputFormat == null) {
				throw ExceptionFactory.createProgramException("Debe indicarse un Formato de archivo de salida(outputFormat)");
			}

			ByteArrayOutputStream oStream = new ByteArrayOutputStream();
			if(outputFormat.equals("pdf"))
				JasperExportManager.exportReportToPdfStream(jasperPrint, oStream);
			else if(outputFormat.equals("xml"))
				JasperExportManager.exportReportToXmlStream(jasperPrint, oStream);
			else
				throw ExceptionFactory.createProgramException("El formato " + outputFormat + "no es reconocido.");
			
			return oStream;
		}
		catch(JRException excep) {
			throw ExceptionFactory.createProgramException("Error al intentar generar el reporte: " + reportName, excep);
		}
	}
	
	public JasperReport getCompiledReport(String reportName) {
		try {
			String compiledReportPath = SisopAdminConfig.getInstance().getProperty(Module.COMMON, "reports.compiled.path") + reportName + ".jasper";
			try {
				// Si no está el reporte cacheado se lo busca en el file system.
				if(jasperReports.get(reportName) == null) {
					// Si existe el compilado se lo carga en un JasperReport.
					jasperReports.put(reportName, (JasperReport) JRLoader.loadObject(new FileInputStream(compiledReportPath)));
				}
			}
			catch (FileNotFoundException excep) {
				// No está compilado ni cacheado => se lo compila.
				String sourceReportPath = SisopAdminConfig.getInstance().getProperty(Module.COMMON, "reports.source.path") + reportName + ".jrxml";
				
				JasperReport report = JasperCompileManager.compileReport(sourceReportPath);
				JRSaver.saveObject(report, compiledReportPath);
				jasperReports.put(reportName, report);
			}			 
			
			return (JasperReport) jasperReports.get(reportName);
		}
		catch(JRException excep) {
			throw ExceptionFactory.createProgramException("Error al intentar generar el reporte: " + reportName, excep);
		}
	}

}