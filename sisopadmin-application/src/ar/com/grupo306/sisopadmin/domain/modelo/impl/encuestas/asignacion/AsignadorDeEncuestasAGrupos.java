package ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion.functors.DesasignadorEncuestaClosure;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion.functors.EncuestaSetterClosure;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Rol;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.RolDomainCodes;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
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
		Collection ayudantes = CollectionFactory.createCollection(Ayudante.class);
		try{
			ayudantes = SisopAdminServiceProvider.getPersistenceService().findAll(Ayudante.class);
		}catch(Exception e){
			return false;
		}
		Collection grupos = CollectionFactory.createCollection(Grupo.class);
		try{
			grupos = SisopAdminServiceProvider.getPersistenceService().findAll(Grupo.class);
			CollectionUtils.filter(grupos,new GrupoRolFilter());
		}catch(Exception e){
			return false;
		}
		DesasignadorEncuestaClosure closureD= new DesasignadorEncuestaClosure(encuesta);
		EncuestaSetterClosure closureA =  new EncuestaSetterClosure(encuesta);
		CollectionUtils.forAllDo(ayudantes,closureD);
		CollectionUtils.forAllDo(grupos, closureA);
		return true;	
	}
	
	private static class GrupoRolFilter implements Predicate{

		public boolean evaluate(Object arg0) {
			Usuario usuario = (Usuario)arg0;
			return usuario.getRoles().contains(Rol.findMeByDomainCode(RolDomainCodes.ROL_GRUPO_INSCRIPTO));
		}
		
	}
	
}
