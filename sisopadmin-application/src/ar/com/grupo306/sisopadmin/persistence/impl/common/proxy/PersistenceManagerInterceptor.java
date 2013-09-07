package ar.com.grupo306.sisopadmin.persistence.impl.common.proxy;

import ar.com.grupo306.sisopadmin.service.ApplicationService;


/**
 * Interfaz común a los proxies para la administración de la persistencia.
 *
 * @author Leandro
 */
public interface PersistenceManagerInterceptor extends ApplicationService {
	
	/**
	 * <p>Crea y retorna un proxy sobre el target.
	 *
	 * <p>Cada invocación a un método sobre el target provocará el inicio 
	 * de una transacción.
	 * <p>En caso de ocurrir una Exception se realizará un rollback sobre la transacción.
	 * <p>En caso de ejecutar satisfactoriamente el método se realizará un commit.
	 * 
	 * @param target: Objeto sobre el cuál se realizará el proxy.
	 * @return Object: Proxy creado.
	 */
	public Object create(Object target);
	
	/**
	 * <p>Crea y retorna un proxy sobre el target.
	 *
	 * <p>Cada invocación a un método sobre el target provocará el inicio 
	 * de una transacción.
	 * <p>En caso de ocurrir una Exception se realizará un rollback sobre la transacción.
	 * <p>En caso de ejecutar satisfactoriamente el método se realizará un commit.
	 * 
	 * @param target: Objeto sobre el cuál se realizará el proxy.
	 * @param transactionalAnnotation: Clase de la Annotation que indicará si un método
	 * es transaccional.
	 * @return Object: Proxy creado.
	 */
	public Object create(Object target, Class transactionalAnnotation, boolean autoRollback);
	
	/**
	 * <p>Crea y retorna un proxy sobre el target.
	 *
	 * <p>Cada invocación a un método sobre el target provocará el inicio 
	 * de una transacción.
	 * 	<p>Por defecto realiza rollback si autoRollback es true.
	 * 
	 * @param target: Objeto sobre el cuál se realizará el proxy.
	 * @param autoRollback
	 * @return Object: Proxy creado.
	 */
	public Object create(Object target, boolean autoRollback);


	/**
	 * <p>Crea y retorna un proxy sobre el target.
	 *
	 * <p>Cada invocación a un método cuyo nombre cumpla con la regular expression 
	 * provocará el inicio de una transacción.
	 * <p>En caso de ocurrir una Exception se realizará un rollback sobre la transacción.
	 * <p>En caso de ejecutar satisfactoriamente el método se realizará un commit.
	 * 
	 * @param target: Objeto sobre el cuál se realizará el proxy.
	 * @return Object: Proxy creado.
	 */
	public Object create(Object target, String regularExpression);
	
	/**
	 * <p>Crea y retorna un proxy sobre el target.
	 *
	 * <p>Cada invocación a un método cuyo nombre cumpla con la regular expression 
	 * provocará el inicio de una transacción.
	 * <p>Por defecto realiza rollback si autoRollback es true
	 * 
	 * @param target: Objeto sobre el cuál se realizará el proxy.
	 * @param regularExpression
	 * @param autoRollback
	 * @return Object: Proxy creado.
	 */
	public Object create(Object target, String regularExpression, boolean autoRollback);

}