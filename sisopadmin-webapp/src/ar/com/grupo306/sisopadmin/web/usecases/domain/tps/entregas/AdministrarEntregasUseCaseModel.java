package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas;

import java.io.File;
import java.util.List;

import org.apache.commons.collections.Predicate;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Entrega;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Coordinador;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
import ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.base.AdminBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.SelectionMode;

/**
 * Modelo para el CU de gestión de entregas de grupo.
 *
 * @author Leandro
 */
public class AdministrarEntregasUseCaseModel extends AdminBaseUseCaseModel {
	private Entrega entrega;

	protected Class<? extends ABMBaseUseCase> abmEntityUseCase() {
		return NotificarEntregaUseCase.class;
	}
	
	protected boolean prePopulateUseCase() {
		return true;
	}

	protected List<? extends ModelObject> doFind(UseCaseContext useCaseContext) {
		return Entrega.findBy(entrega.getGrupo().getId(), null);
	}
	
	protected Predicate searchFilter(final Usuario loggedUser) {
		if(loggedUser != null) {
			// Si el usuario sólo es ayudante, entonces se filtran sus grupos.
			if(loggedUser instanceof Ayudante && !(loggedUser instanceof Coordinador)) {
				return new Predicate() {
					public boolean evaluate(Object object) {
						return ((Ayudante) loggedUser).getGrupos().contains(((Entrega) object).getGrupo());
					}
				};
			}
			
			// Si el usuario es un grupo, sólo se muestran sus entregas.
			if(loggedUser instanceof Grupo) {
				return new Predicate() {
					public boolean evaluate(Object object) {
						return ((Entrega) object).getGrupo().equals(loggedUser);
					}
				};
			}
		}
		return null;
	}

	protected Class<? extends ModelObject> entityClass() {
		return Entrega.class;
	}
	
	@Secure ({})
	@Description ("Inicio de caso de uso")
	@Transactional
	public void initUseCase(UseCaseContext useCaseContext) {
		super.initUseCase(useCaseContext);
		
		entrega = new Entrega();
		
		Usuario usuario = Usuario.findMeByLoginName(((LoggedUserContext) useCaseContext.getUserContext()).getUserLoginName());
		if(usuario instanceof Grupo) {
			entrega.setGrupo((Grupo) usuario);
		}
		else {
			entrega.setGrupo(new Grupo());
		}
	}
	
	public void descargarEntrega(UseCaseContext context) {
		Entrega entregaADescargar = (Entrega) getSelectedEntity(context);
		if(entregaADescargar.getPathArchivo() == null || entregaADescargar.getPathArchivo().trim().equals("")) {
			return;
		}
		
		File entrega = new File(entregaADescargar.getPathArchivo());
		context.addFileAttachment(entrega.getAbsolutePath(), entrega.getName());
	}
	
	@Transactional
	public void returnFromChildDefaultMethod(UseCaseContext context) {
		super.returnFromChildDefaultMethod(context);
		find(context);
	}

	@Secure ({})
	@Description ("Selección de grupo")
	public void seleccionarGrupo(UseCaseContext useCaseContext) {
		useCaseContext.goToChildUseCase(AdministrarGruposUseCase.class, new SelectionMode(), "returnFromSeleccionarGrupo");
	}
	
	public void returnFromSeleccionarGrupo(UseCaseContext useCaseContext) {
		AdministrarGruposUseCaseModel model = (AdministrarGruposUseCaseModel) useCaseContext.getReturnedModel();
		entrega.setGrupo(model != null ? (Grupo) model.getSelectedEntities().iterator().next() : new Grupo());
	}

	public Entrega getEntrega() {
		return entrega;
	}

	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}

}