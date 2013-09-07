package ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Coordinador;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.base.BaseUseCaseModel;

public class AsignarAyudantesACoordinadorUseCaseModel  extends BaseUseCaseModel {
	
	Coordinador coordinador;
	Set<Ayudante> ayudantesDisponibles = CollectionFactory.createSet(Ayudante.class	);
	Set<Ayudante> ayudantesAsignados = CollectionFactory.createSet(Ayudante.class);
	Long indexAsignados, indexDisponibles;
	
	@Transactional
	@Secure ({})
	@Description ("Acceder")
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);	
			
		coordinador = (Coordinador) context.getParameters().get(ABMBaseUseCaseModel.ENTITY_KEY);		
		LlenarGrilla();
	}
	
	@Transactional
	public void asignar(UseCaseContext context) {
		Ayudante ayudanteAsig = Ayudante.findMeById(indexDisponibles);
		ayudantesDisponibles.remove(ayudanteAsig);
		ayudantesAsignados.add(ayudanteAsig);
	}
	
	@Transactional
	public void desasignar(UseCaseContext context) {
		Ayudante ayudanteDesAsig = Ayudante.findMeById(indexAsignados);
		ayudantesAsignados.remove(ayudanteDesAsig);
		ayudantesDisponibles.add(ayudanteDesAsig);
	
	}	
	
	@Transactional
	public void aceptar(UseCaseContext context){
		coordinador.setAyudantes(ayudantesAsignados);		
		
		for (Iterator it=ayudantesDisponibles.iterator(); it.hasNext(); ) {
			Ayudante ayuDisp = ((Ayudante)it.next()); 
			ayuDisp.update();			
		}
	
		for (Iterator it=ayudantesAsignados.iterator(); it.hasNext(); ) {
			Ayudante ayuAsig = ((Ayudante)it.next()); 
			ayuAsig.update();			
		}				
		coordinador.update();
		
		context.acceptUseCase();
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
	private void LlenarGrilla(){		
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