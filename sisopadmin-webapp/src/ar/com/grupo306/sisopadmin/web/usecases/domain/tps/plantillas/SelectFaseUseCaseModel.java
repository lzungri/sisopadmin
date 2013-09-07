package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas;

import java.util.Map;
import java.util.Set;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Fase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Tp;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCaseModel;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.BaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.SelectionMode;

public class SelectFaseUseCaseModel extends BaseUseCaseModel{
		
	private Tp tp = new Tp();
	private Set<Fase> fases = CollectionFactory.createSet(Fase.class);
	private String id_fase = "";
	
	@Override
	@Secure ({})
	@Description ("Acceder a la selección de fase de una plantilla de corrección")
	@Transactional
	public void initUseCase(UseCaseContext context) {
		// TODO Auto-generated method stub
		super.initUseCase(context);
	}

	public void seleccionarTP(UseCaseContext context){
		context.goToChildUseCase(AdministrarTPsUseCase.class, new SelectionMode(), "returnFromSeleccionTP");
	}
	
	@Transactional
	public void irCrearPlantilla(UseCaseContext context){
		if(id_fase != "")	{
			Map parameters = CollectionFactory.createMap();
			parameters.put("FASE_PLANTILLA", Fase.findMeById(Long.parseLong(id_fase)));
			context.goToChildUseCase(ABMPlantillaUseCase.class, parameters);
		}
		else
			context.addErrorMessage("Debe seleccionar un fase");
	}
	
	public void returnFromSeleccionTP(UseCaseContext context) {
		AdministrarTPsUseCaseModel model = ((AdministrarTPsUseCaseModel)context.getReturnedModel());
		if(model != null){
			this.setTp((Tp)model.getSelectedEntities().iterator().next());
			fases = tp.getFases();
		}
	}
	
	public Tp getTp() {
		return tp;
	}
	
	public void setTp(Tp tp) {
		this.tp = tp;
	}

	public Set<Fase> getFases() {
		return fases;
	}

	public void setFases(Set<Fase> fases) {
		this.fases = fases;
	}

	public String getId_fase() {
		return id_fase;
	}

	public void setId_fase(String fase) {
		this.id_fase = fase;
	}

	@Override
	public void returnFromChildDefaultMethod(UseCaseContext context) {
		super.returnFromChildDefaultMethod(context);
		
		if(context.getReturnedModel() != null) {
			ABMPlantillaUseCaseModel model =  ((ABMPlantillaUseCaseModel)context.getReturnedModel());
			if(model.getMode().equals(new CreateMode())){	
				this.setId_fase(model.getPlantilla().getFase().getId().toString());
				this.acceptUseCase(context);
			}		
		}
	}
	
}
