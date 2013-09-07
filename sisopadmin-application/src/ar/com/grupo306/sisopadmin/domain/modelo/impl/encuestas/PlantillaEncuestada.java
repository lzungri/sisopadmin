package ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas;

import java.util.ArrayList;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;

public class PlantillaEncuestada extends PlantillaEncuesta{
	
	public PlantillaEncuestada(String nombrePlantilla, String fechaInicio, String fechaFin, boolean obligatoria, Usuario usuario) {
		super(nombrePlantilla, fechaInicio, fechaFin, obligatoria, usuario);
		
	}
	ArrayList puntosEncuestados;
	
	public ArrayList getPuntosEncuestados() {
		return puntosEncuestados;
	}
	public void setPuntosEncuestados(ArrayList puntosEncuestados) {
		this.puntosEncuestados = puntosEncuestados;
	}
}
