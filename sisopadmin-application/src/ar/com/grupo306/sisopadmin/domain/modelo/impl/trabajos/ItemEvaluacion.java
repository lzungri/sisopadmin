package ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.estados.ConEstadoModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.estados.Estado;

public class ItemEvaluacion extends ConEstadoModelObject {
	public static String ESTADO_CODE_CORREGIDO = "CORREGIDO";
	public static String ESTADO_CODE_NOCORREGIDO = "NO_CORREGIDO";

	private ItemPlantilla itemPlantilla;
	private String observaciones;
	private Integer porcentajeCumplimiento;
	
	
	public boolean estaCorregido() {
		return getEstado().equals(Estado.findMeByDomainCode(ItemEvaluacion.ESTADO_CODE_CORREGIDO));
	}
	
	public boolean esObligatorio() {
		return getItemPlantilla().getObligatorio() != null && getItemPlantilla().getObligatorio().booleanValue();
	}
	
	public String getObservaciones() {
		return observaciones;
	}
	
	public void setObservaciones(String obsParticulares) {
		this.observaciones = obsParticulares;
	}
	
	public Integer getPorcentajeCumplimiento() {
		return porcentajeCumplimiento;
	}
	
	public void setPorcentajeCumplimiento(Integer porcentaje) {
		this.porcentajeCumplimiento = porcentaje;
	}

	public ItemPlantilla getItemPlantilla() {
		return itemPlantilla;
	}

	public void setItemPlantilla(ItemPlantilla itemPlantilla) {
		this.itemPlantilla = itemPlantilla;
	}
	
}