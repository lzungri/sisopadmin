package ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain;

import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.TablePopulator;

/**
 * Popula la base de eventos a notificar al usuario.
 *
 * @author Leandro
 */
public class EventoNotificableTablePopulator extends TablePopulator {
	
//	public void populateEventosCoordinador() {
//		new EventoNotificable(DateUtils.addDays(new Date(), 3), "1 - Este mensaje sólo debe ser leído por un Coordinador", "http://www.linux.org")
//			.addNotificable(Rol.findMeByDomainCode(Rol.ROL_COORDINADOR))
//			.save();
//		
//		new EventoNotificable(DateUtils.addDays(new Date(), 3), "1 - Este mensaje sólo debe ser leído por un Coordinador (Ir al caso de uso de Administración de tps)", "./administrarTPsUseCase.do?reqCode=initUseCase")
//			.addNotificable(Rol.findMeByDomainCode(Rol.ROL_COORDINADOR))
//			.save();
//	}
//	
//	public void populateEventosCoordinadorAyudante() {
//		new EventoNotificable(DateUtils.addDays(new Date(), 3), "2 - Este mensaje sólo debe ser leído por un Coordinador y Ayudante", "http://www.apache.org")
//			.addNotificable(Rol.findMeByDomainCode(Rol.ROL_COORDINADOR))
//			.addNotificable(Rol.findMeByDomainCode(Rol.ROL_AYUDANTE))
//			.save();
//	}
//	
//	public void populateEventosAyudante() {
//		new EventoNotificable(DateUtils.addDays(new Date(), 3), "3 - Este mensaje sólo debe ser leído por un Ayudante")
//			.addNotificable(Rol.findMeByDomainCode(Rol.ROL_AYUDANTE))
//			.save();
//	}
//	
//	public void populateEventosSoledad() {
//		new EventoNotificable(DateUtils.addDays(new Date(), 3), "4 - Este mensaje sólo debe ser leído por Soledad")
//			.addNotificable(Usuario.findMeByLoginName("soledad"))
//			.save();
//	}

}