package ar.com.grupo306.sisopadmin.domain.modelo.impl.comparators;

import java.util.Comparator;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PuntoEncuestado;

public class PuntoEncuestadoComparator implements Comparator{

	public int compare(Object o1, Object o2) {
		PuntoEncuestado pe1 = (PuntoEncuestado)o1;
		PuntoEncuestado pe2 = (PuntoEncuestado)o2;
		
		return pe1.getName().equalsIgnoreCase(pe2.getName())? 0 : 1;
	}

}
