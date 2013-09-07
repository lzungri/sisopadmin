package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.Predicate;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.EvaluacionFasePorPlantilla;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Fase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Tp;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.estados.Estado;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Coordinador;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel;
import ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.AdministrarIntegranteUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.AdministrarIntegranteUseCaseModel;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCaseModel;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.base.AdminBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.SelectionMode;

/**
 * Modelo del CU de administración de evaluaciones de fase.
 *
 * @author Leandro
 */
public class AdministrarEvaluacionesFaseUseCaseModel extends AdminBaseUseCaseModel {
	private EvaluacionFasePorPlantilla evaluacionToFind;
	private List<Estado> estados;
	private Set<Fase> fases;

	protected Class<? extends ABMBaseUseCase> abmEntityUseCase() {
		return ABMEvaluacionFasePorPlantillaUseCase.class;
	}

	protected Class<? extends ModelObject> entityClass() {
		return EvaluacionFasePorPlantilla.class;
	}

	protected boolean prePopulateUseCase() {
		return true;
	}
	
	protected Predicate searchFilter(final Usuario usuario) {
		if(usuario != null) {
			// Si el usuario sólo es ayudante, entonces se filtran sus grupos.
			if(usuario instanceof Ayudante && !(usuario instanceof Coordinador)) {
				return new Predicate() {
					public boolean evaluate(Object object) {
						return ((Ayudante) usuario).getGrupos().contains(((EvaluacionFasePorPlantilla) object).getGrupo());
					}
				};
			}
		}
		return null;
	}

	@Secure ({})
	@Description ("Iniciar el caso de uso")
	@Transactional
	public void initUseCase(UseCaseContext useCaseContext) {
		super.initUseCase(useCaseContext);
		
		evaluacionToFind = new EvaluacionFasePorPlantilla();
		evaluacionToFind.setAyudanteEvaluador(new Ayudante());
		evaluacionToFind.setFase(new Fase());
		evaluacionToFind.getFase().setTp(new Tp());
		evaluacionToFind.setGrupo(new Grupo());
		evaluacionToFind.setEstado(new Estado());

		estados = Estado.findAllByClassNamePropietaria(EvaluacionFasePorPlantilla.class);
		fases = CollectionFactory.createSet(Fase.class);
	}

	protected List<? extends ModelObject> doFind(UseCaseContext useCaseContext) {
		return EvaluacionFasePorPlantilla.findBy(
				evaluacionToFind.getEstado().getId(),
				evaluacionToFind.getFase().getTp().getId(),
				evaluacionToFind.getFase().getId(),
				evaluacionToFind.getAyudanteEvaluador().getId(),
				evaluacionToFind.getGrupo().getId());
	}
	
	public void seleccionarAyudante(UseCaseContext useCaseContext) {
		useCaseContext.goToChildUseCase(AdministrarIntegranteUseCase.class, new SelectionMode(), "returnFromSeleccionarAyudante");
	}
	
	public void returnFromSeleccionarAyudante(UseCaseContext useCaseContext) {
		AdministrarIntegranteUseCaseModel model = (AdministrarIntegranteUseCaseModel) useCaseContext.getReturnedModel();
		evaluacionToFind.setAyudanteEvaluador( model != null ? (Ayudante) model.getSelectedEntities().iterator().next() : new Ayudante());
	}
	
	public void seleccionarTP(UseCaseContext useCaseContext) {
		useCaseContext.goToChildUseCase(AdministrarTPsUseCase.class, new SelectionMode(), "returnFromSeleccionarTP");
	}
	
	public void returnFromSeleccionarTP(UseCaseContext useCaseContext) {
		AdministrarTPsUseCaseModel model = (AdministrarTPsUseCaseModel) useCaseContext.getReturnedModel();
		if(model != null) {
			Tp returnedTP = (Tp) model.getSelectedEntities().iterator().next();
			evaluacionToFind.getFase().setTp(returnedTP);
			fases = returnedTP.getFases();
		}
		else {
			evaluacionToFind.getFase().setTp(new Tp());
			fases = CollectionFactory.createSet(Fase.class);
		}
	}
	
	public void seleccionarGrupo(UseCaseContext useCaseContext) {
		useCaseContext.goToChildUseCase(AdministrarGruposUseCase.class, new SelectionMode(), "returnFromSeleccionarGrupo");
	}
	
	public void returnFromSeleccionarGrupo(UseCaseContext useCaseContext) {
		AdministrarGruposUseCaseModel model = (AdministrarGruposUseCaseModel) useCaseContext.getReturnedModel();
		evaluacionToFind.setGrupo( model != null ? (Grupo) model.getSelectedEntities().iterator().next() : new Grupo());
	}

	public EvaluacionFasePorPlantilla getEvaluacionToFind() {
		return evaluacionToFind;
	}

	public void setEvaluacionToFind(EvaluacionFasePorPlantilla evaluacionToFind) {
		this.evaluacionToFind = evaluacionToFind;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Set<Fase> getFases() {
		return fases;
	}

	public void setFases(Set<Fase> fases) {
		this.fases = fases;
	}

}