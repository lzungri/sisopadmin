package ar.com.grupo306.sisopadmin.persistence.impl.common.proxy;

import ar.com.grupo306.sisopadmin.service.ApplicationService;


/**
 * Interfaz com�n a los proxies para la administraci�n de la persistencia.
 *
 * @author Leandro
 */
public interface PersistenceManagerInterceptor extends ApplicationService {
	
	/**
	 * <p>Crea y retorna un proxy sobre el target.
	 *
	 * <p>Cada invocaci�n a un m�todo sobre el target provocar� el inicio 
	 * de una transacci�n.
	 * <p>En caso de ocurrir una Exception se realizar� un rollback sobre la transacci�n.
	 * <p>En caso de ejecutar satisfactoriamente el m�todo se realizar� un commit.
	 * 
	 * @param target: Objeto sobre el cu�l se realizar� el proxy.
	 * @return Object: Proxy creado.
	 */
	public Object create(Object target);
	
	/**
	 * <p>Crea y retorna un proxy sobre el target.
	 *
	 * <p>Cada invocaci�n a un m�todo sobre el target provocar� el inicio 
	 * de una transacci�n.
	 * <p>En caso de ocurrir una Exception se realizar� un rollback sobre la transacci�n.
	 * <p>En caso de ejecutar satisfactoriamente el m�todo se realizar� un commit.
	 * 
	 * @param target: Objeto sobre el cu�l se realizar� el proxy.
	 * @param transactionalAnnotation: Clase de la Annotation que indicar� si un m�todo
	 * es transaccional.
	 * @return Object: Proxy creado.
	 */
	public Object create(Object target, Class transactionalAnnotation, boolean autoRollback);
	
	/**
	 * <p>Crea y retorna un proxy sobre el target.
	 *
	 * <p>Cada invocaci�n a un m�todo sobre el target provocar� el inicio 
	 * de una transacci�n.
	 * 	<p>Por defecto realiza rollback si autoRollback es true.
	 * 
	 * @param target: Objeto sobre el cu�l se realizar� el proxy.
	 * @param autoRollback
	 * @return Object: Proxy creado.
	 */
	public Object create(Object target, boolean autoRollback);


	/**
	 * <p>Crea y retorna un proxy sobre el target.
	 *
	 * <p>Cada invocaci�n a un m�todo cuyo nombre cumpla con la regular expression 
	 * provocar� el inicio de una transacci�n.
	 * <p>En caso de ocurrir una Exception se realizar� un rollback sobre la transacci�n.
	 * <p>En caso de ejecutar satisfactoriamente el m�todo se realizar� un commit.
	 * 
	 * @param target: Objeto sobre el cu�l se realizar� el proxy.
	 * @return Object: Proxy creado.
	 */
	public Object create(Object target, String regularExpression);
	
	/**
	 * <p>Crea y retorna un proxy sobre el target.
	 *
	 * <p>Cada invocaci�n a un m�todo cuyo nombre cumpla con la regular expression 
	 * provocar� el inicio de una transacci�n.
	 * <p>Por defecto realiza rollback si autoRollback es true
	 * 
	 * @param target: Objeto sobre el cu�l se realizar� el proxy.
	 * @param regularExpression
	 * @param autoRollback
	 * @return Object: Proxy creado.
	 */
	public Object create(Object target, String regularExpression, boolean autoRollback);

}