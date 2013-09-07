package ar.com.grupo306.sisopadmin.domain.modelo.impl.informacion;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * 
 * @author Rafa
 *
 */

public class Informacion extends ModelObject{
	String contenido;
	String descripcion;
	Long estado;
	String nombre;
	Date fechaFin;
	Date fechaInicio;
	Usuario usuarioCreador;
	
	
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getEstado() {
		return estado;
	}
	public void setEstado(Long estado) {
		this.estado = estado;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Usuario getUsuarioCreador() {
		return usuarioCreador;
	}
	public void setUsuarioCreador(Usuario usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}
	
	/**
	 * Encuentra todas las planillas que entran en los parámetros especificados
	 * @param atributo: Nombre del Atributo
	 * @param valor: Valor del Atributo
	 * @return
	 */
	public static List findLike(String atributo, String valor){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Informacion.class);
		criteria.add(Expression.like(atributo, "%" + valor + "%"));
		return SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria);
	}
	
	public static List findMeById(Long id){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Informacion.class);
		criteria.add(Expression.eq("id",id));
		return SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria);
		
	}
	
	public static List findMeByMostrarInicio(){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Informacion.class);
		criteria.add(Expression.like("nombre","%"));
		//criteria.add(Expression.gt("fechaInicio",new Date()));
		//criteria.add(Expression.gt("fechaFin",new Date()));
		
		return SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria);
		
	}
}