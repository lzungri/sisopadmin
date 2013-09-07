package ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.comparator;

import java.util.Comparator;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
/**
 * Utilizado para comparar Plantillas de encuestas por el nombre.
 * @author yaprgn
 *
 */
public class NombreComparator implements Comparator{

	public int compare(Object o1, Object o2) {
		PlantillaEncuesta plantilla1 = (PlantillaEncuesta)o1;
		PlantillaEncuesta plantilla2 = (PlantillaEncuesta)o2;
		return plantilla1.getNombre().equalsIgnoreCase(plantilla2.getNombre()) == true ? 0:1;
	}

}
