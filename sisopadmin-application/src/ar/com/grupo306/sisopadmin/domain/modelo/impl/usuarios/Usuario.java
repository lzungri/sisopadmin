package ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.eventos.EventoNotificable;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.eventos.Notificable;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.mensajes.Mensaje;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * @author Rafa
 * Modificada por Sole (13-09) para agregar el atributo nombre
 *
 */

public class Usuario extends ModelObject implements Notificable {	
	
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
	
	// Metodo necesario para el CU de mensajes
	public Usuario getCoordinador() {
		return null;
	}
	
	public void setRoles(Set roles) {
		this.roles = roles;
	}
	
	public void addRol(Rol rol){
		roles.add(rol);
	}
	
	public void remove() {
		
		//Borrar los mensajes de un usuario antes de eliminarlo		
		for(Iterator it = Mensaje.findByUsuario(this).iterator();it.hasNext();){
			((Mensaje)it.next()).remove();
		}
		
		//Borra los eventos del usuario
		EventoNotificable.removeEventosUsuario(this);

		if(getEncuestasSinLlenar() != null){
			getEncuestasSinLlenar().clear();
			this.saveOrUpdate();
		}
		
		super.remove();
	}
	
	public void addRoles(Collection<Rol> rolesAdd) {
		Iterator<Rol> iter = rolesAdd.iterator();
		while (iter.hasNext()) {
			Rol element = (Rol) iter.next();
			this.addRol(element);
		}
	}
	
	public void removeRol(Rol rol){
		roles.remove(rol);
	}
	
	public void removeRoles(Collection<Rol> rolesDel) {
		Iterator<Rol> iter = rolesDel.iterator();
		while (iter.hasNext()) {
			Rol element = (Rol) iter.next();
			this.removeRol(element);
		}
	}
	
	/**
	 * Indica si el Usuario posee el permiso determinado por el permisoCode.
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
	 * Indica si el Usuario posee el rol determinado por el nombreRol.
	 * 
	 * @param permisoCode
	 */
	public boolean hasRol(String nombreRol) {
		for(Rol rol : (Set<Rol>) this.roles) {
			if(rol.getDomainCode().compareTo(nombreRol) == 0)
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
		return (Usuario)criteria.uniqueResult();
	}
	
	public static List findAll(){
		List<Usuario> usuarios = CollectionFactory.createList();;
		Criteria criteria;
		criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Usuario.class);
		//criteria.add(Restrictions.sqlRestriction("TIPO_USUARIO LIKE '%'"));
		criteria.addOrder(Order.asc("loginName"));
		usuarios.addAll(SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria));
		return usuarios;
	}
	
	
	public static Usuario findMeById(Long id){
		Criteria criteria;
		criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Usuario.class);
		criteria.add(Expression.eq(ID,id));
		
		return (Usuario) criteria.uniqueResult();	
	}
	
	public List<Usuario> getPosiblesReceptoresGruposDeMensaje(){
		/*
		   Este metodo debe sobreescribirse en las subclases de Usuario, de forma que
		   cada tipo de usuario devuelva sus posibles receptores de mensaje.
		   Receptores de un grupo: su ayudante y su coordinador (si el ayudante es
		     coordinador, sólo debe devolver a éste). Si el grupo no tiene ayudante,
		     puede enviar mensajes a todos los coordinadores.
		   Receptores de un ayudante (no coordinador): sus grupos y los otros ayudantes y
		     coordinadores (no puede mandar mensajes a los grupos de otros ayudantes).
		   Receptores de un coordinador: todos los usuarios.
		*/
		return new ArrayList<Usuario>();
	}
	
	public List<Usuario> getPosiblesReceptoresAyudantesDeMensaje(){
		/*
		   Este metodo debe sobreescribirse en las subclases de Usuario, de forma que
		   cada tipo de usuario devuelva sus posibles receptores de mensaje.
		   Receptores de un grupo: su ayudante y su coordinador (si el ayudante es
		     coordinador, sólo debe devolver a éste). Si el grupo no tiene ayudante,
		     puede enviar mensajes a todos los coordinadores.
		   Receptores de un ayudante (no coordinador): sus grupos y los otros ayudantes y
		     coordinadores (no puede mandar mensajes a los grupos de otros ayudantes).
		   Receptores de un coordinador: todos los usuarios.
		*/
		return new ArrayList<Usuario>();
	}

	public List<Usuario> getPosiblesReceptoresCoordinadoresDeMensaje(){
		/*
		   Este metodo debe sobreescribirse en las subclases de Usuario, de forma que
		   cada tipo de usuario devuelva sus posibles receptores de mensaje.
		   Receptores de un grupo: su ayudante y su coordinador (si el ayudante es
		     coordinador, sólo debe devolver a éste). Si el grupo no tiene ayudante,
		     puede enviar mensajes a todos los coordinadores.
		   Receptores de un ayudante (no coordinador): sus grupos y los otros ayudantes y
		     coordinadores (no puede mandar mensajes a los grupos de otros ayudantes).
		   Receptores de un coordinador: todos los usuarios.
		*/
		return new ArrayList<Usuario>();
	}
	
	public boolean matchesNotificationWith(Usuario usuario) {
		return usuario.equals(this);
	}
	
	public String getNombreCompleto(){
		return nombre;
	}
	
}