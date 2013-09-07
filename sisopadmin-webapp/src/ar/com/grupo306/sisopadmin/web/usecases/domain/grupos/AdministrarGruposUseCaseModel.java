/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.grupos;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.Predicate;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Coordinador;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.base.AdminBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.ConfirmMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;

/**
 * @author Sole
 *
 */
public class AdministrarGruposUseCaseModel extends AdminBaseUseCaseModel {
	
	@Override
	@Transactional
	public void returnFromChildDefaultMethod(UseCaseContext context) {
		find(context);
	}

	private String nombreGrupo;	
	
	@Override
	protected boolean prePopulateUseCase() {
		return true;
	}

	protected Class<? extends ABMBaseUseCase> abmEntityUseCase() {		
		return ABMGruposUseCase.class;
	}
	
	protected Predicate searchFilter(final Usuario usuario) {
		if(usuario != null) {
			// Si el usuario sólo es ayudante, entonces se filtran sus grupos.
			if(usuario instanceof Ayudante && !(usuario instanceof Coordinador)) {
				return new Predicate() {
					public boolean evaluate(Object object) {
						return ((Ayudante) usuario).getGrupos().contains(object);
					}
				};
			}
			if(usuario instanceof Grupo){
				return new Predicate() {
					public boolean evaluate(Object object) {
						return (object.equals(usuario));
					}
				};
			}
			
		}
		return null;
	}
	
	
	@Secure({})
	@Description ("Acceder a Administrar Grupos")
	@Override
	@Transactional
	public void initUseCase(UseCaseContext useCaseContext) {		
		super.initUseCase(useCaseContext);
		
		Usuario usuario = Usuario.findMeByLoginName(((LoggedUserContext) useCaseContext.getUserContext()).getUserLoginName());
		if(usuario instanceof Grupo) {
			nombreGrupo = ((Grupo) usuario).getNombre();
		}
	}	

	@Override
	@Secure({})
	@Description ("Editar Grupos")
	public void edit(UseCaseContext useCaseContext) {
		super.edit(useCaseContext);
	}

	@Override
	@Secure({})
	@Description ("Eliminar Grupos")
	public void remove(UseCaseContext useCaseContext) {
		super.remove(useCaseContext);
	}

	@Override
	@Secure({})
	@Description ("Visualizar Grupos")
	public void view(UseCaseContext useCaseContext) {
		super.view(useCaseContext);
	}
	
	@Override
	@Secure({})
	@Description ("Buscar Grupos")
	@Transactional
	public void find(UseCaseContext useCaseContext) {
		// TODO Auto-generated method stub
		super.find(useCaseContext);
	}
	
	@Override
	@Transactional
	protected List<? extends ModelObject> doFind(UseCaseContext useCaseContext) {
		return (List<? extends ModelObject>) Grupo.findByName(nombreGrupo);
	}
	@Override
	protected Class<? extends ModelObject> entityClass() {		
		return Grupo.class;
	}
	
	@Secure({})
	@Description ("Confirmar Grupos")
	public void confirm(UseCaseContext context){
		Map parameters = CollectionFactory.createMap();		
		parameters.put(ABMBaseUseCaseModel.ENTITY_KEY, selectGrupo(context));			
		context.goToChildUseCase(ABMGruposUseCase.class, new ConfirmMode(), parameters, "returnFromConfirm");
	}
	
	@Transactional
	public void returnFromConfirm (UseCaseContext context){
		find(context);
	}
	
	private Grupo selectGrupo(UseCaseContext context){
		if(context.getRequestParameter("useCaseModel.index") == null) 
			return null;
		else{
			try{				
				return (Grupo) getEntities().toArray()[Integer.parseInt(this.getIndex()[0])];
			}
			catch(IndexOutOfBoundsException excep) {
				throw ExceptionFactory.createBusinessException("Ha intentado seleccionar un elemento inexistente.");
			}
		}
	}	
	
	public String getNombreGrupo() {
		return nombreGrupo;
	}
	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}
	
	public void cargarGrupo(UseCaseContext context){
		context.goToChildUseCase(ABMGruposUseCase.class, new CreateMode());
	}
}
