package ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.algoritmosAsignacionGruposAAyudantes;

import java.util.List;

import ar.com.grupo306.commons.exceptions.core.BusinessException;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;

public class FIFO extends Algoritmo {

	protected void inscribirConAyudante(List<Grupo> grupos, List<Ayudante> ayudantes){
		int grupoIndex = 0;
		for (Ayudante ayudante : ayudantes){
			
			int cantGruposDelAyudante = ayudante.getGrupos().size();
			while (cantGruposDelAyudante < ayudante.getMaximaCantidadGrupos() &&
				   grupoIndex < grupos.size()){			
				Grupo grupo = grupos.get(grupoIndex);
				grupo.setAyudante(ayudante);
				grupo.update();
				cantGruposDelAyudante++;
				grupoIndex++; 
			}
			
		}
		
		if (grupos.size() > grupoIndex){
			throw new BusinessException("La cantidad total de grupos admitidos por los ayudantes no es suficiente.");	
		}
	}

}
