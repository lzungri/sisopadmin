package ar.com.grupo306.sisopadmin.persistence.impl.common.populator;

import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain.InformacionCatedraTablePopulator;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain.PermisoTablePopulator;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain.RolTablePopulator;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain.UsuarioTablePopulator;

/**
 * Clase ejecutable encargada de popular la bd con los datos básicos de la 
 * aplicación, es decir, aquellos que son necesarios para ejecutar la misma.
 *
 * @author Leandro
 */
public class SisopAdminBasicRepositoryPopulator {
	
	public static void main(String args[]) {
		new RepositoryPopulator()
			.addTP(new PermisoTablePopulator())
			.addTP(new RolTablePopulator())
			.addTP(new UsuarioTablePopulator())
			.addTP(new InformacionCatedraTablePopulator())
		.run();
	}
	
}