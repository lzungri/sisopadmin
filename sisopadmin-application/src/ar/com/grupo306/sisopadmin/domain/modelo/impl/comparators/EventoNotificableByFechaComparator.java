package ar.com.grupo306.sisopadmin.domain.modelo.impl.comparators;

import java.util.Comparator;
import java.util.Date;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.eventos.EventoNotificable;

/**
 * Comparador que ordena por diferencia entre la fechaHasta y fecha actual
 * en forma ascendente.
 *
 * @author Leandro
 */
public class EventoNotificableByFechaComparator implements Comparator<EventoNotificable> {

	public int compare(EventoNotificable o1, EventoNotificable o2) {
		long now = new Date().getTime();
		long intervalo1 = o1.getFechaHasta().getTime() - now;
		long intervalo2 = o2.getFechaHasta().getTime() - now;
		
		return new Long(intervalo1).compareTo(new Long(intervalo2)) * 1000 + o1.getFechaDesde().compareTo(o2.getFechaHasta());
	}

}