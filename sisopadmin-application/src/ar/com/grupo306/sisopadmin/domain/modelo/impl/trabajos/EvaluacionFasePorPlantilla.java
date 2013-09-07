package ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.estados.ConEstadoModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.estados.Estado;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.persistence.impl.hibernate.utils.HibernateUtils;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * 
 * @author Rafa
 */
public class EvaluacionFasePorPlantilla extends ConEstadoModelObject {
	public static String ESTADO_CODE_COMPLETA= "COMPLETA";
	public static String ESTADO_CODE_INCOMPLETA = "INCOMPLETA";
	
	private Grupo grupo;
	private Ayudante ayudanteEvaluador;
	private Fase fase;
	private Set<ItemEvaluacion> itemsEvaluacion;
	private PlantillaCorreccion plantilla;
	private Boolean plantillaAprobada;
	
	/**
	 * Crea los items de evaluación en base a los items de la plantilla a evaluar.
	 */
	public void crearItemsEvaluacionDesdePlantilla() {
		if(plantilla == null) {
			throw ExceptionFactory.createBusinessException("La evaluación debe estar asociada a una plantilla");
		}
		
		itemsEvaluacion = CollectionFactory.createSet(ItemEvaluacion.class);
		for(ItemPlantilla itemPlantilla : plantilla.getItems()) {
			ItemEvaluacion itemEvaluacion = new ItemEvaluacion();
			itemEvaluacion.setItemPlantilla(itemPlantilla);
			itemEvaluacion.setEstado(Estado.findMeByDomainCode(ItemEvaluacion.ESTADO_CODE_NOCORREGIDO));
			
			itemsEvaluacion.add(itemEvaluacion);
		}
		setEstado(Estado.findMeByDomainCode(ESTADO_CODE_INCOMPLETA));
	}
	
	public Integer getSumatoriaPesosItemCorregidos() {
		Integer sumatoria = 0;
		for(ItemEvaluacion itemEvaluacion : itemsEvaluacion) {
			if(itemEvaluacion.estaCorregido()) {
				sumatoria += itemEvaluacion.getItemPlantilla().getPeso();
			}
		}
		return sumatoria;
	}
	
	/**
	 * Actualiza el estado de la Evaluacion en base a sus ItemEvaluacion.
	 */
	public void actualizarEstado() {
		for(ItemEvaluacion itemEvaluacion : itemsEvaluacion) {
			// Si el ítem es obligatorio y no fue evaluado entonces se modifica el estado de la EvaluacionFase a Incompleta.
			if(itemEvaluacion.esObligatorio() && !itemEvaluacion.estaCorregido()) {
				setEstado(Estado.findMeByDomainCode(ESTADO_CODE_INCOMPLETA));
				return;
			}
		}
		setEstado(Estado.findMeByDomainCode(ESTADO_CODE_COMPLETA));
	}
	
	public boolean estaCompleta() {
		return getEstado().equals(Estado.findMeByDomainCode(ESTADO_CODE_COMPLETA));
	}
	
	public static List<EvaluacionFasePorPlantilla> findBy(Long idEstado, Long idTp, Long idFase, Long idAyudante, Long idGrupo) {
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(EvaluacionFasePorPlantilla.class);

		HibernateUtils.addCriterionIfPropertyNotNullOrEmpty(criteria, Restrictions.eq("estado.id", idEstado), idEstado);
//		HibernateUtils.addCriterionIfPropertyNotNullOrEmpty(criteria, Restrictions.eq("fase.tp.id", idTp), idTp);
		HibernateUtils.addCriterionIfPropertyNotNullOrEmpty(criteria, Restrictions.eq("fase.id", idFase), idFase);
		HibernateUtils.addCriterionIfPropertyNotNullOrEmpty(criteria, Restrictions.eq("ayudanteEvaluador.id", idAyudante), idAyudante);
		HibernateUtils.addCriterionIfPropertyNotNullOrEmpty(criteria, Restrictions.eq("grupo.id", idGrupo), idGrupo);
		
		return (List<EvaluacionFasePorPlantilla>) criteria.list();
	}
	
	public PlantillaCorreccion getPlantilla() {
		return plantilla;
	}
	
	public void setPlantilla(PlantillaCorreccion plantilla) {
		this.plantilla = plantilla;
	}
	
	public Grupo getGrupo() {
		return grupo;
	}
	
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	
	public Ayudante getAyudanteEvaluador() {
		return ayudanteEvaluador;
	}
	
	public void setAyudanteEvaluador(Ayudante ayudanteEvaluador) {
		this.ayudanteEvaluador = ayudanteEvaluador;
	}
	
	public Set<ItemEvaluacion> getItemsEvaluacion() {
		return itemsEvaluacion;
	}
	
	public void setItemsEvaluacion(Set<ItemEvaluacion> itemsCorregidos) {
		this.itemsEvaluacion = itemsCorregidos;
	}

	public Fase getFase() {
		return fase;
	}

	public void setFase(Fase faseEvaluada) {
		this.fase = faseEvaluada;
	}

	public Boolean getPlantillaAprobada() {
		return plantillaAprobada;
	}

	public void setPlantillaAprobada(Boolean plantillaAprobada) {
		this.plantillaAprobada = plantillaAprobada;
	}

}