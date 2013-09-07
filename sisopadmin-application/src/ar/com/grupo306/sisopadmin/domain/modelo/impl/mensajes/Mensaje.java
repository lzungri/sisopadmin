package ar.com.grupo306.sisopadmin.domain.modelo.impl.mensajes;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

public class Mensaje extends ModelObject{
	Date fechaAlta;
	String titulo;
	String contenido;
	Usuario emisor;
	Usuario receptor;
	Mensaje mensajePadre;
	Mensaje mensajeHijo;
	Boolean leidoPorReceptor;
	
	public static List findByUsuario(Usuario usuario){
		Criteria criteria;
		criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Mensaje.class);
		criteria.add(Restrictions.or( Restrictions.eq("emisor", usuario), Restrictions.eq("receptor", usuario)));
		return SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria);
	}	
	
	public Usuario getEmisor() {
		return emisor;
	}
	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}
	public Mensaje getMensajePadre() {
		return mensajePadre;
	}
	public void setMensajePadre(Mensaje mensajePadre) {
		this.mensajePadre = mensajePadre;
	}
	public Usuario getReceptor() {
		return receptor;
	}
	public void setReceptor(Usuario receptor) {
		this.receptor = receptor;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Mensaje getMensajeHijo() {
		return mensajeHijo;
	}
	public void setMensajeHijo(Mensaje mensajeHijo) {
		this.mensajeHijo = mensajeHijo;
	}
	public Boolean getLeidoPorReceptor() {
		return leidoPorReceptor;
	}
	public void setLeidoPorReceptor(Boolean leidoPorReceptor) {
		this.leidoPorReceptor = leidoPorReceptor;
	}
	
}
