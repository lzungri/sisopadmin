package ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.helpers.DateHelper;
import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.sisopadmin.constants.SisopAdminConstants;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;


/**
 * 
 * @author Rafa
 *
 */

public class PlantillaEncuesta extends ModelObject{
	Collection puntosAEncuestar;
	Date fechaAlta;
	Date fechaFin;
	String nombre;
	Long obligatoriedad;//mapear
	Set usuariosSinEncuestar;
	Usuario usuario;
	Long estado;
	
	
	
	
	public PlantillaEncuesta(){
		super();
	}
	
	public PlantillaEncuesta(String nombrePlantilla,String fechaInicio,String fechaFin,boolean obligatoria,Usuario usuario){
		super();
		this.setNombre(nombrePlantilla);
		this.setFechaAlta(DateHelper.textoComoFecha(fechaInicio));
		try {
			this.setFechaFin(DateUtils.getDateFromString(fechaFin,SisopAdminConstants.DATE_FORMAT_PATTERN));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setUsuariosSinEncuestar(CollectionFactory.createSet(TreeSet.class));
		this.setUsuario(usuario);
		
	}
	
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getObligatoriedad() {
		return obligatoriedad;
	}
	public void setObligatoriedad(Long obligatoriedad) {
		this.obligatoriedad = obligatoriedad;
	}
	public Collection getPuntosAEncuestar() {
		return puntosAEncuestar;
	}
	public void setPuntosAEncuestar(Collection puntosAEncuestar) {
		this.puntosAEncuestar = puntosAEncuestar;
		
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Set getUsuariosSinEncuestar() {
		return usuariosSinEncuestar;
	}
	public void setUsuariosSinEncuestar(Set usuariosSinEncuestar) {
		this.usuariosSinEncuestar = usuariosSinEncuestar;
	}
	
	public Long getEstado() {
		return estado;
	}
	public void setEstado(Long estado) {
		this.estado = estado;
	}
	
	/** finds por criterios... y mucho espaninglish
	 * Como dijimos que cada objeto iba a concentrar todas las peticiones a la base y las creaciones
	 * Se definen los siguientes  metodos
	 */
	
	public static PlantillaEncuesta findMeById(Long id){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(PlantillaEncuesta.class);
		criteria.add(Expression.eq(ID,id));
		return (PlantillaEncuesta)SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria).iterator().next();
		
	}
	
	
	/**
	 * Encuentra todas las planillas que entran en los parámetros especificados
	 * @param nombrePlantillaBusqueda
	 * @param fechaInicioBusqueda
	 * @param fechaFinBusqueda
	 * @return
	 */
	
	public static List findUs(String nombrePlantillaBusqueda,Date fechaInicioBusqueda,Date fechaFinBusqueda){
		/**TODO - El método está chancho , pensar en la forma de evitar tantos ifsssss*/
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(PlantillaEncuesta.class);
		if(!"".equalsIgnoreCase(nombrePlantillaBusqueda) && nombrePlantillaBusqueda != null)
			criteria.add(Expression.eq(NOMBRE,nombrePlantillaBusqueda));
		if(fechaInicioBusqueda != null && fechaFinBusqueda != null)
			criteria.add(Expression.between(FECHA_ALTA,fechaInicioBusqueda,fechaFinBusqueda));
		if(fechaInicioBusqueda != null && fechaFinBusqueda != null)
			criteria.add(Expression.between(FECHA_FIN,fechaInicioBusqueda,fechaFinBusqueda));
		if(fechaInicioBusqueda != null && fechaFinBusqueda == null)
			criteria.add(Expression.ge(FECHA_ALTA, fechaInicioBusqueda));
		if(fechaInicioBusqueda == null && fechaFinBusqueda != null)
			criteria.add(Expression.le(FECHA_FIN, fechaFinBusqueda));
		return SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria);
	}
	/**
	 * Encuentra a la plantilla de encuesta por el nombre. Este último es único.
	 * @param id
	 * @return
	 */
	public static PlantillaEncuesta findMeByName(String nombre){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(PlantillaEncuesta.class);
		criteria.add(Expression.eq(NOMBRE,nombre));
		try{
			return (PlantillaEncuesta)SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria).iterator().next();
		}catch(Exception e){
			return null;
		}
			
	}
	
}
