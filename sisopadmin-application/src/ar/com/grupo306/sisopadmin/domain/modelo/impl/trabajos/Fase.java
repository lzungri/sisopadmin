package ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

public class Fase extends ModelObject{
	boolean entregaObligatoria;
	Date fechaFin;
	Date fechaInicio;
	String nombre;
	Integer numero;
	Collection plantillasCorreccion;
	Set<Consigna> consignas;
	Tp tp;
	
	
	public boolean isEntregaObligatoria() {
		return entregaObligatoria;
	}
	public void setEntregaObligatoria(boolean entregaObligatoria) {
		this.entregaObligatoria = entregaObligatoria;
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
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Set<Consigna> getConsignas() {
		if(consignas == null)
			consignas =  CollectionFactory.createSet(Consigna.class);
		return consignas;
	}
	public void setConsignas(Set<Consigna> consignas) {
		this.consignas = consignas;
	}
	public Collection getPlantillasCorreccion() {
		return plantillasCorreccion;
	}
	public void setPlantillasCorreccion(Collection plantillasCorreccion) {
		this.plantillasCorreccion = plantillasCorreccion;
	}
	public Tp getTp() {
		return tp;
	}
	public void setTp(Tp tp) {
		this.tp = tp;
	}
	
	public static Fase findMeById(Long id){
		Criteria criteria;
		criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Fase.class);
		criteria.add(Expression.eq(ID,id));
		
		return (Fase) criteria.uniqueResult();	
	}
	
	
	/**
	 * Retorna las RelacionPlantillaEvaluacion para un determinado ayudante y grupo sobre esta fase.
	 * 
	 * @param ayudante
	 * @param grupo
	 */
	public EvaluacionFaseConsolidada getEvaluacionFaseConsolidada(Ayudante ayudante, Grupo grupo) {
		if(ayudante == null || grupo == null) {
			throw ExceptionFactory.createProgramException("El ayudante o grupo no fueron indicados.");
		}
		
		EvaluacionFaseConsolidada evaluacionConsolidada = new EvaluacionFaseConsolidada();
		
		List<PlantillaCorreccion> plantillasFase = PlantillaCorreccion.findByFase(this);
		List<EvaluacionFasePorPlantilla> evaluacionesFaseDeGrupo = EvaluacionFasePorPlantilla.findBy(null, tp.getId(), this.getId(), ayudante.getId(), grupo.getId());
		
		// Por cada plantilla se genera un RelacionPlantillaEvaluacion.
		for(PlantillaCorreccion plantilla : plantillasFase) {
			RelacionPlantillaEvaluacion relacion = new RelacionPlantillaEvaluacion();
			relacion.setPlantilla(plantilla);
			
			for(EvaluacionFasePorPlantilla evaluacion : evaluacionesFaseDeGrupo) {
				if(evaluacion.getPlantilla().equals(plantilla)) {
					relacion.setEvaluacion(evaluacion);
					evaluacionesFaseDeGrupo.remove(evaluacion);
					break;
				}
			}
			
			evaluacionConsolidada.addRelacion(relacion);
		}
		
		return evaluacionConsolidada;
	}
	
	public static Fase findMeByNombreTPYNumeroFase(String nombreTP, Integer numeroFase){
		return (Fase) SisopAdminServiceProvider.getPersistenceService().createQuery(
				"from Fase fase " + 
				"where fase.tp.nombre = :nombreTP " +
				"and fase.numero = :numeroFase")
				.setParameter("nombreTP", nombreTP)
				.setParameter("numeroFase", numeroFase)
				.uniqueResult();		
	}

	
}