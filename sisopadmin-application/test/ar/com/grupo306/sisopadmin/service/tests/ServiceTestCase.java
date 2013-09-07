package ar.com.grupo306.sisopadmin.service.tests;

import junit.framework.TestCase;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * @author Leandro
 */
public class ServiceTestCase extends TestCase {

	public void testSecurityService() {
		SisopAdminServiceProvider.getSecurityService().isValidUser("test", "test");
	}
	
}