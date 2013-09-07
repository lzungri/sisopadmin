package ar.com.grupo306.sisopadmin.persistence.impl.common.populator;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

import ar.com.grupo306.commons.exceptions.core.ProgramException;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.persistence.interfaces.PersistenceService;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * Entidad encargada de popular una tabla de la base de datos.
 * Se ejecutarán dentro de una transacción aquellos métodos que tengan el 
 * prefijo "populate", ejemplo: "populateXXX()".
 * 
 * @author lzungri
 */
public class TablePopulator {
	private static final String METHOD_PREFIX_POPULATE = "populate";
	
	/**
	 * Se guarda una instancia de sí mismo pero con un proxy de Session.
	 * Esto se hace para reutilizar el proxy que administra la persistencia.
	 */
	private TablePopulator thisProxied;
	
	/**
	 * Ejecuta los métodos encargados de popular la base.
	 * Los mismos deben tener el prefijo: "populate", es decir: populateXXX().
	 */
	public void doPopulate() {
		Collection<Method> populateMethods = this.getPopulateMethods();

		this.thisProxied = (TablePopulator) SisopAdminServiceProvider.
			getPersistenceManagerInterceptor().create(this, "populate.*");
		
		for(Iterator it = populateMethods.iterator(); it.hasNext(); ) {
			Method populateMethod = (Method) it.next();

			try {
				/* El método se invoca sobre la instancia actual que se encuentra
				 * proxiada, por la tanto la session y transaction la creara el proxy. */
				populateMethod.invoke(this.thisProxied, (Object []) null);
			}
			catch(Exception excep) {
				throw new ProgramException("Falla al intentar ejecutar el metodo: " + populateMethod.getName(), excep);
			}
		}
	}
	
	/**
	 * Retorna los métodos utilizados para popular la base.
	 */
	private Collection<Method> getPopulateMethods() {
		Method methods[] = getClass().getMethods();
		Collection<Method> populateMethods = CollectionFactory.createCollection(Method.class);
		
		for(int index = 0; index < methods.length; ++index) {
			if(this.isPopulateMethod(methods[index])) {
				populateMethods.add(methods[index]);
			}
		}

		return populateMethods;
	}
	
	private boolean isPopulateMethod(Method method) {
		return 
			method.getName().startsWith(METHOD_PREFIX_POPULATE) && 
			method.getParameterTypes().length == 0;
	}
	
	public void save(ModelObject persistent) {
		this.getPersistenceService().save(persistent);
	}
	
	public PersistenceService getPersistenceService() {
		return SisopAdminServiceProvider.getPersistenceService(); 
	}

}