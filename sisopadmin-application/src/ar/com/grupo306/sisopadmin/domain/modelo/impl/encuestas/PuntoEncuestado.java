package ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;

public class PuntoEncuestado extends ModelObject{
	Long puntuacion;
	PuntoAEncuestar puntoAEncuestar;
	PlantillaEncuestada plantillaEncuestada;

	public PuntoAEncuestar getPuntoAEncuestar() {
		return puntoAEncuestar;
	}

	public void setPuntoAEncuestar(PuntoAEncuestar puntoAEncuestar) {
		this.puntoAEncuestar = puntoAEncuestar;
	}

	public Long getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(Long puntuacion) {
		this.puntuacion = puntuacion;
	}

	public PlantillaEncuestada getPlantillaEncuestada() {
		return plantillaEncuestada;
	}

	public void setPlantillaEncuestada(PlantillaEncuestada plantillaEncuestada) {
		this.plantillaEncuestada = plantillaEncuestada;
	}
}
