package ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;

public class ItemPlantilla extends ModelObject {
	int numeroConsigna;
	boolean obligatorio;
	String observacionBajaCalificacion;
	float peso;
	String procedimiento;
	String resultadoEsperado;
	
	
	public int getNumeroConsigna() {
		return numeroConsigna;
	}
	public void setNumeroConsigna(int numeroConsigna) {
		this.numeroConsigna = numeroConsigna;
	}
	public boolean isObligatorio() {
		return obligatorio;
	}
	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}
	public String getObservacionBajaCalificacion() {
		return observacionBajaCalificacion;
	}
	public void setObservacionBajaCalificacion(String observacionBajaCalificacion) {
		this.observacionBajaCalificacion = observacionBajaCalificacion;
	}
	public float getPeso() {
		return peso;
	}
	public void setPeso(float peso) {
		this.peso = peso;
	}
	public String getProcedimiento() {
		return procedimiento;
	}
	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}
	public String getResultadoEsperado() {
		return resultadoEsperado;
	}
	public void setResultadoEsperado(String resultadoEsperado) {
		this.resultadoEsperado = resultadoEsperado;
	}
}
