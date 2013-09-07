package ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.CodedModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.eventos.EventoNotificable;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.eventos.Notificable;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * Representa las capacidades que posee un Usuario sobre el sistema.
 * El rol se compone de un conjunto de permisos.
 * 
 * @author Rafa
 */
public class Rol extends CodedModelObject implements RolDomainCodes, Notificable {
	String descripcion;
	String nombre;
	boolean deSistema;
	private Collection permisos = CollectionFactory.createSet();
	private Collection usuarios = CollectionFactory.createSet();

	
	
	public void addPermiso(Permiso permiso) {
			this.permisos.add(permiso);
			permiso.addRol(this);
	}
	
	public void addPermisos(Collection<Permiso> permisosAdd) {
		Iterator<Permiso> iter = permisosAdd.iterator();
		while (iter.hasNext()) {
			Permiso element = (Permiso) iter.next();
			this.addPermiso(element);
		}
	}
	
	public void removePermiso(Permiso permiso) {
		this.permisos.remove(permiso);
		permiso.removeRol(this);
	}
	
	public void removePermisos(Collection<Permiso> permisosDel) {
		Iterator<Permiso> iter = permisosDel.iterator();
		while (iter.hasNext()) {
			Permiso element = (Permiso) iter.next();
			this.removePermiso(element);
		}
	}
	
	
	public void addUsuario(Usuario usuario) {
		this.usuarios.add(usuario);
		usuario.addRol(this);
	}
	
	public void addUsuarios(Collection<Usuario> usuariosAdd) {
		Iterator<Usuario> iter = usuariosAdd.iterator();
		while (iter.hasNext()) {
			Usuario element = (Usuario) iter.next();
			this.addUsuario(element);
		}
	}
	
	public void removeUsuario(Usuario usuario) {
		this.usuarios.remove(usuario);
		usuario.removeRol(this);
	}
	
	public void removeUsuarios(Collection<Usuario> usuariosDel) {
		Iterator<Usuario> iter = usuariosDel.iterator();
		while (iter.hasNext()) {
			Usuario element = (Usuario) iter.next();
			this.removeUsuario(element);
		}
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
	
	public boolean getDeSistema() {
		return deSistema;
	}

	public void setDeSistema(boolean deSistema) {
		this.deSistema = deSistema;
	}

	public void setRoles(ArrayList roles) {
		this.permisos = roles;
	}
	
	public Collection getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Collection usuarios) {
		this.usuarios = usuarios;
	}
	
	
	public void remove() {
		//Remuevo asociaciones a permisos
		if(permisos!=null && permisos.size()>0){
			Iterator iter = permisos.iterator();
			while (iter.hasNext()) {
				Permiso permiso = (Permiso) iter.next();
				permiso.removeRol(this);
				permiso.saveOrUpdate();
			}
		}
		
		//Remuevo asociaciones a usuarios
		if(usuarios!=null && usuarios.size()>0){
			Iterator iter = usuarios.iterator();
			while (iter.hasNext()) {
				Usuario usuario = (Usuario) iter.next();
				usuario.removeRol(this);
				usuario.saveOrUpdate();
			}
		}
		
		 Set<EventoNotificable> eventosNotificablesDelRol = EventoNotificable.findAllRoleEvents(this);
		 Iterator iter = eventosNotificablesDelRol.iterator();
		 while (iter.hasNext()) {
			EventoNotificable evento = (EventoNotificable) iter.next();
			evento.removeRolNotificable(this);
			evento.saveOrUpdate();	
		}
		
		 
		super.remove();
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
	
	public static List findLike(String atributo, String valor){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Rol.class);
		criteria.add(Expression.like(atributo, "%" + valor + "%"));
		criteria.addOrder(Order.asc("nombre"));
		return SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria);
	}
	
	public static List findMeById(Long id){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Rol.class);
		criteria.add(Expression.eq("id",id));
		criteria.addOrder(Order.asc("nombre"));
		return SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria);
		
	}
	
	/**
	 * Retorna la cantidad de objetos encontrados utilizando el filtro.
	 * @param atributo
	 * @param valor
	 * @return
	 */
	public static int findMeBy(String atributo, Object valor){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Rol.class);
		criteria.add(Expression.eq(atributo,valor));
		criteria.addOrder(Order.asc(atributo));
		return SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria).size();
		
	}

	public boolean matchesNotificationWith(Usuario usuario) {
		return usuario.getRoles().contains(this);
	}
	
}