package ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion;

import java.util.Collection;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion.functors.EncuestaSetterClosure;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.componentesGrupo.Alumno;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

public class AsignadorDeEncuestasAGrupos extends AsignadorDeEncuestas{
	
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
		Collection usuarios = CollectionFactory.createCollection(Grupo.class);
		try{
			usuarios = SisopAdminServiceProvider.getPersistenceService().findAll(Grupo.class);
		}catch(Exception e){
			return false;
		}
		EncuestaSetterClosure closure =  new EncuestaSetterClosure(encuesta);
		CollectionUtils.forAllDo(usuarios,closure);
		return true;
		
	}
	
	
	
}
