package ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Permiso;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Rol;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.TablePopulator;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * @author Leandro
 */
public class RolTablePopulator extends TablePopulator {
	
	public void populateAdminRol() {
		Rol adminRol = new Rol();
		adminRol.setDomainCode(Rol.ROL_ADMIN);
		adminRol.setNombre("Rol de administrador");
		
		save(adminRol);
	}
	
	public void populateJefeCatedraRol() {
		Rol rol = new Rol();
		rol.setDomainCode(Rol.ROL_JEFE_CATEDRA);		
		rol.setNombre("Rol de jefe de cátedra");
		
		save(rol);
	}	
	
	public void populateCoordinadorRol() {
		Rol rol = new Rol();
		rol.setDomainCode(Rol.ROL_COORDINADOR);		
		rol.setNombre("Rol de coordinador");
		
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.AdministrarIntegranteUseCaseModel.initUseCase"));	
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.initUseCase.create"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.initUseCase.edit"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.initUseCase.view"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.initUseCase.remove"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.createAccept"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.createAccept.create"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.editAccept"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.editAccept.edit"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.removeAccept"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.ABMIntegranteUseCaseModel.removeAccept.remove"));
		
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.archivos.ABMArchivosUseCaseModel.initUseCase"));	
		
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.initUseCase.create"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.altaInformacionCatedra"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.altaInformacionCatedra.create"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.initUseCase.remove"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.bajaInformacionCatedra"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.bajaInformacionCatedra.remove"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.initUseCase.edit"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.modificacionInformacionCatedra"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.modificacionInformacionCatedra.edit"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.AdministrarInformacionCatedraUseCaseModel.initUseCase"));
		
		save(rol);		
	}
	
	public void populateAyudanteRol() {
		Rol rol = new Rol();
		rol.setDomainCode(Rol.ROL_AYUDANTE);		
		rol.setNombre("Rol de ayudante");
		
		save(rol);
	}
	
	public void populateGrupoRol() {
		Rol rol = new Rol();
		rol.setDomainCode(Rol.ROL_GRUPO);		
		rol.setNombre("Rol de grupo de trabajos prácticos");
		
		save(rol);
	}	
	
	public void populateAdminPlantillasRol() {
		Rol adminRol = new Rol();
		adminRol.setDomainCode(Rol.ROL_ADMIN_ENCUESTAS);		
		adminRol.setNombre("Rol de administrador de plantillas");
		
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.ABMEncuestaUseCaseModel.initUseCase"));
		adminRol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.initUseCase"));		
		
		save(adminRol);
	}
	
	public void populatePedorroRol() {
		Rol rol = new Rol();
		rol.setDomainCode(Rol.ROL_PEDORRO);		
		rol.setNombre("Rol pedorro, pim pam pum");
		
		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUUnoUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUDosUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUTresUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUDosUseCaseModel.volverDeCUTres"));
		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUDosUseCaseModel.initUseCase.edit"));
		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUTresUseCaseModel.initUseCase.edit"));		
		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUDosUseCaseModel.volverDeCUTres.edit"));
		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUTresUseCaseModel.getValor11.edit"));
		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUTresUseCaseModel.getValor11"));
		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUTresUseCaseModel.setValor11"));
		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUTresUseCaseModel.getValor12"));
		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUTresUseCaseModel.setValor12"));
		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUTresUseCaseModel.cancelUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.usecasefwk.usecases.models.test.CUUnoUseCaseModel.select"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.initUseCase"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.initUseCase.create"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.altaInformacionCatedra"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.altaInformacionCatedra.create"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.initUseCase.remove"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.bajaInformacionCatedra"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.bajaInformacionCatedra.remove"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.initUseCase.edit"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.modificacionInformacionCatedra"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCaseModel.modificacionInformacionCatedra.edit"));
		rol.addPermiso(findPermiso("ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.AdministrarInformacionCatedraUseCaseModel.initUseCase"));		
		
		save(rol);
	}
	
	private Permiso findPermiso(String domainCode) {
		return SisopAdminServiceProvider.getPersistenceService().findByDomainCode(Permiso.class, domainCode);
	}
	
}