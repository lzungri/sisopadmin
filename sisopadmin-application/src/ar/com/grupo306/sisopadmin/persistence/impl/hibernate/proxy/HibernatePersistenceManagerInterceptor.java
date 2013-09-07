package ar.com.grupo306.sisopadmin.persistence.impl.hibernate.proxy;

import java.lang.reflect.Method;
import java.util.Date;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.exceptions.core.BusinessException;
import ar.com.grupo306.commons.exceptions.core.SisopAdminSecurityException;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.sisopadmin.persistence.impl.common.proxy.PersistenceManagerInterceptor;
import ar.com.grupo306.sisopadmin.persistence.impl.hibernate.session.SessionManager;

/**
 * Proxy cuya responsabilidad será:<br>
 * <li>Iniciar y cerrar la Session según la configuración indicada.
 * <li>Realizar un commit o rollback según la configuración indicada.
 * 
 * @author Leandro
 */
public class HibernatePersistenceManagerInterceptor implements PersistenceManagerInterceptor, MethodInterceptor {
	private static Logger logger = Logger.getLogger(HibernatePersistenceManagerInterceptor.class);
	
	private Object target;
	private String regularExpression;	
	private boolean autoRollback;
	private Class transactionalAnnotation;

	public HibernatePersistenceManagerInterceptor() {
		super();
	}
	
	private HibernatePersistenceManagerInterceptor(Object target, String regularExpression, boolean autoRollback, Class transacionalAnnotation) {
		this.target = target;
		this.regularExpression = regularExpression;
		this.autoRollback = autoRollback;
		this.transactionalAnnotation = transacionalAnnotation;
	}
	
	
	
	public Object create(Object target) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(new HibernatePersistenceManagerInterceptor(target, null, false, Transactional.class));
		
		return enhancer.create();
	}

	public Object create(Object target, boolean autoRollback) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(new HibernatePersistenceManagerInterceptor(target, null, autoRollback, Transactional.class));
		
		return enhancer.create();		
	}
	
	public Object create(Object target, Class transactionalAnnotation, boolean autoRollback) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(new HibernatePersistenceManagerInterceptor(target, null, autoRollback, transactionalAnnotation));
		
		return enhancer.create();
	}

	public Object create(Object target, String regularExpression) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(new HibernatePersistenceManagerInterceptor(target, regularExpression, false, null));
		
		return enhancer.create();
	}

	public Object create(Object target, String regularExpression, boolean autoRollback) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(new HibernatePersistenceManagerInterceptor(target, regularExpression, autoRollback, null));
		
		return enhancer.create();
	}
	
	
	
	/**
	 * Método que se ejecutará al invocar un método sobre el objeto proxiado.
	 */
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		if(this.isTransactionalMethod(method)) {
			return this.invokeMethodWithTransaction(method, args, proxy);
		}
		else {
			return proxy.invoke(this.target, args); 
		}
	}
	
	/**
	 * Indica si el Method corresponde a un método transaccional.
	 * 
	 * @param method
	 */
	private boolean isTransactionalMethod(Method method) {
		if(this.transactionalAnnotation != null) {
			return method.getAnnotation(this.transactionalAnnotation) != null;
		}
		
		return method.getName().matches(this.regularExpression) && !method.getName().equals("finalize");
	}
	
	/**
	 * Ejecuta el método creando previamente la Session y Transaction. 
	 */
	private Object invokeMethodWithTransaction(Method method, Object []args, MethodProxy proxy) throws Throwable {
		/* Se abre la Session que administrará los objetos. */
		SessionManager.openSession();
		
		/* Se inicia la Transaction con la base. */
		SessionManager.beginTransaction();
		
		try {
			
			logger.debug("Se comienza la ejecucion del metodo: " + method.getName());
			long initialTime = new Date().getTime();
			
			/* Se invoca sobre el target. Asegurándole una session y transaction creada. */
			Object result = proxy.invoke(this.target, args);
			
			long elapsedTime = new Date().getTime() - initialTime;
			logger.debug("Se finaliza la ejecucion del metodo: "	+ method.getName() + " en un tiempo de: " + elapsedTime + "ms.");

			
			/* Si está habilitado el autoRollback no se comitea. */
			if(this.autoRollback) {
				SessionManager.rollbackTransaction();
				logger.debug("Se ejecuto rollBack() luego de invocar al metodo: " + method.getName());				
			}
			else {
				SessionManager.commitTransaction();
				logger.debug("Se ejecuto commit() luego de invocar al metodo: " + method.getName());				
			}
			
			return result;
		}
		catch(Exception exception) {
			SessionManager.rollbackTransaction();
			logger.debug("Se ejecuto rollBack() luego de invocar al metodo: " + method.getName());
			
			if(exception instanceof BusinessException || exception instanceof SisopAdminSecurityException) {
				throw exception;
			}
			// Y se va otro instanceof...
			if(exception instanceof ConstraintViolationException) {
				throw ExceptionFactory.createBusinessException("No es posible realizar la operación ya que existen otras entidades que la referencian.");
			}
			throw ExceptionFactory.createProgramException("Falla al ejecutar el metodo: " + method.getName() + " sobre el objeto de clase: " + this.target.getClass() + ". Se realizo rollBack().", exception);
		}
		finally {
			if(!SessionManager.hasOpenTransactions()) {
				SessionManager.getCurrentSession().close();
			}
		}
	}
	
}