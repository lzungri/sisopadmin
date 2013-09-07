package ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos;

import ar.com.grupo306.commons.factorys.ExceptionFactory;

/**
 * Relaciona una plantilla con su evaluación (Si fue efecutada).
 *
 * @author Leandro
 */
public class RelacionPlantillaEvaluacion {
	private PlantillaCorreccion plantilla;
	private EvaluacionFasePorPlantilla evaluacion;
	
	private Double porcentajeEvaluado;
	private Double porcentajeCumplimientoPlantilla;
	
	/**
	 * Procesa la evaluación de fase y genera los datos para llenar el informe de evaluación.
	 */
	public void procesarEvaluacion() {
		porcentajeCumplimientoPlantilla = 0d;
		porcentajeEvaluado = 0d;
		
		if(plantilla.getSumatoriaPesosItems() <= 0) {
			throw ExceptionFactory.createBusinessException("Verifique que los ítems de plantilla se encuentren correctamente cargados.");
		}
		
		// Si evaluó la plantilla.
		if(evaluacion != null) {
			Integer sumatoriaPesosItemsCorregidos = evaluacion.getSumatoriaPesosItemCorregidos();
			if(sumatoriaPesosItemsCorregidos <= 0) {
				return; // No debería ingresar acá si se valida que al menos un ítem sea obligatorio.
			}
			
			porcentajeEvaluado = sumatoriaPesosItemsCorregidos.doubleValue() / plantilla.getSumatoriaPesosItems().doubleValue() * 100;
			for(ItemEvaluacion itemEvaluacion : evaluacion.getItemsEvaluacion()) {
				if(itemEvaluacion.estaCorregido()) {
					porcentajeCumplimientoPlantilla += itemEvaluacion.getPorcentajeCumplimiento().doubleValue() * itemEvaluacion.getItemPlantilla().getPeso().doubleValue() / sumatoriaPesosItemsCorregidos.doubleValue();
				}
			}
		}
	}
	
	/**
	 * Indica si la evaluación de la plantilla está completa.
	 */
	public boolean estaCompleta() {
		return evaluacion != null && evaluacion.estaCompleta();
	}
	
	public PlantillaCorreccion getPlantilla() {
		return plantilla;
	}
	public void setPlantilla(PlantillaCorreccion plantilla) {
		this.plantilla = plantilla;
	}
	public EvaluacionFasePorPlantilla getEvaluacion() {
		return evaluacion;
	}
	public void setEvaluacion(EvaluacionFasePorPlantilla evaluacion) {
		this.evaluacion = evaluacion;
	}
	public Double getPorcentajeEvaluado() {
		return porcentajeEvaluado;
	}
	public void setPorcentajeEvaluado(Double porcentajeEvaluado) {
		this.porcentajeEvaluado = porcentajeEvaluado;
	}
	public Double getPorcentajeCumplimientoPlantilla() {
		return porcentajeCumplimientoPlantilla;
	}
	public void setPorcentajeCumplimientoPlantilla(Double porcentajeAprobacion) {
		this.porcentajeCumplimientoPlantilla = porcentajeAprobacion;
	}
}