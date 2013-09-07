package ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.functors;

import org.apache.commons.collections.Predicate;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuestada;

public class EncuestadaFilter implements Predicate{

	public boolean evaluate(Object arg0) {
		// TODO Auto-generated method stub
		return !(arg0 instanceof PlantillaEncuestada);
	}

}
