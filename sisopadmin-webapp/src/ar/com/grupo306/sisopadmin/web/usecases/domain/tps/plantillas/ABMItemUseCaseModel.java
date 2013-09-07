/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas;

import java.util.Map;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Consigna;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Fase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.ItemPlantilla;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.RemoveMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.SelectionMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.ViewMode;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseValidationUtils;

/**
 * @author Sole
 *
 */
public class ABMItemUseCaseModel extends ABMBaseUseCaseModel {
	private ItemPlantilla item = new ItemPlantilla();	
	private Fase fase;

	@Secure ({})
	@Description ("Acceder a ABMItemPlantillaCorreccion")
	@Transactional
	@Override
	public void initUseCase(UseCaseContext context) {		
		super.initUseCase(context);
		this.setFase((Fase) context.getParameter("FASE_PLANTILLA"));		
	}
	
	@Transactional
	protected void processModelObject(UseCaseContext context, ModelObject modelObject) {			
		this.setItem((ItemPlantilla) modelObject);		
		if(this.getItem().getConsigna() != null){
			this.setFase((Fase) context.getParameter("FASE_PLANTILLA"));			
		}			
	}
	
	@Secure ({CreateMode.class, EditMode.class, ViewMode.class, RemoveMode.class})
	@Description ("Efectuar la creación de Items")
	public void createAccept(UseCaseContext context){		
		if(validacionOK(new UseCaseValidationUtils(context)))
			context.acceptUseCase();
	}
	
	@Secure ({CreateMode.class, EditMode.class, ViewMode.class, RemoveMode.class})
	@Description ("Efectuar la edición de Items")
	public void editAccept(UseCaseContext context){		
		if(validacionOK(new UseCaseValidationUtils(context)))
			context.acceptUseCase();
	}
	
	@Secure ({CreateMode.class, EditMode.class, ViewMode.class, RemoveMode.class})
	@Description ("Efectuar la eliminación de Items")
	public void removeAccept(UseCaseContext context){		
		context.acceptUseCase();
	}
	
	public void seleccionarConsigna(UseCaseContext context){
		Map parameters = CollectionFactory.createMap();				
		parameters.put("FASE", this.getFase());		
		context.goToChildUseCase(AdministrarConsignasUseCase.class, new SelectionMode(), parameters, "returnFromSelectConsigna");
	}
	
	public void returnFromSelectConsigna (UseCaseContext context){
		if(context.getReturnedModel() != null){
			AdministrarConsignasUseCaseModel model = (AdministrarConsignasUseCaseModel)context.getReturnedModel();
			item.setConsigna((Consigna)model.getSelectedEntities().iterator().next());
			context.refreshUseCase();
		}
		else // si presiona cancelar se desselecciona.
			item.setConsigna(null);
	}
	
	private boolean validacionOK(UseCaseValidationUtils validationUtils){		
		validationUtils.assertNotNull(item.getPeso(), "Debe ingresar un peso válido para el ítem");
		if(item.getPeso() != null)
			validationUtils.assertTrue(item.getPeso() > 0 && item.getPeso() < 1000, "El peso de un ítem debe ser mayor que cero y menor a 1000");
		validationUtils.assertNotEmpty(item.getProcedimiento(), "Debe ingresar un procedimiento para el ítem");
		validationUtils.assertNotEmpty(item.getResultadoEsperado(), "Debe ingresar un resultado esperado para el ítem");
		validationUtils.assertNotEmpty(item.getObservacionBajaCalificacion(), "Debe ingresar una observación de baja calificación para el ítem");
		
		return !validationUtils.hasErrors();
	}	

	public ItemPlantilla getItem() {
		return item;
	}
	public void setItem(ItemPlantilla item) {
		this.item = item;
	}

	public Fase getFase() {
		return fase;
	}

	public void setFase(Fase fase) {
		this.fase = fase;
	}
}
