package ar.com.grupo306.sisopadmin.web.usecases.domain.roles;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.exceptions.core.BusinessException;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.archivos.Archivo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Rol;
import ar.com.grupo306.sisopadmin.web.context.UserContext;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.BaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.RemoveMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.ViewMode;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseValidationUtils;

/**
 * Modelo para Caso de uso de 
 * 		CU 1.3.1 - Administrar Roles
 * 
 * @author Damian
 */

public class AdministrarRolesUseCaseModel extends BaseUseCaseModel{
	private String valorBusqueda;
	private String buscarPor;
	private String deSistema;
	private int entradas=0;
	private Collection<Rol> roles = null;
	private Collection<Rol> rolesById = null;
	private Long index;
	
	
	@Secure ({})
	@Description ("Administración de Roles de la Cátedra")
	@Transactional
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);
		if(entradas==0){
			roles = Rol.findLike("nombre","");
		}
		
	}
	
	
	@Transactional
	public void buscarRolCatedra(UseCaseContext context){
		if (!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}
					
		UserContext userContext = (UserContext)context.getUserContext();
		if(!userContext.isUserLogged()){
			throw new BusinessException("No se puede realizar la operación ya que el usuario tiene que estar logueado en el sistema");
		}

		if(buscarPor!=null && buscarPor.equalsIgnoreCase("0")){
			roles = Rol.findLike("nombre",valorBusqueda);
		} else {
			roles = Rol.findLike("descripcion",valorBusqueda);
		}
		
		int size = 0;
		if(roles!=null){
			size = roles.size();
		}
		context.addMessage("Se han encontrado " + size + " elementos que coinciden con el filtro.");
		entradas++;
		
		context.refreshUseCase();				
	}
	
	public void altaRolCatedra(UseCaseContext context) {
		if (!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}
		context.goToChildUseCase(ABMRolesUseCase.class,new CreateMode(), new HashMap(), "buscarRolCatedra");
	}
	
	@Transactional
	public void bajaRolCatedra(UseCaseContext context) {
		if (!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}
		Map parametros = new HashMap();
		rolesById = Rol.findMeById(index);
		if(rolesById!=null){
			Rol rol = (Rol) rolesById.iterator().next();
			parametros.put("rolSelected",rol);
		} 
		context.goToChildUseCase(ABMRolesUseCase.class, new RemoveMode(), parametros,"buscarRolCatedra");
		
	}
	
	@Transactional
	public void modificacionRolCatedra(UseCaseContext context) {
		if (!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}
		Map parametros = new HashMap();
		rolesById = Rol.findMeById(index);
		if(rolesById!=null){
			Rol rol = (Rol) rolesById.iterator().next();
			parametros.put("rolSelected",rol);
		} 
		context.goToChildUseCase(ABMRolesUseCase.class, new EditMode(), parametros,"buscarRolCatedra");
		
	}
	
	@Transactional
	public void visualizarRolCatedra(UseCaseContext context) {
		if (!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}
		Map parametros = new HashMap();
		rolesById = Rol.findMeById(index);
		if(rolesById!=null){
			Rol rol = (Rol) rolesById.iterator().next();
			parametros.put("rolSelected",rol);
		} 
		context.goToChildUseCase(ABMRolesUseCase.class, new ViewMode(), parametros,"buscarRolCatedra");
		
	}
	
	private boolean validacionOK(UseCaseValidationUtils validationUtils) {
		validationUtils.assertTextoSeguro(valorBusqueda,"El campo de busqueda contiene caracteres no admitidos por el sistema.");
		return !validationUtils.hasErrors();
	}

	
//	 GETTER AND SETTERS 

	public String getBuscarPor() {
		return buscarPor;
	}


	public void setBuscarPor(String buscarPor) {
		this.buscarPor = buscarPor;
	}


	public int getEntradas() {
		return entradas;
	}


	public void setEntradas(int entradas) {
		this.entradas = entradas;
	}

	public Long getIndex() {
		return index;
	}


	public void setIndex(Long index) {
		this.index = index;
	}


	public Collection<Rol> getRoles() {
		return roles;
	}


	public void setRoles(Collection<Rol> roles) {
		this.roles = roles;
	}


	public Collection<Rol> getRolesById() {
		return rolesById;
	}


	public void setRolesById(Collection<Rol> rolesById) {
		this.rolesById = rolesById;
	}


	public String getValorBusqueda() {
		return valorBusqueda;
	}


	public void setValorBusqueda(String valorBusqueda) {
		this.valorBusqueda = valorBusqueda;
	}

	public String getDeSistema() {
		return deSistema;
	}


	public void setDeSistema(String deSistema) {
		this.deSistema = deSistema;
	}
	
	
	

}
