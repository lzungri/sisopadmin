package ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Coordinador;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.web.usecases.domain.roles.ABMRolesUsuarioUseCase;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.base.AdminBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;

public class AdministrarIntegranteUseCaseModel extends AdminBaseUseCaseModel {
	private String nombre = "";
	private String apellido = "";
	private String tipoIntegrante= "";		
	
	protected Class<? extends ModelObject> entityClass() {
		return Ayudante.class;
	}	

	protected Class<? extends ABMBaseUseCase> abmEntityUseCase() {
		return ABMIntegranteUseCase.class;
	}
	
	protected boolean prePopulateUseCase() {
		return true;
	}

	@Secure ({})
	@Description ("Acceder a administrarIntegrante")
	@Transactional
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);		
	}
	
	@Override
	protected List<? extends ModelObject> doFind(UseCaseContext useCaseContext) {
		Class tipo = null;
		 if (tipoIntegrante.compareTo("Coordinador")== 0)
			 tipo = Coordinador.class;
		 if (tipoIntegrante.compareTo("Ayudante")== 0)
			 tipo = Ayudante.class;		 
		 // Con la opción (Todos) quedá el tipo = null
		 
		 this.setEntities(Ayudante.findUs(nombre, apellido,tipo));
		 return this.getEntities();
	}
	
	@Override
	@Transactional
	public void remove(UseCaseContext useCaseContext) {
		Ayudante ayudante = (Ayudante) getSelectedEntity(useCaseContext);
		if(ayudante.getGrupos().size() > 0){
			useCaseContext.addErrorMessage("El integrante " + ayudante.getNombre() + " " + ayudante.getApellido() +  " no puede eliminarse porque tiene grupos asignados");
		}
		else{			
			super.remove(useCaseContext);
		}
	}

	@Override
	@Transactional
	public void returnFromChildDefaultMethod(UseCaseContext context) {
		this.setEntities(new Ayudante().findAll(Ayudante.class));
		this.nombre = "";
		this.apellido = "";		
		this.tipoIntegrante = "Todos";
		super.returnFromChildDefaultMethod(context);
	}	
	
	@Transactional
	public void roles(UseCaseContext useCaseContext) {
		Map parametros = new HashMap();
		Usuario usuarioById = (Usuario) this.getEntities().get(Integer.valueOf(this.getIndex()[0]).intValue());
		if(usuarioById!=null){
			parametros.put("usuarioById",usuarioById.getId());
		}
		useCaseContext.goToChildUseCase(ABMRolesUsuarioUseCase.class, new EditMode(), parametros);
	}
	
	public void asignarAyudantes(UseCaseContext context) {		
		Map parametros = CollectionFactory.createMap();
		parametros.put(ABMBaseUseCaseModel.ENTITY_KEY, this.findEntitys(context).get(0));		
		context.goToChildUseCase(AsignarAyudantesACoordinadorUseCase.class, parametros);
	}
	
	public void asignarGrupos(UseCaseContext context) {		
		Map parametros = CollectionFactory.createMap();
		parametros.put("ayudante", this.findEntitys(context).get(0));		
		context.goToChildUseCase(AsignarGruposAIntegranteUseCase.class, parametros);
	}

	public void cargarIntegrante(UseCaseContext context){
		context.goToChildUseCase(ABMIntegranteUseCase.class);
	}	
	
	public void refrescar(UseCaseContext context){
		context.refreshUseCase();
	}
	
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipoIntegrante() {
		return tipoIntegrante;
	}
	public void setTipoIntegrante(String tipoIntegrante) {
		this.tipoIntegrante = tipoIntegrante;
	}
	
}
 