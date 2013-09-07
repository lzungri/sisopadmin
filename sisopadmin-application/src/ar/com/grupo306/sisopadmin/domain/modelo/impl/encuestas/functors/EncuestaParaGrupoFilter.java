package ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.functors;

import org.apache.commons.collections.Predicate;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;

public class EncuestaParaGrupoFilter implements Predicate{
	
	String destinatario = "GRUPO";

	public boolean evaluate(Object arg0) {
		PlantillaEncuesta encuesta = (PlantillaEncuesta)arg0;
		return destinatario.equalsIgnoreCase(encuesta.getDestinatario());
	}

}