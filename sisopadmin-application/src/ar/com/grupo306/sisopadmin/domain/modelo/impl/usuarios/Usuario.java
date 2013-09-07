package ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * @author Rafa
 * Modificada por Sole (13-09) para agregar el atributo nombre
 *
 */

public class Usuario extends ModelObject {	
	
	protected Date fechaAlta;
	protected String loginName;
	protected String password;
	protected Set roles = CollectionFactory.createSet();
	protected Collection<PlantillaEncuesta> encuestasSinLlenar;
	protected String nombre;
	protected String email;
		
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	public Collection getRoles() {
		return roles;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Collection getEncuestasSinLlenar() {
		return encuestasSinLlenar;
	}
	public void setEncuestasSinLlenar(Collection encuestasSinLlenar) {
		this.encuestasSinLlenar = encuestasSinLlenar;
	}	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setRoles(Set roles) {
		this.roles = roles;
	}
	
	public void addRol(Rol rol){
		roles.add(rol);
	}
	
	/**
	 * Indica si un Usuario posee el permiso determinado por el permisoCode.
	 * 
	 * @param permisoCode
	 */
	public boolean hasPermiso(String permisoCode) {
		for(Rol rol : (Set<Rol>) this.roles) {
			if(rol.hasPermiso(permisoCode))
				return true;
		}
		return false;
	}
	
	/**
	 * Indica si un Usuario posee el Permiso envíado por parámetro.
	 * 
	 * @param permiso
	 */
	public boolean hasPermiso(Permiso permiso) {
		return this.hasPermiso(permiso.getDomainCode());
	}
	
	
	/*****************************************************************************/
	/** Obtenemos usuarios por medio de filtros									 */
	/*****************************************************************************/
	
	public static Usuario findMeByLoginName(String loginName){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Usuario.class);
		criteria.add(Expression.eq(LOGIN_NAME,loginName));
		try{
			return (Usuario)(SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria).iterator().next());
		}catch(Exception e){
			return null;
		}
	}
	
}