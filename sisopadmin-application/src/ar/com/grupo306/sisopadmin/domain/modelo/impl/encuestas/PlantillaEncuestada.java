package ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas;

import java.util.Set;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;

public class PlantillaEncuestada extends PlantillaEncuesta{
	
	public PlantillaEncuestada(String nombrePlantilla, String fechaInicio, String fechaFin, boolean obligatoria, Usuario usuario) {
		super(nombrePlantilla, fechaInicio, fechaFin, obligatoria, usuario);
		
	}
	
	public PlantillaEncuestada(){
		super();
	}
	
	Set puntosEncuestados;
	PlantillaEncuesta plantillaEncuesta;
	
	public Set getPuntosEncuestados() {
		return puntosEncuestados;
	}
	public void setPuntosEncuestados(Set puntosEncuestados) {
		this.puntosEncuestados = puntosEncuestados;
	}

	public PlantillaEncuesta getPlantillaEncuesta() {
		return plantillaEncuesta;
	}

	public void setPlantillaEncuesta(PlantillaEncuesta plantillaEncuesta) {
		this.plantillaEncuesta = plantillaEncuesta;
	}
}
