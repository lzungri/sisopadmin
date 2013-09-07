package ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain;

import java.util.Date;

import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.informacion.Informacion;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.TablePopulator;

/**
 * Popula informaciones por default
 * @author Damian
 */
public class InformacionCatedraTablePopulator extends TablePopulator {

	public void populateInformacionInscripcion() {
		StringBuffer contenido = new StringBuffer();
		
		contenido.append("Se recuerda que tal como lo aclaran las NTP, la inscripci&oacute;n al TP ");
		contenido.append("finaliza en la fecha de presentaci&oacute;n del mismo en aula magna, o sea el ");
		contenido.append("pr&oacute;ximo s&aacute;bado 1 de Septiembre.&nbsp;</p>");
		contenido.append("<p>Las planillas impresas se recibir&aacute;n &uacute;nicamente ese s&aacute;bado, y los ");
		contenido.append("		emails de inscripci&oacute;n podr&aacute;n ser enviados hasta el viernes inclusive.</p>");
		
		Informacion informacion = new Informacion();
		informacion.setNombre("Inscripción al trabajo práctico");
		informacion.setDescripcion("Inscripción al Trabajo Práctico cuatrimestral");
		informacion.setContenido(contenido.toString());
		informacion.setFechaInicio(new Date());
		informacion.setFechaFin(DateUtils.addDays(new Date(), 40));
		informacion.setEstado(1L);
		informacion.save();	
	}
	
	public void populateInformacionPresentacion() {
		StringBuffer contenido = new StringBuffer();
		
		contenido.append("El s&aacute;bado 1 de Septiembre se presentar&aacute; el Trabajo Pr&aacute;ctico en el ");
		contenido.append("aula magna de Medrano a las 14hs. El mismo ser&aacute; subido al grupo a la ");
		contenido.append("brevedad.");

		
		Informacion informacion = new Informacion();
		informacion.setNombre("Presentacion del trabajo práctico");
		informacion.setDescripcion("Presentación del Trabajo Práctico cuatrimestral");
		informacion.setContenido(contenido.toString());
		informacion.setFechaInicio(new Date());
		informacion.setFechaFin(DateUtils.addDays(new Date(), 30));
		informacion.setEstado(1L);
		informacion.save();	

	}
	
	public void populateInformacionCharlaSocketes(){
		StringBuffer contenido = new StringBuffer();
		
		contenido.append("Alumnos:<br />&nbsp;&nbsp; El d&iacute;a S&aacute;bado 8 de septiembre se ");
		contenido.append("realizar&aacute; una charla con el objetivo de introducirlos en los conceptos ");
		contenido.append("b&aacute;sicos para llevar a cabo el desarrollo del tp cuatrimestral. <br /><br />Los ");
		contenido.append("temas principales que en ella se abordar&aacute;n ser&aacute;n: Procesos, sockets, ");
		contenido.append("comandos de Linux, herramientas de depuraci&oacute;n, entre otros.<br /><br />La charla estar&aacute; orientada a aquellas personas que cursen por primera vez la materia o que no posean dichos conocimientos.");
		
		
		Informacion informacion = new Informacion();
		informacion.setNombre("Charla introductoria de Linux, sockets y procesos.");
		informacion.setDescripcion("Invitación a la charla de Sockets");
		informacion.setContenido(contenido.toString());
		informacion.setFechaInicio(new Date());
		informacion.setFechaFin(DateUtils.addDays(new Date(), 60));
		informacion.setEstado(1L);
		informacion.save();	
	}

	
	public void populateInformacionEmulaso(){
		StringBuffer contenido = new StringBuffer();
		
		contenido.append("Alumnos:</font></p>");
		contenido.append("&nbsp;&nbsp; Ya se encuentra disponible en el grupo la ");
		contenido.append("especificaci&oacute;n del trabajo pr&aacute;ctico correspondiente al segundo ");
		contenido.append("cuatrimestre de 2007.");
		contenido.append("<p>&nbsp;<br />El archivo que deber&aacute;n descargar se denomina TP20072C-EMULASO.zip, el cual contendr&aacute;:</p>");
		contenido.append("<p>&nbsp;- El documento de especificaci&oacute;n del tp.</p>");
		contenido.append("<p>&nbsp;- Un script de creaci&oacute;n de usuarios.</p>");
		contenido.append("<p>&nbsp;- 2 (dos) programas ejemplo que deber&aacute; ser capaz de ejecutar EMULASO.</p>");
		
		Informacion informacion = new Informacion();
		informacion.setNombre("EMULASO - Trabajo pr&aacute;ctico 2do Cuatrimestre");
		informacion.setDescripcion("Presentación del trabajo práctico cuatrimestral");
		informacion.setContenido(contenido.toString());
		informacion.setFechaInicio(new Date());
		informacion.setFechaFin(DateUtils.addDays(new Date(), 50));
		informacion.setEstado(1L);
		informacion.save();	
	}
	
	public void populateInformacionEmulasoFest(){
		StringBuffer contenido = new StringBuffer();
		
		contenido.append("</p><p>Alumnos:<br />");
		contenido.append("&nbsp;&nbsp; Les comunicamos que el s&aacute;bado 13 de octubre dispondremos ");
		contenido.append("de los Laboratorios 1 y 2 de Medrano para que todos aquellos grupos inscriptos que ");
		contenido.append("quieran recibir un feedback de su tp y/o realizar consultas sobre el mismo puedan hacerlo personalmente all&iacute;.");
		contenido.append("<br /><br />Los ayudantes y sus horarios de asistencia ser&aacute;n los siguientes:<br /></p><ul>");
		contenido.append("<li>Esteban Masoero: 10 a 13.</li><li>Federico Pomesano: 10 a 13.</li><li>Gabriel Bonsoir: 12 a 16.</li><li>Leandro Zungri: 10 a 16/17.");
		contenido.append("</span></li></ul>Los grupos que tengan como ayudante a alguno de los ");
		contenido.append("mencionados previamente, pueden comunicarse con &eacute;l para planificar un ");
		contenido.append("horario de correcci&oacute;n si as&iacute; lo desean.");
		contenido.append("<br /> Los esperamos.");
		
		Informacion informacion = new Informacion();
		informacion.setNombre("EmulasoFest - S&aacute;bado 13, Laboratorios 1 y 2 (Medrano)");
		informacion.setDescripcion("EmulasoFest, reunión de soporte y evaluación del tp.");
		informacion.setContenido(contenido.toString());
		informacion.setFechaInicio(new Date());
		informacion.setFechaFin(DateUtils.addDays(new Date(), 70));
		informacion.setEstado(1L);
		informacion.save();	
	}
}
