package ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion.functors;

import org.apache.commons.collections.Closure;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
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
		if(encuesta.getUsuariosSinEncuestar() == null)
			encuesta.setUsuariosSinEncuestar(CollectionFactory.createTreeSet(Usuario.class));
		encuesta.getUsuariosSinEncuestar().add(usuario);
		usuario.saveOrUpdate();
		//encuesta.saveOrUpdate();
	}

}
