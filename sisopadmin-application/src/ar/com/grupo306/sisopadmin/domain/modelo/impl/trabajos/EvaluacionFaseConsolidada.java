package ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos;

import java.util.List;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.factorys.ExceptionFactory;

/**
 * Agrupa las RelacionPlantillaEvaluacion que fueron realizadas para una determinada
 * Fase sobre un determinado Grupo.
 *
 * @author Leandro
 */
public class EvaluacionFaseConsolidada {
	private List<RelacionPlantillaEvaluacion> relaciones = CollectionFactory.createList(RelacionPlantillaEvaluacion.class);
	private Double porcentajeEvaluado;
	private Double porcentajeCumplimientoFase;
	

	/**
	 * Procesa las evaluaciones de fase realizadas y genera los datos para llenar el informe de evaluación.
	 */
	public void procesarEvaluaciones() {
		if(!estaCompleta()) {
			throw ExceptionFactory.createBusinessException("Existe al menos una plantilla obligatoria que aún no fue totalmente evaluada.");
		}
		
		porcentajeCumplimientoFase = 0d;
		porcentajeEvaluado = 0d;
		Integer sumatoriaPesosPlantillas = getSumatoriaPesosPlantillas();
		Integer sumatoriaPesosPlantillasCorregidas = getSumatoriaPesosPlantillasCorregidas();
		
		if(sumatoriaPesosPlantillas <= 0) {
			throw ExceptionFactory.createBusinessException("Verifique que la plantilla se encuentre correctamente cargada.");
		}
		if(sumatoriaPesosPlantillasCorregidas <= 0) {
			throw ExceptionFactory.createBusinessException("No se ha corregido ninguna plantilla.");
		}
		
		for(RelacionPlantillaEvaluacion relacion : relaciones) {
			relacion.procesarEvaluacion();
			
			porcentajeEvaluado += relacion.getPorcentajeEvaluado().doubleValue() * relacion.getPlantilla().getPeso().doubleValue() / sumatoriaPesosPlantillas.doubleValue();
			porcentajeCumplimientoFase += relacion.getPorcentajeCumplimientoPlantilla().doubleValue() * relacion.getPlantilla().getPeso().doubleValue() / sumatoriaPesosPlantillasCorregidas.doubleValue();
		}
	}
	
	/**
	 * Indica si la evaluación de la fase tiene minimamente todas las plantillas obligatorias corregidas.
	 */
	public boolean estaCompleta() {
		for(RelacionPlantillaEvaluacion relacion : relaciones) {
			if(relacion.getPlantilla().getObligatoria() && !relacion.estaCompleta()) {
				return false;
			}
		}
		return true;
	}
	
	public Integer getSumatoriaPesosPlantillas() {
		Integer sumatoria = 0;
		
		for(RelacionPlantillaEvaluacion relacion: relaciones) {
			sumatoria += relacion.getPlantilla().getPeso();
		}
		return sumatoria;
	}
	
	public Integer getSumatoriaPesosPlantillasCorregidas() {
		Integer sumatoria = 0;
		
		for(RelacionPlantillaEvaluacion relacion: relaciones) {
			if(relacion.estaCompleta()) {
				sumatoria += relacion.getPlantilla().getPeso();
			}
		}
		return sumatoria;
	}
	
	public void addRelacion(RelacionPlantillaEvaluacion relacion) {
		relaciones.add(relacion);
	}
	
	public List<RelacionPlantillaEvaluacion> getRelaciones() {
		return relaciones;
	}

	public void setRelaciones(List<RelacionPlantillaEvaluacion> relaciones) {
		this.relaciones = relaciones;
	}

	public Double getPorcentajeCumplimientoFase() {
		return porcentajeCumplimientoFase;
	}

	public void setPorcentajeCumplimientoFase(Double porcentajeAprobacion) {
		this.porcentajeCumplimientoFase = porcentajeAprobacion;
	}

	public Double getPorcentajeEvaluado() {
		return porcentajeEvaluado;
	}

	public void setPorcentajeEvaluado(Double porcentajeEvaluado) {
		this.porcentajeEvaluado = porcentajeEvaluado;
	}
	
}