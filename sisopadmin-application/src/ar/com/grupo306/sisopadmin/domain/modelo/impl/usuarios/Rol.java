package ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios;

import java.util.ArrayList;
import java.util.Collection;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.CodedModelObject;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * Representa las capacidades que posee un Usuario sobre el sistema.
 * El rol se compone de un conjunto de permisos.
 * 
 * @author Rafa
 */
public class Rol extends CodedModelObject implements RolDomainCodes {
	String descripcion;
	String nombre;
	private Collection permisos = CollectionFactory.createSet();

	
	
	public void addPermiso(Permiso permiso) {
		this.permisos.add(permiso);
		permiso.addRol(this);
	}
	
	public void removePermiso(Permiso permiso) {
		this.permisos.remove(permiso);
	}

	public Collection getPermisos() {
		return permisos;
	}

	public void setPermisos(Collection permisos) {
		this.permisos = permisos;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setRoles(ArrayList roles) {
		this.permisos = roles;
	}
	
	/**
	 * Indica si un Rol posee el permiso determinado por el permisoCode.
	 * 
	 * @param permisoCode
	 */
	public boolean hasPermiso(String permisoCode) {
		for(Permiso permiso : (Collection<Permiso>) this.permisos) {
			if(permiso.getDomainCode().equals(permisoCode))
				return true;
		}
		return false;
	}

	/**
	 * Indica si un Rol posee el Permiso envíado por parámetro.
	 * 
	 * @param permiso
	 */
	public boolean hasPermiso(Permiso permiso) {
		return this.hasPermiso(permiso.getDomainCode());
	}
	
	public static Rol findMeByDomainCode(String domainCode){
		return SisopAdminServiceProvider.getPersistenceService().findByDomainCode(Rol.class, domainCode);		
	}
	
}