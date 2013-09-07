package ar.com.grupo306.sisopadmin.auditory.interfaces;

import ar.com.grupo306.sisopadmin.auditory.impl.AuditEvent;
import ar.com.grupo306.sisopadmin.service.ApplicationService;

/**
 * Servicio para la auditoria de eventos de sistema.
 *
 * @author Leandro
 */
public interface AuditoryService extends ApplicationService {
	
	public void audit(AuditEvent auditEvent);

}