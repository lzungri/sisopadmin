package ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Coordinador;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.base.BaseUseCaseModel;

public class AsignarAyudantesACoordinadorUseCaseModel  extends BaseUseCaseModel {
	
	Coordinador coordinador;
	Set<Ayudante> ayudantesDisponibles = CollectionFactory.createSet(Ayudante.class	);
	Set<Ayudante> ayudantesAsignados = CollectionFactory.createSet(Ayudante.class);
	Long indexAsignados, indexDisponibles;
	
	@Transactional
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);	
			
		coordinador = (Coordinador) context.getParameters().get(ABMBaseUseCaseModel.ENTITY_KEY);		
		refrescarGrilla();
	}
	
	@Transactional
	public void asignar(UseCaseContext context) {
		Set ayudantesAsignados;
		
		Ayudante ayudanteAsig = Ayudante.findMeById(indexDisponibles);
		if (coordinador.getAyudantes() == null || coordinador.getAyudantes().size() == 0)
			 ayudantesAsignados = CollectionFactory.createSet(Ayudante.class);
		else
			ayudantesAsignados = coordinador.getAyudantes();
		ayudantesAsignados.add(ayudanteAsig);
		coordinador.setAyudantes(ayudantesAsignados);
		ayudanteAsig.update();
		coordinador.update();
		
		refrescarGrilla();
	}
	
	@Transactional
	public void desasignar(UseCaseContext context) {
		Ayudante ayudanteDesAsig = Ayudante.findMeById(indexAsignados);
		Set<Ayudante> ayu = coordinador.getAyudantes();
		ayu.remove(ayudanteDesAsig); 
		
		coordinador.setAyudantes(ayu);
		ayudanteDesAsig.update();  
		coordinador.update();
		
		refrescarGrilla();
	}	

	public Set<Ayudante> getAyudantesAsignados() {
		return ayudantesAsignados;
	}

	public void setAyudantesAsignados(Set<Ayudante> ayudantesAsignados) {
		this.ayudantesAsignados = ayudantesAsignados;
	}

	public Set<Ayudante> getAyudantesDisponibles() {
		return ayudantesDisponibles;
	}

	public void setAyudantesDisponibles(Set<Ayudante> ayudantesDisponibles) {
		this.ayudantesDisponibles = ayudantesDisponibles;
	}

	public Long getIndexAsignados() {
		return indexAsignados;
	}

	public void setIndexAsignados(Long indexAsignados) {
		this.indexAsignados = indexAsignados;
	}

	public Long getIndexDisponibles() {
		return indexDisponibles;
	}

	public void setIndexDisponibles(Long indexDisponibles) {
		this.indexDisponibles = indexDisponibles;
	}	
	
	@Transactional
	private void refrescarGrilla(){		
		ayudantesAsignados.clear();
		ayudantesDisponibles.clear();
		ArrayList<Ayudante> ayudantesAsig = (ArrayList<Ayudante>)Ayudante.findByCoordinadorAsignado(coordinador.getId());
		Iterator<Ayudante> ayudantesIterator = ayudantesAsig.iterator();
		while (ayudantesIterator.hasNext( )) {
			ayudantesAsignados.add(ayudantesIterator.next());
		}
				
		ArrayList<Ayudante> ayudantesDisp = (ArrayList<Ayudante>)Ayudante.findSinCoordinador();
		Iterator<Ayudante> disponiblesIterator = ayudantesDisp.iterator();
		while (disponiblesIterator.hasNext( )) {
			ayudantesDisponibles.add(disponiblesIterator.next());
		}		
	}

	public Coordinador getCoordinador() {
		return coordinador;
	}

}