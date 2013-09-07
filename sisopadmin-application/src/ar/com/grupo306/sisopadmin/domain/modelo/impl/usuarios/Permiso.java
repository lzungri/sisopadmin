package ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios;

import java.util.Set;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.CodedModelObject;

/**
 * Se reutilizaron los atributos, getters y setters para mantener la arquitectura.
 * 
 * Un permiso de método sobre un UseCaseModel tendrá la siguiente nomenclatura:
 * 		nombreCompletoDeClase.nombreMetodo
 * Ejemplo:
 * 		ar.com.grupo306.sisopadmin.web.usecases.models.LoginUseCaseModel.init
 * 		ar.com.grupo306.sisopadmin.web.usecases.models.LoginUseCaseModel.login
 *  
 * @author Rafa
 */
public class Permiso extends CodedModelObject{
	private String nombre;
	
	private String description;
	
	private Set roles = CollectionFactory.createSet();
	
	
	public Permiso() {
		super();
	}

	public Permiso(String domainCode, String nombre) {
		this.setDomainCode(domainCode);
		this.nombre = nombre;
	}
	
	public Permiso(String domainCode, String nombre, String description) {
		this.setDomainCode(domainCode);
		this.nombre = nombre;		
		this.description = description;
	}
	
	public void addRol(Rol rol)
	{
		roles.add(rol);
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set getRoles() {
		return roles;
	}

	public void setRoles(Set roles) {
		this.roles = roles;
	}
	
}