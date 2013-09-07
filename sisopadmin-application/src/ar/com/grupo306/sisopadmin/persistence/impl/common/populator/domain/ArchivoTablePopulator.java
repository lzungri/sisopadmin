/**
 * 
 */
package ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain;

import java.util.Date;
import java.util.Set;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.sisopadmin.config.Module;
import ar.com.grupo306.sisopadmin.config.SisopAdminConfig;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.archivos.Archivo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Consigna;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Fase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.ItemPlantilla;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.PlantillaCorreccion;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Tp;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.TablePopulator;

/**
 * @author Damian
 *
 */
public class ArchivoTablePopulator extends TablePopulator {
	
	
	public void populateArchivoEjemplo01() {
		/*curso_practico.pdf*/
		
		String downloadPath = SisopAdminConfig.getInstance().getProperty(Module.RECURSOS, "abmArchivos.downloadFiles.path");
		Archivo nuevoArchivo= new Archivo();
		nuevoArchivo.setNombre("curso_practico.pdf");
		nuevoArchivo.setDescripcion("Curso práctico de Linux");
		nuevoArchivo.setRuta(downloadPath + "curso_practico.pdf");
		nuevoArchivo.save();
		
	}
	
	public void populateArchivoEjemplo02() {
		/*curso_practico.pdf*/
		
		String downloadPath = SisopAdminConfig.getInstance().getProperty(Module.RECURSOS, "abmArchivos.downloadFiles.path");
		Archivo nuevoArchivo= new Archivo();
		nuevoArchivo.setNombre("SisOp_030222.doc");
		nuevoArchivo.setDescripcion("Final evaluado en día 22/03/2003");
		nuevoArchivo.setRuta(downloadPath + "SisOp_030222.doc");
		nuevoArchivo.save();
	}
	
	public void populateArchivoEjemplo03() {
		/*curso_practico.pdf*/
		
		String downloadPath = SisopAdminConfig.getInstance().getProperty(Module.RECURSOS, "abmArchivos.downloadFiles.path");
		Archivo nuevoArchivo= new Archivo();
		nuevoArchivo.setNombre("SisOp_030719.doc");
		nuevoArchivo.setDescripcion("Final evaluado en día 19/07/2003");
		nuevoArchivo.setRuta(downloadPath + "SisOp_030719.doc");
		nuevoArchivo.save();
	}
	
	
	
}

