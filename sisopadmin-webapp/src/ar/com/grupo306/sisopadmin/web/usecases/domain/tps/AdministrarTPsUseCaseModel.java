/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.tps;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.config.Module;
import ar.com.grupo306.sisopadmin.config.SisopAdminConfig;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Entrega;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.EvaluacionFasePorPlantilla;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Fase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Tp;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.base.AdminBaseUseCaseModel;

/**
 * @author Sole
 *
 */
public class AdministrarTPsUseCaseModel extends AdminBaseUseCaseModel {
	private String nombreTp = "";
		
	@Override
	protected boolean prePopulateUseCase() {		
		return true;
	}	
	
	@Override
	protected Class<? extends ModelObject> entityClass() {
		return Tp.class;
	}

	@Override
	protected Class<? extends ABMBaseUseCase> abmEntityUseCase() {		
		return ABMTpUseCase.class;
	}
	
	@Secure ({})
	@Description ("Acceder a administrarTps")
	@Transactional
	@Override	
	public void initUseCase(UseCaseContext useCaseContext) {		
		super.initUseCase(useCaseContext);
	}
	
	@Override
	@Secure({})
	@Description ("Editar TPs")
	public void edit(UseCaseContext useCaseContext) {
		super.edit(useCaseContext);
	}

	@Override
	@Secure({})
	@Description ("Eliminar TPs")
	@Transactional
	public void remove(UseCaseContext useCaseContext) {
		Tp tp = (Tp) getSelectedEntity(useCaseContext);
		Boolean tieneEvaluaciones = false;
		Boolean tieneEntregas = false;
		Iterator it = tp.getFases().iterator();
		
		
		while (it.hasNext() && !tieneEvaluaciones){
			Fase fase = (Fase)it.next();
			List<EvaluacionFasePorPlantilla> lista = EvaluacionFasePorPlantilla.findBy(null, null, fase.getId(), null, null); 
			
			tieneEvaluaciones = lista != null && lista.size() > 0 ;
		}
		
		it = tp.getFases().iterator();
		while (it.hasNext() && !tieneEntregas){
			Fase fase = (Fase)it.next();
			List<Entrega> lista = Entrega.findBy(null, fase.getId()); 
			tieneEntregas = lista != null && lista.size() > 0 ;
		}		
		
		if(tieneEvaluaciones){
			useCaseContext.addErrorMessage("No puede eliminarse el Trabajo Práctico " + tp.getNombre() + " porque tiene asociada evaluaciones.");
		}
		else{
			if(tieneEntregas){
				useCaseContext.addErrorMessage("No puede eliminarse el Trabajo Práctico " + tp.getNombre() + " porque tiene entregas asociada.");
			}
			else{
				super.remove(useCaseContext);
			}			
		}
	}

	@Override
	@Secure({})
	@Description ("Visualizar TPs")
	public void view(UseCaseContext useCaseContext) {
		super.view(useCaseContext);
	}
	
	public void verInformacion(UseCaseContext context){
		Map parametros = CollectionFactory.createMap();
		parametros.put(ABMBaseUseCaseModel.ENTITY_KEY, this.findEntitys(context).get(0));		
		context.goToChildUseCase(VerInformacionTPUseCase.class, parametros);
	}
	
	@Override
	protected List<? extends ModelObject> doFind(UseCaseContext useCaseContext) {
		 this.setEntities(Tp.findUs(nombreTp));
		 return this.getEntities(); 
	}
	
	public void cargarTp(UseCaseContext context){
		context.goToChildUseCase(ABMTpUseCase.class);
	}
	
	@Override
	@Transactional
	public void returnFromChildDefaultMethod(UseCaseContext context) {		
		doFind(context);
	}	
	
	public void descargarArchivoTp(UseCaseContext context) {
		
		// Encontrar el TP seleccionado		
		List<? extends ModelObject> selectedEntities = this.findEntitys(context);
		Tp tp = (Tp)selectedEntities.iterator().next();

		String uploadPath = SisopAdminConfig.getInstance().getProperty(Module.TP, "abmTp.uploadFiles.path");
		context.addFileAttachment(uploadPath + tp.getArchivoEspecificacion());
	}
	
	
	//SETTERS AND GETTERS
	
	public String getNombreTp() {
		return nombreTp;
	}

	public void setNombreTp(String nombreTp) {
		this.nombreTp = nombreTp;
	}

}
