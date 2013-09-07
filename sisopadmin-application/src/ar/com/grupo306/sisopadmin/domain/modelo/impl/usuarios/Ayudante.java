package ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion.functors.EncuestasAyudantesPredicate;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.EvaluacionFasePorPlantilla;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.InformeEvaluacionFase;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * @author Rafa
 * Modificada por Sole (13-09) para agregar el atributo apellido
 *
 */

public class Ayudante extends Usuario{
	public static String TIPO_INTEGRANTE_AYUDANTE = "Ayudante";
	public static String TIPO_INTEGRANTE_COORDINADOR = "Coordinador";
	
	protected Date fechaAltaCatedra;
	protected Long maximaCantidadGrupos;
	protected Collection grupos;
	protected Boolean notificacionesPorMail;
	protected Boolean resumenDeConsultas;	
	protected String apellido;
	protected Coordinador coordinador;
		
	public void initialize(){
		Collection plantillas = new PlantillaEncuesta().findAll(PlantillaEncuesta.class);
		plantillas = CollectionUtils.select(plantillas,new EncuestasAyudantesPredicate());
		this.setEncuestasSinLlenar(plantillas);
	}
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
		return TIPO_INTEGRANTE_AYUDANTE;
	}
	
	public Coordinador getCoordinador() {
		return coordinador;
	}
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
	
	public void remove() {
		//Borrar los informe evaluación fase de un ayudante antes de eliminarlo
		for(Iterator it = EvaluacionFasePorPlantilla.findBy(null, null, null, this.getId(), null).iterator();it.hasNext();){
			((EvaluacionFasePorPlantilla)it.next()).remove();
		}
		//Borrar los informe evaluación fase de un grupo antes de eliminarlo
		for(Iterator it = InformeEvaluacionFase.findBy(null,this.getId(),null).iterator();it.hasNext();){
			((InformeEvaluacionFase)it.next()).remove();
		}
		
		super.remove();
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
	
	public static Ayudante findMeByLoginName(String loginName){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Ayudante.class);
		criteria.add(Expression.eq(LOGIN_NAME,loginName));
		return (Ayudante)criteria.uniqueResult();
	}
	
	public List<Usuario> getPosiblesReceptoresGruposDeMensaje(){
		return new ArrayList<Usuario>(grupos);
	}
	
	public List<Usuario> getPosiblesReceptoresAyudantesDeMensaje(){
		List<String> ayudantesNoValidos = SisopAdminServiceProvider.getPersistenceService().createQuery(
				"select loginName from Coordinador").list();
		ayudantesNoValidos.add(this.loginName);
		
		return SisopAdminServiceProvider.getPersistenceService().createCriteria(Ayudante.class)
				.add( Restrictions.not(Restrictions.in("loginName", ayudantesNoValidos)) )
				.list();
	}
	
	public List<Usuario> getPosiblesReceptoresCoordinadoresDeMensaje(){	
		return SisopAdminServiceProvider.getPersistenceService().createCriteria(Coordinador.class).list();
	}
	
	public String getNombreCompleto(){
		return ObjectUtils.defaultIfNull(nombre, "") + " " + ObjectUtils.defaultIfNull(apellido, "");
	}


}