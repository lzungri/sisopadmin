package ar.com.grupo306.sisopadmin.domain.modelo.impl.eventos;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Rol;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * Representa un evento dentro de la app que debe ser notificado al usuario.
 *
 * @author Leandro
 */
public class EventoNotificable extends ModelObject {
	private Date fechaDesde;
	private Date fechaHasta;
	private String descripcion;
	private String url;
	
	// TODO ARREGLAR ALGUN PUTO DIA: Así debería ser...
	// private Set<Notificable> notificables = CollectionFactory.createSet(Notificable.class);

	// Por el mapeo puto se hace así.
	private Set<Rol> rolesNotificables = CollectionFactory.createSet(Rol.class);
	private Set<Usuario> usuariosNotificables = CollectionFactory.createSet(Usuario.class);
	
	public EventoNotificable() {
		super();
	}
	
	public EventoNotificable(Date fechaDesde, Date fechaHasta, String descripcion, String url) {
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.descripcion = descripcion;
		this.url = url;
	}
	
	public EventoNotificable(Date fechaHasta, String descripcion, String url) {
		this.fechaDesde = new Date();
		this.fechaHasta = fechaHasta;
		this.descripcion = descripcion;
		this.url = url;
	}
	
	public EventoNotificable(Date fechaHasta, String descripcion) {
		this.fechaDesde = new Date();
		this.fechaHasta = fechaHasta;
		this.descripcion = descripcion;
	}
	

	/**
	 * Indica si el evento corresponde al usuario.
	 * 
	 * @param usuario
	 */
	public boolean matchesWith(Usuario usuario) {
		// TODO SACAR CUANDO SE MODIFIQUE EL MAPEO.
		Set<Notificable> notificables = CollectionFactory.createSet(Notificable.class);
		notificables.addAll(this.usuariosNotificables);
		notificables.addAll(this.rolesNotificables);
		
		for(Notificable notificable : notificables) {
			if(notificable.matchesNotificationWith(usuario)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Retorna todos los eventos notificables activos al día actual.
	 */
	public static Set<EventoNotificable> findAllActives() {
		Set<EventoNotificable> eventos = CollectionFactory.createSet(EventoNotificable.class);
		
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(EventoNotificable.class);
		criteria.add(Expression.le("fechaDesde", new Date()));
		criteria.add(Expression.ge("fechaHasta", new Date()));
		
		eventos.addAll(criteria.list());
		return eventos;
	}
	
	public static void removeEventosUsuario(Usuario usuario) {
		Set<EventoNotificable> eventos = CollectionFactory.createSet(EventoNotificable.class);
		
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(EventoNotificable.class);
		eventos.addAll(criteria.list());
		
		for(Iterator it = eventos.iterator();it.hasNext();){
			EventoNotificable evento = (EventoNotificable)it.next();
			evento.getUsuariosNotificables().remove(usuario);
			evento.update();			
		}
	}
	
	/**
	 * Retorna todos los eventos notificables por descripcion
	 */
	public static Set<EventoNotificable> findAllPorDescripcion(String descripcion) {
		Set<EventoNotificable> eventos = CollectionFactory.createSet(EventoNotificable.class);
		
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(EventoNotificable.class);
		criteria.add(Expression.eq("descripcion", descripcion));
		
		
		eventos.addAll(criteria.list());
		return eventos;
	}
	
	
	/**
	 * Retorna todos los eventos notificables para un Rol.
	 * TODO  FILTRAR LA BUSQUEDA, para que solo traiga los eventos del ROl. 
	 * 		 Ahora trae a todos los eventos.
	 */
	public static Set<EventoNotificable> findAllRoleEvents(Rol rol) {
		Set<EventoNotificable> eventos = CollectionFactory.createSet(EventoNotificable.class);
		
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(EventoNotificable.class);
		
		eventos.addAll(criteria.list());
		return eventos;
	}
	
	
	public EventoNotificable addNotificable(Usuario usuario) {
		this.usuariosNotificables.add(usuario);
		
		return this;
	}

	public EventoNotificable addNotificable(Rol rol) {
		this.rolesNotificables.add(rol);
		
		return this;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}
	
	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	
	public Date getFechaHasta() {
		return fechaHasta;
	}
	
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
//	public Set<Notificable> getNotificables() {
//		return notificables;
//	}
//	
//	public void setNotificables(Set<Notificable> notificables) {
//		this.notificables = notificables;
//	}

	public Set<Rol> getRolesNotificables() {
		return rolesNotificables;
	}

	public void setRolesNotificables(Set<Rol> rolesNotificables) {
		this.rolesNotificables = rolesNotificables;
	}
	
	public void removeRolNotificable(Rol rolNotificable) {
		this.rolesNotificables.remove(rolNotificable);
	}

	public Set<Usuario> getUsuariosNotificables() {
		return usuariosNotificables;
	}

	public void setUsuariosNotificables(Set<Usuario> usuariosNotificables) {
		this.usuariosNotificables = usuariosNotificables;
	}
}