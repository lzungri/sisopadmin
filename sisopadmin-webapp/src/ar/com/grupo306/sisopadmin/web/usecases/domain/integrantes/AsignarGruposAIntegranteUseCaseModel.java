/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseValidationUtils;

/**
 * @author Sole
 *
 */
public class AsignarGruposAIntegranteUseCaseModel extends ABMBaseUseCaseModel {
	Ayudante ayudante;
		
	private Collection<Grupo> gruposAsignados = CollectionFactory.createCollection(Grupo.class);
	private Collection<Grupo> gruposDisponibles = CollectionFactory.createCollection(Grupo.class);
	private String[] selectedAsignados = null;
	private String[] selectedDisponibles = null;
	private Long id = 0L;
	private int entradas=0;
	private String tam;
	

	@Secure({})
	@Description("Asignar grupos a integrante manualmente")
	@Transactional
	public void initUseCase(UseCaseContext context) {
		// Cargo la lista de permisos disponibles
		super.initUseCase(context);
		ayudante = (Ayudante) context.getParameter("ayudante");
		gruposAsignados = ayudante.getGrupos();
		gruposDisponibles = Grupo.findInscriptosSinAyudante();		 
		
	}
	
	@Transactional
	public void modificar(UseCaseContext context) {
		gruposAsignados = buscarGruposSeleccionados(context, "gruposAsignados");
		gruposDisponibles = buscarGruposSeleccionados(context,"gruposDisponibles");
		
		if(!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}
						
		for(Iterator it = gruposAsignados.iterator(); it.hasNext();){
			Grupo grupo = (Grupo) it.next();
			grupo.setAyudante(ayudante);
			grupo.update();
		}
		
		for(Iterator it = gruposDisponibles.iterator(); it.hasNext();){
			Grupo grupo = (Grupo) it.next();
			grupo.setAyudante(null);
			grupo.update();
		}
		
		context.addMessage("Los grupos se han asignado exitosamente.");
		context.acceptUseCase();
	}

	public void cancelar(UseCaseContext context) {
		context.cancelUseCase();
	}

	private boolean validacionOK(UseCaseValidationUtils validationUtils) {
		validationUtils.assertTrue(gruposAsignados.size() <= ayudante.getMaximaCantidadGrupos() ,"La cantidad de grupos asignados al ayudantes es superior a los " + ayudante.getMaximaCantidadGrupos() + " que puede corregir");		
		return !validationUtils.hasErrors();
	}


	@Transactional
	private Collection<Grupo> buscarGruposSeleccionados(UseCaseContext context, String param) {
		Collection<Grupo> grupos = CollectionFactory.createCollection(Grupo.class);
		String[] gruposSelected = null;

		try {
			gruposSelected = context.getRequestParameterValues(param);
		} catch (Exception e) {
		}

		if (gruposSelected != null) {
			for (int i = 0; i < gruposSelected.length; i++) {
				grupos.add((Grupo) Grupo.findMeById(Long.valueOf(gruposSelected[i])));
			}
		}
		return ordenarGrupos(grupos);
	}

	//Ordenar Roles
	private Collection ordenarGrupos(Collection colAOrdenar){
		//Para que los roles aparezcan ordenados
		SortedSet gruposOrdenados = CollectionFactory.createTreeSet(new Comparator() {	
											public int compare(Object o1, Object o2) {
												return((Grupo)o1).getNombre().compareTo(((Grupo)o2).getNombre());				
											}		
										});
		if(colAOrdenar!=null){
			gruposOrdenados.addAll(colAOrdenar);
		}
		return gruposOrdenados;
	}
	
	public Ayudante getAyudante() {
		return ayudante;
	}

	public void setAyudante(Ayudante ayudante) {
		this.ayudante = ayudante;
	}

	public int getEntradas() {
		return entradas;
	}

	public void setEntradas(int entradas) {
		this.entradas = entradas;
	}

	public Collection<Grupo> getGruposAsignados() {
		return gruposAsignados;
	}

	public void setGruposAsignados(Collection<Grupo> gruposAsignados) {
		this.gruposAsignados = gruposAsignados;
	}

	public Collection<Grupo> getGruposDisponibles() {
		return gruposDisponibles;
	}

	public void setGruposDisponibles(Collection<Grupo> gruposDisponibles) {
		this.gruposDisponibles = gruposDisponibles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String[] getSelectedAsignados() {
		return selectedAsignados;
	}

	public void setSelectedAsignados(String[] selectedAsignados) {
		this.selectedAsignados = selectedAsignados;
	}

	public String[] getSelectedDisponibles() {
		return selectedDisponibles;
	}

	public void setSelectedDisponibles(String[] selectedDisponibles) {
		this.selectedDisponibles = selectedDisponibles;
	}

	public String getTam() {
		return tam;
	}

	public void setTam(String tam) {
		this.tam = tam;
	}

	
}
