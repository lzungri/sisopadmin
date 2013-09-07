package ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.estados.ConEstadoModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.estados.Estado;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.persistence.impl.hibernate.utils.HibernateUtils;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * Representa un informe que genera el ayudante al evaluar una fase a un grupo.
 * 
 * @author Rafa
 */
public class InformeEvaluacionFase extends ConEstadoModelObject{
	public static String ESTADO_CODE_ENVIADA = "ENVIADO";
	public static String ESTADO_CODE_NOENVIADA = "NO_ENVIADO";
	
	private Fase fase;
	private Ayudante ayudanteEvaluador;
	private Grupo grupoEvaluado;
	private Entrega entregaEvaluada;
	
	private Date fechaAlta;
	private Date fechaEnvio;
	
	private String pathInforme;
	
	private boolean faseAprobada;
	private Double porcentajeEvaluado;
	private Double porcentajeCumplimientoFase;
	
	
	public void enviar() {
		if(getEstado().getDomainCode().equals(InformeEvaluacionFase.ESTADO_CODE_ENVIADA)) {
			throw ExceptionFactory.createBusinessException("El informe ya se encuentra enviado.");
		}
		setEstado(Estado.findMeByDomainCode(InformeEvaluacionFase.ESTADO_CODE_ENVIADA));
		saveOrUpdate();
	}
	
	public static List<InformeEvaluacionFase> findBy(Long estadoId, Long ayudanteId, Long grupoId) {
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(InformeEvaluacionFase.class);
		HibernateUtils.addCriterionIfPropertyNotNullOrEmpty(criteria, Restrictions.eq("estado.id", estadoId), estadoId);
		HibernateUtils.addCriterionIfPropertyNotNullOrEmpty(criteria, Restrictions.eq("grupoEvaluado.id", grupoId), grupoId);
		HibernateUtils.addCriterionIfPropertyNotNullOrEmpty(criteria, Restrictions.eq("ayudanteEvaluador.id", ayudanteId), ayudanteId);
		
		return (List<InformeEvaluacionFase>) criteria.list();
	}
	
	public Double getPorcentajeCumplimientoFase() {
		return porcentajeCumplimientoFase;
	}
	public void setPorcentajeCumplimientoFase(Double porcentajeAprobacion) {
		this.porcentajeCumplimientoFase = porcentajeAprobacion;
	}
	public Ayudante getAyudanteEvaluador() {
		return ayudanteEvaluador;
	}
	public void setAyudanteEvaluador(Ayudante ayudanteEvaluador) {
		this.ayudanteEvaluador = ayudanteEvaluador;
	}
	public boolean getFaseAprobada() {
		return faseAprobada;
	}
	public void setFaseAprobada(boolean faseAprobada) {
		this.faseAprobada = faseAprobada;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Grupo getGrupoEvaluado() {
		return grupoEvaluado;
	}
	public void setGrupoEvaluado(Grupo grupoEvaluado) {
		this.grupoEvaluado = grupoEvaluado;
	}
	public String getPathInforme() {
		return pathInforme;
	}
	public void setPathInforme(String pathInforme) {
		this.pathInforme = pathInforme;
	}
	public Fase getFase() {
		return fase;
	}
	public void setFase(Fase fase) {
		this.fase = fase;
	}
	public Date getFechaEnvio() {
		return fechaEnvio;
	}
	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
	public Double getPorcentajeEvaluado() {
		return porcentajeEvaluado;
	}
	public void setPorcentajeEvaluado(Double porcentajeEvaluado) {
		this.porcentajeEvaluado = porcentajeEvaluado;
	}

	public Entrega getEntregaEvaluada() {
		return entregaEvaluada;
	}

	public void setEntregaEvaluada(Entrega entregaEvaluada) {
		this.entregaEvaluada = entregaEvaluada;
	}
}