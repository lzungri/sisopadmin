package ar.com.grupo306.sisopadmin.web.usecases.permission.populator;

import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.RepositoryPopulator;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.TablePopulator;

/**
 * Clase ejecutable encargada de popular la base con los permisos requeridos
 * por cada uno de los UseCases registrados en el SisopAdminUseCaseRegister.
 *
 * @author Leandro
 */
public class UseCasePermissionsRepositoryPopulator extends TablePopulator {

	public static void main(String[] args) {
		new RepositoryPopulator()
			.addTP(new UseCasePermissionsTablePopulator())
		.run();
	}
	
}