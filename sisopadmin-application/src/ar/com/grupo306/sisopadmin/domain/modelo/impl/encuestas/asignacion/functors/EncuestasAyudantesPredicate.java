package ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion.functors;

import org.apache.commons.collections.Predicate;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;

public class EncuestasAyudantesPredicate implements Predicate{

	
	
	public boolean evaluate(Object arg0) {
		PlantillaEncuesta encuesta = (PlantillaEncuesta)arg0;
		return "AYUDANTE".equalsIgnoreCase(encuesta.getDestinatario());
	}

}
