package ar.com.grupo306.sisopadmin.web.usecases.register;

import ar.com.grupo306.sisopadmin.web.usecases.domain.HomeUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.LoginUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.archivos.ABMArchivosUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.ABMEncuestaUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.error.ErrorUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.ABMGruposUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.AdministrarInformacionCatedraUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.AdministrarIntegranteUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.AsignarAyudantesACoordinadorUseCase;
import ar.com.grupo306.usecasefwk.struts.plugin.UseCaseRegister;
import ar.com.grupo306.usecasefwk.usecases.domain.test.CUDosUseCase;
import ar.com.grupo306.usecasefwk.usecases.domain.test.CUTresUseCase;
import ar.com.grupo306.usecasefwk.usecases.domain.test.CUUnoUseCase;
import ar.com.grupo306.usecasefwk.usecases.registry.CompositeRegistryElement;
import ar.com.grupo306.usecasefwk.usecases.registry.UseCaseRegistry;

/**
 * UseCaseRegister propio de la aplicación de Sisop Admin.
 *
 * @author Leandro
 */
public class SisopAdminUseCaseRegister extends UseCaseRegister {

	public void registerUseCases(UseCaseRegistry useCaseRegistry) {
		useCaseRegistry
		
			.registerUseCases(
				new CompositeRegistryElement("Tests")
					.addUseCase(new CUUnoUseCase())
					.addUseCase(new CUDosUseCase())
					.addUseCase(new CUTresUseCase()))
					
			.registerUseCases(
				new CompositeRegistryElement("Sisop Admin")
					.addUseCase(new HomeUseCase())					
					.addUseCases(
						new CompositeRegistryElement("Integrantes")
							.addUseCase(new AdministrarIntegranteUseCase())
							.addUseCase(new ABMIntegranteUseCase())
							.addUseCase(new AsignarAyudantesACoordinadorUseCase())
					)
					
					.addUseCases(
						new CompositeRegistryElement("Grupos")
							.addUseCase(new ABMGruposUseCase())
					)
					
					.addUseCases(
						new CompositeRegistryElement("Encuestas")
							.addUseCase(new ABMEncuestaUseCase())
							.addUseCase(new AdministrarEncuestaUseCase())
					) 
					.addUseCases(
						new CompositeRegistryElement("Recursos de la cátedra")
							.addUseCase(new ABMInformacionCatedraUseCase())
							.addUseCase(new AdministrarInformacionCatedraUseCase())
							.addUseCase(new ABMArchivosUseCase())
					)				
			)
			.registerUseCase(new ErrorUseCase())
			.registerUseCase(new LoginUseCase());
			
	}
}