package ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.helpers.DateHelper;
import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.sisopadmin.constants.SisopAdminConstants;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion.functors.EncuestasAyudantesPredicate;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion.functors.EncuestasGruposPredicate;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.eventos.EventoNotificable;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Rol;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.RolDomainCodes;
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
	String destinatario;
	Collection plantillasEncuestadas;
	String descripcionEstado;
	
	
	
	
	
	
	
	
	public String getDescripcionEstado() {
		return descripcionEstado;
	}

	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}

	public Collection getPlantillasEncuestadas() {
		return plantillasEncuestadas;
	}

	public void setPlantillasEncuestadas(Collection plantillasEncuestadas) {
		this.plantillasEncuestadas = plantillasEncuestadas;
	}

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
	
	public static void definirDescripcionEstado(PlantillaEncuesta encuesta){
		if(DateUtils.isInRange(new Date(),(Timestamp)encuesta.getFechaAlta(),(Timestamp)encuesta.getFechaFin())){
			encuesta.setDescripcionEstado("En Encuesta");
			encuesta.setEstado(new Long(2));
			return;
		}
		if(DateUtils.isGreaterThanRange(new Date(),(Timestamp)encuesta.getFechaFin())){
			encuesta.setDescripcionEstado("Encuestada");
			encuesta.setEstado(new Long(3));
			return;
		}
			
		encuesta.setDescripcionEstado("Sin Encuestar");
		encuesta.setEstado(new Long(1));
	}
	
	
	/** finds por criterios... y mucho espaninglish
	 * Como dijimos que cada objeto iba a concentrar todas las peticiones a la base y las creaciones
	 * Se definen los siguientes  metodos
	 */
	
	public static PlantillaEncuesta findMeById(Long id){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(PlantillaEncuesta.class);
		criteria.add(Expression.eq(ID,id));
		PlantillaEncuesta encuesta = (PlantillaEncuesta)SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria).iterator().next();
		PlantillaEncuesta.definirDescripcionEstado(encuesta);
		encuesta.setNovedad();	
		return encuesta;
		
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
		List encuestas =  SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria);
		Iterator iEncuestas = encuestas.iterator();
		while(iEncuestas.hasNext()){
			PlantillaEncuesta encuesta = (PlantillaEncuesta)iEncuestas.next();
			PlantillaEncuesta.definirDescripcionEstado(encuesta);
			encuesta.setNovedad();
		}
		return encuestas;
		
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
			PlantillaEncuesta encuesta =  (PlantillaEncuesta)SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria).iterator().next();
			PlantillaEncuesta.definirDescripcionEstado(encuesta);
			return encuesta;
		}catch(Exception e){
			return null;
		}
			
	}
	
	public static List findEncuestasByDestinatario ( String destinatario){
		List encuestas = new PlantillaEncuesta().findAll(PlantillaEncuesta.class);
		if("AYUDANTE".equalsIgnoreCase(destinatario))
			CollectionUtils.filter(encuestas , new EncuestasAyudantesPredicate());
		if("GRUPO".equalsIgnoreCase(destinatario))
			CollectionUtils.filter(encuestas, new EncuestasGruposPredicate());
		CollectionUtils.filter(encuestas, new EstadoEnEncuestaFilter());
		
		
		return encuestas;
	}
	
	private static  class EstadoEnEncuestaFilter implements Predicate{

		public boolean evaluate(Object arg0) {
			PlantillaEncuesta encuesta = (PlantillaEncuesta)arg0;
			return 	DateUtils.isInRange(new Date(), encuesta.getFechaAlta(), encuesta.getFechaFin()) && !(encuesta instanceof PlantillaEncuestada);
		}
		
	}
	
	private static  class NovedadPredicate implements Closure{


		public void execute(Object arg0) {
			PlantillaEncuesta encuesta = (PlantillaEncuesta)arg0;
		 	encuesta.setNovedad();
		}
		
	}
	

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	
	
	public void setNovedad(){
		String ENCUESTA_NOVEDAD = "Existen nuevas encuestas cargadas en el sistema.";
		Set eventosNotificables = EventoNotificable.findAllPorDescripcion(ENCUESTA_NOVEDAD);
		Iterator iEventos = eventosNotificables.iterator();
		boolean lleno = false;
		while(iEventos.hasNext()){
			EventoNotificable evento = (EventoNotificable)iEventos.next();
			if("GRUPO".equalsIgnoreCase(this.getDestinatario()) && ENCUESTA_NOVEDAD.equalsIgnoreCase(evento.getDescripcion()) && evento.getRolesNotificables().contains(Rol.findMeByDomainCode(RolDomainCodes.ROL_GRUPO_INSCRIPTO)) &&
					DateUtils.isInRange(new Date(),this.getFechaAlta(),this.getFechaFin())){
				evento.setFechaHasta(DateUtils.addDays(new Date(),1));
				lleno = true;
			}
				
			
			if("AYUDANTE".equalsIgnoreCase(this.getDestinatario()) && ENCUESTA_NOVEDAD.equalsIgnoreCase(evento.getDescripcion()) && evento.getRolesNotificables().contains(Rol.findMeByDomainCode(RolDomainCodes.ROL_AYUDANTE)) &&
					DateUtils.isInRange(new Date(),this.getFechaAlta(),this.getFechaFin())){
				evento.setFechaHasta(DateUtils.addDays(new Date(),1));
				lleno = true;
			}
				
			
		}
		
		if (("GRUPO".equalsIgnoreCase(this.getDestinatario()) && eventosNotificables.isEmpty() || 
				"GRUPO".equalsIgnoreCase(this.getDestinatario()) && !lleno) && DateUtils.isInRange(new Date(),this.getFechaAlta(),this.getFechaFin())){
			new EventoNotificable(
					DateUtils.addDays(new Date(),1),
					"Existen nuevas encuestas cargadas en el sistema.",
					"." + "/" + StringUtils.uncapitalize("AdministrarEncuestaUseCase") + ".do?reqCode=initUseCase")
				.addNotificable(Rol.findMeByDomainCode(RolDomainCodes.ROL_GRUPO_INSCRIPTO))
				.save();
		}
		
		if ( ("AYUDANTE".equalsIgnoreCase(this.getDestinatario()) && eventosNotificables.isEmpty() || 
				"AYUDANTE".equalsIgnoreCase(this.getDestinatario()) && !lleno)  && DateUtils.isInRange(new Date(),this.getFechaAlta(),this.getFechaFin())){
			new EventoNotificable(
				DateUtils.addDays(new Date(),1),
				"Existen nuevas encuestas cargadas en el sistema.",
				"." + "/" + StringUtils.uncapitalize("AdministrarEncuestaUseCase") + ".do?reqCode=initUseCase")
			.addNotificable(Rol.findMeByDomainCode(RolDomainCodes.ROL_AYUDANTE))
			.save();
	}
	}

	
	


	
}
