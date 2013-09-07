package ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * @author Rafa
 * Modificada por Sole (13-09) para agregar el atributo apellido
 *
 */

public class Ayudante extends Usuario{
	protected Date fechaAltaCatedra;
	protected Long maximaCantidadGrupos;
	protected Collection grupos;
	protected Boolean notificacionesPorMail;
	protected Boolean resumenDeConsultas;	
	protected String apellido;
		
	public Date getFechaAltaCatedra() {
		return fechaAltaCatedra;
	}
	public void setFechaAltaCatedra(Date fechaAltaCatedra) {
		this.fechaAltaCatedra = fechaAltaCatedra;
	}
	public Long getMaximaCantidadGrupos() {
		return maximaCantidadGrupos;
	}
	public void setMaximaCantidadGrupos(Long maximaCantidadGrupos) {
		this.maximaCantidadGrupos = maximaCantidadGrupos;
	}
	public Collection getGrupos() {
		return grupos;
	}
	public void setGrupos(Collection grupos) {
		this.grupos = grupos;
	}	
	public Boolean getNotificacionesPorMail() {
		return notificacionesPorMail;
	}
	public void setNotificacionesPorMail(Boolean notificacionesPorMail) {
		this.notificacionesPorMail = notificacionesPorMail;
	}
	public Boolean getResumenDeConsultas() {
		return resumenDeConsultas;
	}
	public void setResumenDeConsultas(Boolean resumenDeConsultas) {
		this.resumenDeConsultas = resumenDeConsultas;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getTipoIntegrante(){
		return "Ayudante";
	}
	
	public static List findUs(String nombre,String apellido,Class tipo){
		Criteria criteria;
		
		// tipo puede ser Ayudante,Coordinador o null(Si se elijió la opción TODOS)
		if(tipo != null && tipo == Coordinador.class)
			criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Coordinador.class);
		else{
			criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Ayudante.class);
			if(tipo != null && tipo == Ayudante.class)
				criteria.add(Restrictions.sqlRestriction("TIPO_USUARIO = 'AYUDANTE'"));		   
		}
		
		if("".compareTo(nombre) != 0)
			criteria.add(Expression.like(NOMBRE,"%" + nombre + "%"));
		
		if("".compareTo(apellido) != 0)
			criteria.add(Expression.like("apellido", "%" + apellido + "%"));	
		return SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria);
	}
	
	public static List findByCoordinadorAsignado(Long idCoordinador){
		Criteria criteria;
		criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Ayudante.class);
		criteria.add(Restrictions.sqlRestriction("ID_COORDINADOR = " + idCoordinador.toString()));
		return SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria);
	}
	
	public static List findSinCoordinador(){
		Criteria criteria;
		criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Ayudante.class);
		criteria.add(Restrictions.sqlRestriction("TIPO_USUARIO = 'AYUDANTE'")); // Filtra los coordinadores
		criteria.add(Restrictions.sqlRestriction("ID_COORDINADOR IS NULL " ));
		return SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria);
	}
	
	public static Ayudante findMeById(Long id){
		Criteria criteria;
		criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Ayudante.class);
		criteria.add(Expression.eq(ID,id));
		
		return (Ayudante) criteria.uniqueResult();	
	}	
}
