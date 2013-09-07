package ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos;

import java.util.ArrayList;
import java.util.Collection;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;

public class PlantillaCorreccion extends ModelObject{
	float pesoPlantilla;
	Collection items;

	public float getPesoPlantilla() {
		return pesoPlantilla;
	}

	public void setPesoPlantilla(float pesoPlantilla) {
		this.pesoPlantilla = pesoPlantilla;
	}

	public Collection getItems() {
		return items;
	}

	public void setItems(Collection items) {
		this.items = items;
	}
}
