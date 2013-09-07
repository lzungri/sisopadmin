package ar.com.grupo306.sisopadmin.persistence.impl.common.populator;

import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain.EvaluacionFasePorPlantillaTablePopulator;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain.EventoNotificableTablePopulator;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain.PlantillaCorreccionTablePopulator;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain.ArchivoTablePopulator;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain.PlantillaEncuestaTablePopulator;
/**
 * Clase ejecutable encargada de popular la bd con datos útiles para la
 * etapa de desarrollo del sistema.
 * 
 * @author Leandro
 */
public class SisopAdminDevelopmentRepositoryPopulator {
	
	public static void main(String args[]) {
		RepositoryPopulator repositoryPopulator = new RepositoryPopulator();
		
		repositoryPopulator
			.addTP(new EventoNotificableTablePopulator())
			.addTP(new EvaluacionFasePorPlantillaTablePopulator())
			.addTP(new PlantillaCorreccionTablePopulator())
			.addTP(new ArchivoTablePopulator())
			.addTP(new PlantillaEncuestaTablePopulator())
		.run();
	}
}