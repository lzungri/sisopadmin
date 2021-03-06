package ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.CodedModelObject;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * Se reutilizaron los atributos, getters y setters para mantener la arquitectura.
 * 
 * Un permiso de m�todo sobre un UseCaseModel tendr� la siguiente nomenclatura:
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
	
	public void removeRol(Rol rol)
	{
		roles.remove(rol);
	}
	
	public String getDescription() {
		if(description!=null && !description.equalsIgnoreCase("")){
			return description;
		} else {
			return this.getDomainCode();
		}
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
	
	public static List findLike(String atributo, String valor){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Permiso.class);
		criteria.add(Expression.like(atributo, "%" + valor + "%"));
		criteria.addOrder(Order.asc("description"));
		return SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria);
	}
	
	public static List findMeById(Long id){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Permiso.class);
		criteria.add(Expression.eq("id",id));
		criteria.addOrder(Order.asc("description"));
		return SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria);
		
	}
	
	public static List findAll(){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Permiso.class);
		return (SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria));
	}
	
}