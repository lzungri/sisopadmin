package ar.com.grupo306.sisopadmin.auditory.impl;

import ar.com.grupo306.sisopadmin.auditory.interfaces.AuditoryService;

/**
 * Implementaci�n del servicio de auditoria.
 *
 * @author Leandro
 */
public class AuditoryServiceImpl implements AuditoryService {

	public void audit(AuditEvent auditEvent) {
		System.out.println(auditEvent.toString());
	}

}