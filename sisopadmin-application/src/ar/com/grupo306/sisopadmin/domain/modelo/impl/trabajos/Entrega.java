package ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.CodedModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.persistence.impl.hibernate.utils.HibernateUtils;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * Representa una entrega realizada por un grupo.
 * La entrega puede basarse en la subida de un archivo o en la indicación de tomar el código del cvs.
 *
 * @author Leandro
 */
public class Entrega extends CodedModelObject {
	private Grupo grupo;
	private Fase fase;
	private Date fechaEntrega;
	private String pathArchivo;
	private boolean descargarDeCVS;
	private String observaciones;
	
	public static List<Entrega> findBy(Long grupoId, Long faseId) {
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Entrega.class);
		HibernateUtils.addCriterionIfPropertyNotNullOrEmpty(criteria, Restrictions.eq("grupo.id", grupoId), grupoId);
		HibernateUtils.addCriterionIfPropertyNotNullOrEmpty(criteria, Restrictions.eq("fase.id", faseId), faseId);
		
		return (List<Entrega>) criteria.list();
	}
	
	public Fase getFase() {
		return fase;
	}
	
	public void setFase(Fase fase) {
		this.fase = fase;
	}
	
	public Date getFechaEntrega() {
		return fechaEntrega;
	}
	
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	
	public String getPathArchivo() {
		return pathArchivo;
	}
	
	public void setPathArchivo(String pathArchivo) {
		this.pathArchivo = pathArchivo;
	}
	
	public boolean getDescargarDeCVS() {
		return descargarDeCVS;
	}
	
	public void setDescargarDeCVS(boolean descargarDeCVS) {
		this.descargarDeCVS = descargarDeCVS;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

}