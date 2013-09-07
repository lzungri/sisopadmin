package ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion.functors;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

import org.apache.commons.collections.Closure;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.comparator.NombreComparator;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;

public class DesasignadorEncuestaClosure implements Closure{

	PlantillaEncuesta encuesta;
	
	public DesasignadorEncuestaClosure(PlantillaEncuesta encuesta){
		this.encuesta = encuesta;
	}
	
	public void execute(Object arg0) {
		Usuario usuario = (Usuario)arg0;
		if(usuario.getEncuestasSinLlenar() == null)
			usuario.setEncuestasSinLlenar(CollectionFactory.createSet(PlantillaEncuesta.class));
		Collection encuestas =usuario.getEncuestasSinLlenar();
		Iterator iEncuestas = encuestas.iterator();
		//encuesta = encuesta.findMeByName(encuesta.getNombre());
		if(encuesta.getUsuariosSinEncuestar() == null)
			encuesta.setUsuariosSinEncuestar(CollectionFactory.createSet(Usuario.class));
		this.encuesta.getUsuariosSinEncuestar().remove(usuario);
		encuestas.remove(this.encuesta);
		
				
		
		usuario.saveOrUpdate();
		encuesta.saveOrUpdate();
	}

}