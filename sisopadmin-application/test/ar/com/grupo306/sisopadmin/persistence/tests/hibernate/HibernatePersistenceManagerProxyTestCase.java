package ar.com.grupo306.sisopadmin.persistence.tests.hibernate;

import junit.framework.TestCase;
import ar.com.grupo306.sisopadmin.persistence.impl.common.proxy.PersistenceManagerInterceptor;
import ar.com.grupo306.sisopadmin.persistence.impl.hibernate.proxy.HibernatePersistenceManagerInterceptor;

/**
 * Test del proxy de persistencia.
 *
 * @author Leandro
 */
public class HibernatePersistenceManagerProxyTestCase extends TestCase {
	private static PersistenceManagerInterceptor proxy = new HibernatePersistenceManagerInterceptor();
	
	public void testInstantiation() {
		proxy.create(new TestObject());
	}
	
	public void testInvocation() {
		TestObject testObject = new TestObject();
		TestObject testObjectProxy = (TestObject) proxy.create(testObject, "transactional.*");
		
		testObject.setApellido("Grillo");
		testObject.setNombre("Pepe");
		testObject.setEdad(new Integer(1));
		
		System.out.println(testObject.getNombre());
		System.out.println("Invocacion al proxy...");
		System.out.println(testObjectProxy.getNombre());
		System.out.println(testObjectProxy.transactionalMetodito());
	}
	
}