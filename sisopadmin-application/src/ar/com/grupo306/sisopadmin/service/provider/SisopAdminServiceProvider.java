package ar.com.grupo306.sisopadmin.service.provider;

import ar.com.grupo306.sisopadmin.auditory.impl.AuditoryServiceImpl;
import ar.com.grupo306.sisopadmin.auditory.interfaces.AuditoryService;
import ar.com.grupo306.sisopadmin.persistence.impl.common.proxy.PersistenceManagerInterceptor;
import ar.com.grupo306.sisopadmin.persistence.impl.hibernate.HibernatePersistenceServiceImpl;
import ar.com.grupo306.sisopadmin.persistence.impl.hibernate.proxy.HibernatePersistenceManagerInterceptor;
import ar.com.grupo306.sisopadmin.persistence.interfaces.PersistenceService;
import ar.com.grupo306.sisopadmin.reportes.impl.ReportsGeneratorServiceImpl;
import ar.com.grupo306.sisopadmin.reportes.interfaces.ReportsGeneratorService;
import ar.com.grupo306.sisopadmin.security.impl.SecurityServiceImpl;
import ar.com.grupo306.sisopadmin.security.interfaces.SecurityService;
import ar.com.grupo306.sisopadmin.service.ApplicationService;
import ar.com.grupo306.sisopadmin.webservice.interfaces.SisopAdminWebService;

/**
 * Interfaz de acceso a los servicios de la aplicación de Sisop admin.
 *
 * @author Leandro
 */
public class SisopAdminServiceProvider {
	/** Servicio de persistencia asociado a Hibernate. */
	private static final PersistenceService HIBERNATE_PERSISTENCE_SERVICE = new HibernatePersistenceServiceImpl();
	
	/** Factory para crear proxys de administración de la persistencia acoplado al Hibernate.	 */
	private static final PersistenceManagerInterceptor HIBERNATE_PERSISTENCE_MANAGER_PROXY = new HibernatePersistenceManagerInterceptor();	
	
	/** Servicio de web asociado a XXX.	 */
	private static final SisopAdminWebService XXX_WEB_SERVICE = null;
	
	/** Servicio de seguridad.	 */
	private static final SecurityService SECURITY_SERVICE = new SecurityServiceImpl();
	
	/** Servicio de auditoria.	 */
	private static final AuditoryService AUDITORY_SERVICE = new AuditoryServiceImpl();
	
	/** Servicio para la generación de reportes   */
	private static final ReportsGeneratorService REPORT_SERVICE = new ReportsGeneratorServiceImpl();
	

	
	public static PersistenceService getPersistenceService() {
		return HIBERNATE_PERSISTENCE_SERVICE;
	}
	
	public static SisopAdminWebService getWebService() {
		return XXX_WEB_SERVICE;
	}
	
	public static PersistenceManagerInterceptor getPersistenceManagerInterceptor() {
		return HIBERNATE_PERSISTENCE_MANAGER_PROXY;
	}
	
	public static SecurityService getSecurityService() {
		return (SecurityService) makeTransactionalService(SECURITY_SERVICE);
	}
	
	public static AuditoryService getAuditoryService() {
		return (AuditoryService) makeTransactionalService(AUDITORY_SERVICE);
	}
	
	public static ReportsGeneratorService getReportsGeneratorService() {
		return (ReportsGeneratorService) REPORT_SERVICE;
	}
	
	
	
	
	/**
	 * Concede al servicio la capacidad de acceder a la base de datos mediante
	 * la administración de la transacción.
	 * 
	 * @param applicationService
	 * @return
	 */
	private static ApplicationService makeTransactionalService(ApplicationService applicationService) {
		return (ApplicationService) getPersistenceManagerInterceptor().create(applicationService, ".*");
	} 

}