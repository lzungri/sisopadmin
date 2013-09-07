package ar.com.grupo306.sisopadmin.persistence.tests.hibernate;

import ar.com.grupo306.sisopadmin.persistence.impl.hibernate.proxy.HibernatePersistenceManagerInterceptor;
import junit.framework.TestCase;

public class PersistenceServiceTest extends TestCase{
	
//	public static boolean testCriteria(){
//		UseCaseModule useCaseModule = (UseCaseModule)new HibernatePersistenceManagerInterceptor().create(new UseCaseModule(),false);
//		return !useCaseModule.findPlantillaEncuestaById(new Long(131072)).isEmpty();
//	}
	
	
	public static boolean testPlantillaEncuesta(){
		UseCaseModule useCaseModule = (UseCaseModule)new HibernatePersistenceManagerInterceptor().create(new UseCaseModule(),false);
		useCaseModule.crearPlantillaEncuesta();
		return true;
	}
	
	public static void testUsuarios(){
		UseCaseModule useCaseModule = (UseCaseModule)new HibernatePersistenceManagerInterceptor().create(new UseCaseModule(),false);
        useCaseModule.testUsuarios();
        //return true;
	}	
	
	public static void testCargarGrupo(){
		UseCaseModule useCaseModule = (UseCaseModule)new HibernatePersistenceManagerInterceptor().create(new UseCaseModule(),false);
		useCaseModule.cargarGrupo();
	}
}
