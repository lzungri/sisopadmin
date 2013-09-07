package ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion.functors.EncuestaSetterClosure;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

public class AsignadorDeEncuestasAAyudantes extends AsignadorDeEncuestas{
	
	/**
	 * asigna encuestas a todos los alumnos
	 * @param encuestas
	 * @return
	 */
	public boolean asignar(Collection encuestas){
		
		
		
		return false;
		
	}
	/**
	 * Asigna la encuesta a todos los alumnos
	 * @param encuesta
	 * @return
	 */
	public boolean asignar(PlantillaEncuesta encuesta){
		Collection usuarios = CollectionFactory.createCollection(Ayudante.class);
		try{
			usuarios = SisopAdminServiceProvider.getPersistenceService().findAll(Ayudante.class);
		}catch(Exception e){
			return false;
		}
		EncuestaSetterClosure closure =  new EncuestaSetterClosure(encuesta);
		CollectionUtils.forAllDo(usuarios,closure);
		return true;
		
	}
	
	
	
}
