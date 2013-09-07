package ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion.functors;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.Closure;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.comparators.ModelObjectComparator;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuestada;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.eventos.EventoNotificable;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Rol;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.RolDomainCodes;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;


public class EncuestaSetterClosure implements Closure{

	PlantillaEncuesta encuesta;
	
	public EncuestaSetterClosure(PlantillaEncuesta encuesta){
		this.encuesta = encuesta;
	}
	
	public void execute(Object arg0) {
		Usuario usuario = (Usuario)arg0;
		if(usuario.getEncuestasSinLlenar() == null)
			usuario.setEncuestasSinLlenar(CollectionFactory.createTreeSet(PlantillaEncuesta.class));
		usuario.getEncuestasSinLlenar().add(encuesta);
		if(encuesta.getUsuariosSinEncuestar() == null || encuesta.getUsuariosSinEncuestar().isEmpty())
			encuesta.setUsuariosSinEncuestar(CollectionFactory.createTreeSet(new ModelObjectComparator()));
		else{
			Set set = CollectionFactory.createTreeSet(new ModelObjectComparator());
			set.addAll(encuesta.getUsuariosSinEncuestar());
			encuesta.setUsuariosSinEncuestar(set);
		}	
		encuesta.getUsuariosSinEncuestar().add((Usuario)usuario);
		encuesta.setNovedad();
		
		
		usuario.saveOrUpdate();
		//encuesta.saveOrUpdate();
	}
	
	
	

}
