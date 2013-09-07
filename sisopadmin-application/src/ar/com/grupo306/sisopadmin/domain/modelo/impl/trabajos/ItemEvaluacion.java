package ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;

public class ItemEvaluacion extends ModelObject{

	String obsGenerales;
	String obsParticulares;
	int porcentaje;
	long puntaje;
	
	
	public String getObsGenerales() {
		return obsGenerales;
	}
	public void setObsGenerales(String obsGenerales) {
		this.obsGenerales = obsGenerales;
	}
	public String getObsParticulares() {
		return obsParticulares;
	}
	public void setObsParticulares(String obsParticulares) {
		this.obsParticulares = obsParticulares;
	}
	public int getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(int porcentaje) {
		this.porcentaje = porcentaje;
	}
	public long getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(long puntaje) {
		this.puntaje = puntaje;
	}
	
	
}
