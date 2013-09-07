package ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Permiso;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Rol;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.TablePopulator;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * @author Leandro
 */
public class RolTablePopulator extends TablePopulator {
		
	public void populateUsuariosSistemaRol() {
		Rol rol = new Rol();
		rol.setDomainCode(Rol.ROL_USUARIO_SISTEMA);		
		rol.setNombre("Usuario del Sistema");
		rol.setDescripcion("Rol que permite el uso básico del sistema.");
		rol.setDeSistema(true);
		
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.usuarios.cambiarPasswordUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.mensajes.AdministrarMensajesUseCaseModel.initUseCase"));
		
		save(rol);
	}
	
	public void populateAdminTps() {
		Rol rol = new Rol();
		rol.setDomainCode(Rol.ROL_ADMIN_TPS);		
		rol.setNombre("Administrador de TPs");
		rol.setDescripcion("Rol de administración de TPs");
		
		
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTpsUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCaseModel.select"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCaseModel.select.operation"));		
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMTpUseCaseModel.initUseCase"));		
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMTpUseCaseModel.createAccept"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMTpUseCaseModel.createAccept.create"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMTpUseCaseModel.editAccept"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMTpUseCaseModel.editAccept.edit"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMTpUseCaseModel.removeAccept"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMTpUseCaseModel.removeAccept.remove"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMFaseUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMFaseUseCaseModel.createAccept"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMFaseUseCaseModel.createAccept.create"));	
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMFaseUseCaseModel.editAccept"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMFaseUseCaseModel.editAccept.edit"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMFaseUseCaseModel.removeAccept"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMFaseUseCaseModel.removeAccept.remove"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMConsignaUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMConsignaUseCaseModel.createAccept"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMConsignaUseCaseModel.createAccept.create"));	
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMConsignaUseCaseModel.editAccept"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMConsignaUseCaseModel.editAccept.edit"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMConsignaUseCaseModel.removeAccept"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMConsignaUseCaseModel.removeAccept.remove"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCaseModel.view"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCaseModel.edit"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCaseModel.remove"));
		
		
		save(rol);
	}

	public void populateAdminIntegrantes() {
		Rol rol = new Rol();
		rol.setDomainCode(Rol.ROL_ADMIN_INTEGRANTE);		
		rol.setNombre("Administrador de Integrantes de Cátedra");
		rol.setDescripcion("Rol de administración de Integrantes de Cátedra");
		
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.AdministrarIntegranteUseCaseModel.initUseCase"));	
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.initUseCase.create"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.initUseCase.edit"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.AdministrarIntegranteUseCaseModel.select"));									
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.initUseCase.view"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.initUseCase.remove"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.createAccept"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.createAccept.create"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.editAccept"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.editAccept.edit"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.removeAccept"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.removeAccept.remove"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.AsignarGruposAAyudantesUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.AsignarAyudantesACoordinadorUseCaseModel.initUseCase"));
		
	}
	
	public void populateAdminPlantillasCorreccion() {
		Rol rol = new Rol();
		rol.setDomainCode(Rol.ROL_ADMIN_PLANTILLAS_CORRECCION);		
		rol.setNombre("Administrador de Plantillas de Corrección");
		rol.setDescripcion("Rol de administración de Plantillas de Corrección");
		
		//PLANTILLAS DE CORRECCION
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas.AdministrarPlantillasUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas.AdministrarPlantillasUseCaseModel.select"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas.AdministrarPlantillasUseCaseModel.select.operation"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas.SelectFaseUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas.ABMPlantillaUseCaseModel.initUseCase"));		
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas.ABMItemUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas.ABMItemUseCaseModel.createAccept"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas.ABMItemUseCaseModel.createAccept.create"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas.ABMItemUseCaseModel.editAccept"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas.ABMItemUseCaseModel.editAccept.edit"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas.ABMItemUseCaseModel.removeAccept"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas.ABMItemUseCaseModel.removeAccept.remove"));		
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas.AdministrarConsignasUseCaseModel.select.operation"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas.AdministrarConsignasUseCaseModel.select"));
	}
	
	public void populateAdminGrupos(){
		Rol rol = new Rol();
		rol.setDomainCode(Rol.ROL_ADMIN_GRUPOS);
		rol.setNombre("Administración de Grupos");
		rol.setDescripcion("Rol de Administración de Grupos de Trabajo Práctico");

		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.select"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.select.operation"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.view"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.edit"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.remove"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.find"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.confirm"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.ABMGruposUseCaseModel.seleccionarAyudante"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.AsignarGruposAIntegranteUseCaseModel.initUseCase"));		
	}
	
	public void populateAdminRoles(){
		Rol rol = new Rol();
		rol.setDomainCode(Rol.ROL_ADMIN_ROLES);
		rol.setNombre("Administración de Roles");
		rol.setDescripcion("Rol de Administración de Roles de la aplicación");		
		
		//ABM Roles
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.roles.ABMRolesUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.roles.ABMRolesUsuarioUseCaseModel.initUseCase"));
		//Administrar Roles
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.roles.AdministrarRolesUseCaseModel.initUseCase"));
	}
	
	public void populateAdminPlantillasEncuestas() {
		Rol adminRol = new Rol();
		adminRol.setDomainCode(Rol.ROL_ADMIN_ENCUESTAS);		
		adminRol.setNombre("Administrador de encuestas");
		adminRol.setDescripcion("Rol de Administrador de encuestas");
		
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.ABMEncuestaUseCaseModel.initUseCase"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.ABMEncuestaUseCaseModel.aceptar"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.initUseCase"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.initUseCase.operation"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.cargarEncuesta.operation"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.eliminarEncuesta.operation"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.modificarEncuesta.operation"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.visualizarResultadoEncuesta.operation"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.consultarEncuesta.operation"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.cargarEncuesta"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.eliminarEncuesta"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.modificarEncuesta"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.visualizarResultadoEncuesta"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.consultarEncuesta"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.ABMEncuestaUseCaseModel.cargarPuntoAEncuestar"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.ABMEncuestaUseCaseModel.cargarPuntoAEncuestar.operation"));
		save(adminRol);
	}
	
	public void populateAdminRecursosRol() {
		Rol adminRol = new Rol();
		adminRol.setDomainCode(Rol.ROL_ADMIN_RECURSOS);		
		adminRol.setNombre("Administrador de Recursos de la cátedra");
		adminRol.setDescripcion("Rol de Administrador de Recursos de la cátedra");
		
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.archivos.ABMArchivosUseCaseModel.initUseCase"));
		//adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.archivos.AdministrarArchivosUseCaseModel.initUseCase"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.archivos.AdministrarArchivosUseCaseModel.altaArchivo"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.archivos.AdministrarArchivosUseCaseModel.bajaArchivo"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.initUseCase"));		
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.AdministrarInformacionCatedraUseCaseModel.initUseCase"));
		
		save(adminRol);
	}
	
	public void populateAdminEvaluacionRol(){
		Rol adminRol = new Rol();
		adminRol.setDomainCode(Rol.ROL_ADMIN_EVALUACION);
		adminRol.setNombre("Administrador de Evaluacion de los TPs");
		adminRol.setDescripcion("Rol de Administrador de Evaluacion de los TPs");
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.AdministrarEvaluacionesFaseUseCaseModel.initUseCase"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.AdministrarPlantillasAEvaluarUseCaseModel.initUseCase"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.ABMEvaluacionFasePorPlantillaUseCaseModel.initUseCase"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.AdministrarInformesUseCaseModel.initUseCase"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.AdministrarInformesUseCaseModel.seleccionarAyudante"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.AdministrarInformesUseCaseModel.seleccionarGrupo"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.AdministrarInformesUseCaseModel.enviarInforme"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.AdministrarInformesUseCaseModel.remove"));
				
		save(adminRol);
		
	}
	
	public void populateUsuarioPlantillasRol() {
		Rol adminRol = new Rol();
		adminRol.setDomainCode(Rol.ROL_USUARIO_ENCUESTAS);		
		adminRol.setNombre("Realizador de encuestas");
		adminRol.setDescripcion("Rol que permite completar encuestas");
		
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.ABMEncuestaUseCaseModel.initUseCase"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.ABMEncuestaUseCaseModel.initUseCase.edit"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.ABMEncuestaUseCaseModel.aceptar"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.initUseCase"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.initUseCase.operation"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.llenarEncuesta"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.llenarEncuesta.operation"));
				
		save(adminRol);
	}
		
	public void populateCoordinadorRol() {
		Rol rol = new Rol();
		rol.setDomainCode(Rol.ROL_COORDINADOR);		
		rol.setNombre("Coordinador");
		rol.setDescripcion("Rol de Coordinador");
		rol.setDeSistema(true);
			
		incluirPermisos(rol, Rol.findMeByDomainCode(Rol.ROL_ADMIN_INTEGRANTE));
		incluirPermisos(rol, Rol.findMeByDomainCode(Rol.ROL_ADMIN_PLANTILLAS_CORRECCION));
		incluirPermisos(rol, Rol.findMeByDomainCode(Rol.ROL_ADMIN_TPS));
		incluirPermisos(rol, Rol.findMeByDomainCode(Rol.ROL_ADMIN_GRUPOS));
		incluirPermisos(rol,Rol.findMeByDomainCode( Rol.ROL_ADMIN_EVALUACION));
		incluirPermisos(rol,Rol.findMeByDomainCode(Rol.ROL_USUARIO_SISTEMA));
		incluirPermisos(rol,Rol.findMeByDomainCode(Rol.ROL_ADMIN_ENCUESTAS));
		incluirPermisos(rol,Rol.findMeByDomainCode(Rol.ROL_ADMIN_RECURSOS));
				
		//ENTREGAS
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.NotificarEntregaUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.NotificarEntregaUseCaseModel.initUseCase.view"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.AdministrarEntregasUseCaseModel.select"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.AdministrarEntregasUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.AdministrarEntregasUseCaseModel.seleccionarGrupo"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.VisualizarAvanceUseCaseModel.initUseCase"));

		save(rol);		
	}
	
	public void populateAyudanteRol() {
		Rol rol = new Rol();
		rol.setDomainCode(Rol.ROL_AYUDANTE);		
		rol.setNombre("Ayudante");
		rol.setDescripcion("Rol de Ayudante de Cátedra");
		rol.setDeSistema(true);
		
		incluirPermisos(rol,Rol.findMeByDomainCode(Rol.ROL_USUARIO_SISTEMA));
		incluirPermisos(rol,Rol.findMeByDomainCode(Rol.ROL_USUARIO_ENCUESTAS));
		
		//GRUPOS
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.select"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.find"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.view"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.edit"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.select"));
		
		//TPs
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTpsUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCaseModel.select"));		
		
		//EVALUACION
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.AdministrarPlantillasAEvaluarUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.ABMEvaluacionFasePorPlantillaUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.AdministrarInformesUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.AdministrarInformesUseCaseModel.enviarInforme"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.AdministrarInformesUseCaseModel.seleccionarGrupo"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.AdministrarInformesUseCaseModel.remove"));
		
		//ENTREGAS
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.NotificarEntregaUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.NotificarEntregaUseCaseModel.initUseCase.view"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.AdministrarEntregasUseCaseModel.select"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.AdministrarEntregasUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.AdministrarEntregasUseCaseModel.seleccionarGrupo"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.VisualizarAvanceUseCaseModel.initUseCase"));
				
		save(rol);
	}
	
	
//	public void populatePedorroRol() {
//		Rol rol = new Rol();
//		rol.setDomainCode(Rol.ROL_PEDORRO);		
//		rol.setNombre("Test");
//		rol.setDescripcion("Rol para testear la aplicación");
//		
//		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUUnoUseCaseModel.initUseCase"));
//		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUDosUseCaseModel.initUseCase"));
//		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUTresUseCaseModel.initUseCase"));
//		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUDosUseCaseModel.volverDeCUTres"));
//		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUDosUseCaseModel.initUseCase.edit"));
//		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUTresUseCaseModel.initUseCase.edit"));		
//		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUDosUseCaseModel.volverDeCUTres.edit"));
//		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUTresUseCaseModel.getValor11.edit"));
//		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUTresUseCaseModel.getValor11"));
//		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUTresUseCaseModel.setValor11"));
//		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUTresUseCaseModel.getValor12"));
//		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUTresUseCaseModel.setValor12"));
//		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUTresUseCaseModel.cancelUseCase"));
//		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUUnoUseCaseModel.select"));
//		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.initUseCase"));
//		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.AdministrarInformacionCatedraUseCaseModel.initUseCase"));		
//		
//		save(rol);
//	}
	
	
	public void populateGrupoRegistrado() {
		Rol rol = new Rol();
		rol.setDomainCode(Rol.ROL_GRUPO_REGISTRADO);		
		rol.setNombre("Grupo Registrado");
		rol.setDescripcion("Rol de grupo registrado");	
		rol.setDeSistema(true);
		
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.usuarios.cambiarPasswordUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.view"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.edit"));	
		
		save(rol);
	}
	
	public void populateGrupoInscripto() {
		Rol rol = new Rol();
		rol.setDomainCode(Rol.ROL_GRUPO_INSCRIPTO);		
		rol.setNombre("Grupo Inscripto");
		rol.setDescripcion("Rol de grupo inscripto");
		rol.setDeSistema(true);
			
		incluirPermisos(rol,Rol.findMeByDomainCode(Rol.ROL_USUARIO_ENCUESTAS));		
		incluirPermisos(rol,Rol.findMeByDomainCode(Rol.ROL_USUARIO_SISTEMA));	
				
		//TPs
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTpsUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTpsUseCaseModel.select"));
		
		//Informes de evaluación
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.AdministrarInformesUseCaseModel.initUseCase"));

		//Entregas
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.NotificarEntregaUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.NotificarEntregaUseCaseModel.initUseCase.view"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.NotificarEntregaUseCaseModel.initUseCase.create"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.AdministrarEntregasUseCaseModel.select"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.AdministrarEntregasUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.VisualizarAvanceUseCaseModel.initUseCase"));
		
		save(rol);
	}
	
	public void populateAdminRol() {
		//Roles que no posee el admin
		List list = CollectionFactory.createList(String.class);
		list.add(Rol.ROL_USUARIO_ENCUESTAS);
		list.add(Rol.ROL_GRUPO_INSCRIPTO);
		list.add(Rol.ROL_GRUPO_REGISTRADO);
		list.add(Rol.ROL_AYUDANTE);
		Rol adminRol = new Rol();
		adminRol.setDomainCode(Rol.ROL_ADMIN);
		adminRol.setNombre("Administrador");
		adminRol.setDescripcion("Rol de Administrador del Sistema");
		adminRol.setDeSistema(true);
		Collection rolesList = SisopAdminServiceProvider.getPersistenceService().findAll(Rol.class);
		for(Iterator it = rolesList.iterator();it.hasNext();){
			Rol rol = (Rol)it.next();
			if(!list.contains(rol.getDomainCode()))
				incluirPermisos(adminRol,Rol.findMeByDomainCode(rol.getDomainCode()));
			
		}
		//CollectionUtils.filter(adminRol.getPermisos(),new AdminRolFilter(list));
		adminRol.getPermisos().removeAll(list);
		save(adminRol);
	}
	
	
	private Permiso findPermiso(String domainCode) {
		return SisopAdminServiceProvider.getPersistenceService().findByDomainCode(Permiso.class, domainCode);
	}
	
	
	private void incluirPermisos(Rol superRol, Rol subRol){
		if(subRol != null && subRol.getPermisos() != null){
			for(Iterator it = subRol.getPermisos().iterator(); it.hasNext();){
				Permiso permiso = (Permiso) it.next();
				superRol.addPermiso(permiso);
			}
		}
	}
	
}