package ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain;

import java.util.Date;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.informacion.Informacion;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.TablePopulator;

/**
 * Popula informaciones por default
 * @author Damian
 */
public class InformacionCatedraTablePopulator extends TablePopulator {
	
	public void populateInformacion() {
		StringBuffer contenido = new StringBuffer();
		
		contenido.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">");
		contenido.append("<HTML>");
		contenido.append("<HEAD>");
		contenido.append("</HEAD>");
		contenido.append("");
		contenido.append("<BODY>");
		contenido.append("	<P>Bienvenidos a...</P>");
		contenido.append("	<P>SISOPADMIN!!!</P>");
		contenido.append("</BODY>");
		contenido.append("</HTML>");
		
		Informacion informacion = new Informacion();
		informacion.setNombre("Bienvenida");
		informacion.setDescripcion("Bienvenida");
		informacion.setContenido(contenido.toString());
		informacion.setFechaInicio(new Date());
		informacion.setEstado(1L);
		informacion.save();
		

	}
	
}