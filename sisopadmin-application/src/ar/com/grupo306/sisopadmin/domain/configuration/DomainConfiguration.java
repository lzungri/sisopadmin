package ar.com.grupo306.sisopadmin.domain.configuration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;

/**
 * 
 * @author Rafa
 *
 */


public class DomainConfiguration {
	
	public static String  TIPO_SESION_PERSISTENCIA 				= "PERSISTENT";
	public static String  ARCHIVO_CONFIGURACION_VALIDADORES		= "validadoresConfiguracion.xml";//validadoresConfiguracion.xml
	private static Logger logger 								= Logger.getLogger(DomainConfiguration.class);
	
	
	
	public static Document getValidadoresForObjectAndMethod(ModelObject object , String method){
		InputStream is = getInputStream(DomainConfiguration.ARCHIVO_CONFIGURACION_VALIDADORES);
		String xml = InputStreamToString(is);
		SAXBuilder builder = new SAXBuilder();
	    Document doc = null;
		try {
			doc = builder.build(new StringReader(xml));
		}catch(Exception e){
			logger.debug("ERROR DE JDOMMMMM en el mecanismo de validadores");
		}
		
		return doc;
	}
	
	private static InputStream getInputStream(String nombreArchivo){
		logger.debug("Leyendo archivo: '" + nombreArchivo + "'");
		InputStream is = DomainConfiguration.class.getResourceAsStream(nombreArchivo);
		if(is != null) logger.debug("InputStream: '" + is.toString() + "'"); else logger.debug("InputStream: 'null'");
		return is;
	}
	
	private static String InputStreamToString(InputStream is){
		StringBuffer xmlBuffer = new StringBuffer();
		String linea = "";
		String newLine = "\n";
		
		try {
			InputStreamReader inputStream = new InputStreamReader(is);
			BufferedReader bufferReader = new BufferedReader(inputStream);
			while((linea = bufferReader.readLine()) != null) {
				xmlBuffer.append(linea + newLine);
			}
			return xmlBuffer.toString();
		} catch (Exception e) {
			logger.debug("No es posible leer el stream de entrada (archivo XML):");
			return null;
		}
	}
	
	
	
}
