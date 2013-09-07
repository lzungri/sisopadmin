package ar.com.grupo306.sisopadmin.domain.modelo.impl.informacion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
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
		this.usuarioCreador = null;
	}
	
	public boolean isInHome() {
		return this.estado==1 && DateUtils.isInRange(new Date(), this.fechaInicio, this.fechaFin);
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
		//criteria.add(Expression.like("nombre","%"));
		criteria.add(Expression.le("fechaInicio",new Date()));
		criteria.add(Expression.ge("fechaFin",new Date()));
	
		criteria.add(Expression.eq("estado",1L));
		
		Set<Informacion> orderedSet = CollectionFactory.createTreeSet(Informacion.class, new Comparator<Informacion>() {
			public int compare(Informacion o1, Informacion o2) {
				return o1.getFechaInicio().compareTo(o2.fechaFin) * 10000 + o1.getId().compareTo(o2.getId());
			}
		});
		orderedSet.addAll(SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria));
		
		return new ArrayList<Informacion>(orderedSet);
		
	}
	
	/**
	 * Retorna la cantidad de objetos encontrados utilizando el filtro.
	 * @param atributo
	 * @param valor
	 * @return
	 */
	public static int findMeBy(String atributo, Object valor){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Informacion.class);
		criteria.add(Expression.eq(atributo,valor));
		criteria.addOrder(Order.asc(atributo));
		return SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria).size();
		
	}
}