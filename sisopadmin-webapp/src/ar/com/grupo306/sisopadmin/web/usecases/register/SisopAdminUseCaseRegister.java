package ar.com.grupo306.sisopadmin.web.usecases.register;

import ar.com.grupo306.sisopadmin.web.usecases.domain.HomeUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.LoginUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.archivos.ABMArchivosUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.archivos.AdministrarArchivosUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.ABMEncuestaUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.error.ErrorUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.ABMGruposUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.AdministrarInformacionCatedraUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.AdministrarIntegranteUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.AsignarAyudantesACoordinadorUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.AsignarGruposAAyudantesUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.AsignarGruposAIntegranteUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.mensajes.ABMMensajesUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.mensajes.AdministrarMensajesUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.roles.ABMRolesUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.roles.ABMRolesUsuarioUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.roles.AdministrarRolesUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMConsignaUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMFaseUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMTpUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.VerInformacionTPUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.AdministrarEntregasUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.NotificarEntregaUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.VisualizarAvanceUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.ABMEvaluacionFasePorPlantillaUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.AdministrarEvaluacionesFaseUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.AdministrarInformesUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.AdministrarPlantillasAEvaluarUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas.ABMItemUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas.ABMPlantillaUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas.AdministrarConsignasUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas.AdministrarPlantillasUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas.SelectFaseUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.usuarios.cambiarPasswordUseCase;
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
					.addUseCase(new CUTresUseCase())
			)
			.registerUseCase(new HomeUseCase())
			.registerUseCases(
				new CompositeRegistryElement("Integrantes")
					.addUseCase(new AdministrarIntegranteUseCase())
					.addUseCase(new ABMIntegranteUseCase())
					.addUseCase(new AsignarAyudantesACoordinadorUseCase())
					.addUseCase(new AsignarGruposAAyudantesUseCase())
					.addUseCase(new AsignarGruposAIntegranteUseCase())
			)
			.registerUseCases(
				new CompositeRegistryElement("Grupos")
					.addUseCase(new ABMGruposUseCase())
					.addUseCase(new AdministrarGruposUseCase())
			)
			.registerUseCases(
				new CompositeRegistryElement("Encuestas")
					.addUseCase(new ABMEncuestaUseCase())
					.addUseCase(new AdministrarEncuestaUseCase())
			)
			.registerUseCases(
				new CompositeRegistryElement("Recursos de la cátedra")
					.addUseCase(new ABMInformacionCatedraUseCase())
					.addUseCase(new AdministrarInformacionCatedraUseCase())
					.addUseCase(new ABMArchivosUseCase())
					.addUseCase(new AdministrarArchivosUseCase())
			)
			.registerUseCases(
				new CompositeRegistryElement("Roles de la cátedra")
					.addUseCase(new ABMRolesUseCase())
					.addUseCase(new AdministrarRolesUseCase())
					.addUseCase(new ABMRolesUsuarioUseCase())
			)
			.registerUseCases(
				new CompositeRegistryElement("Trabajos Prácticos")
					.addUseCase(new ABMTpUseCase())
					.addUseCase(new ABMFaseUseCase())
					.addUseCase(new ABMConsignaUseCase())
					.addUseCase(new AdministrarTPsUseCase())
					.addUseCase(new VerInformacionTPUseCase())
					.addUseCases(
						new CompositeRegistryElement("Evaluación")
							.addUseCase(new AdministrarPlantillasAEvaluarUseCase())
							.addUseCase(new AdministrarEvaluacionesFaseUseCase())
							.addUseCase(new AdministrarInformesUseCase())
							.addUseCase(new ABMEvaluacionFasePorPlantillaUseCase())
							.addUseCases(
									new CompositeRegistryElement("Plantillas")
									.addUseCase(new SelectFaseUseCase())
									.addUseCase(new AdministrarPlantillasUseCase())
									.addUseCase(new ABMPlantillaUseCase())
									.addUseCase(new ABMItemUseCase())
									.addUseCase(new AdministrarConsignasUseCase())
							)
					)
					.addUseCases(
							new CompositeRegistryElement("Entregas")
								.addUseCase(new NotificarEntregaUseCase())
								.addUseCase(new AdministrarEntregasUseCase())
								.addUseCase(new VisualizarAvanceUseCase())
					)
			)
			.registerUseCases(
				new CompositeRegistryElement("Comunicación")
					.addUseCase(new AdministrarMensajesUseCase())
					.addUseCase(new ABMMensajesUseCase())
			)
			.registerUseCase(new ErrorUseCase())
			.registerUseCase(new LoginUseCase())
			.registerUseCase(new cambiarPasswordUseCase());
	}
}