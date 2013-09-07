package ar.com.grupo306.sisopadmin.domain.modelo.impl.archivos;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * @author Damián
 * 
 * Representa un puntero a los archivos subidos al sistema.
 * 
 */

public class Archivo extends ModelObject {
	String ruta;
	String nombre;
	String descripcion;
	Usuario usuarioCreador;
	
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
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public Usuario getUsuarioCreador() {
		return usuarioCreador;
	}
	public void setUsuarioCreador(Usuario usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}
	
	@Transactional
	public static List findLike(String atributo, String valor){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Archivo.class);
		criteria.add(Expression.like(atributo, "%" + valor + "%"));
		return SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria);
	}
	
	@Transactional
	public static Archivo findMeById(Long id){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Archivo.class);
		criteria.add(Expression.eq("id",id));
		return (Archivo) SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria).iterator().next();
		
	}
	
	@Transactional
	public static List findMeByMostrarInicio(){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Archivo.class);
		criteria.add(Expression.like("nombre","%"));	
		return SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria);
		
	}
	
	/**
	 * Retorna la cantidad de objetos encontrados utilizando el filtro.
	 * @param atributo
	 * @param valor
	 * @return
	 */
	@Transactional
	public static int findMeBy(String atributo, Object valor){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Archivo.class);
		criteria.add(Expression.eq(atributo,valor));
		criteria.addOrder(Order.asc(atributo));
		return SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria).size();
		
	}
	
}
