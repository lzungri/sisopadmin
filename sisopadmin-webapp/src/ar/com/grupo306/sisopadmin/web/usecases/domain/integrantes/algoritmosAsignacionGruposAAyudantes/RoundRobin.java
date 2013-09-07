package ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.algoritmosAsignacionGruposAAyudantes;

import java.util.List;

import ar.com.grupo306.commons.exceptions.core.BusinessException;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;


public class RoundRobin extends Algoritmo {

	protected void inscribirConAyudante(List<Grupo> grupos, List<Ayudante> ayudantes){		
		for (int i = 0; i < grupos.size(); i++){
			Grupo grupo = grupos.get(i);
			
			Ayudante ayudante = ayudantes.get(i % ayudantes.size());
			while (ayudante.getGrupos().size() == ayudante.getMaximaCantidadGrupos()){
				ayudantes.remove(i % ayudantes.size());
				if (ayudantes.size() > 0){
					ayudante = ayudantes.get(i % ayudantes.size());
				} else {					
					throw new BusinessException("La cantidad total de grupos admitidos por los ayudantes no es suficiente.");
				}
			}			
			
			ayudante.getGrupos().add(grupo);			
			grupo.setAyudante(ayudante);			
			grupo.update();
		}
	}

}
