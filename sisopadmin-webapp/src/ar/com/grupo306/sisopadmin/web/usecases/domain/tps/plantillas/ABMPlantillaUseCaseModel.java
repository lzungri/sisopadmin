/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Fase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.ItemPlantilla;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.PlantillaCorreccion;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.RemoveMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.ViewMode;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseValidationUtils;

/**
 * @author Sole
 *
 */
public class ABMPlantillaUseCaseModel extends ABMBaseUseCaseModel {
	private PlantillaCorreccion plantilla = new PlantillaCorreccion();
	private String index;
	private Set<ItemPlantilla> itemsCreados = CollectionFactory.createSet(ItemPlantilla.class);
	private Set<ItemPlantilla> itemsEliminados = CollectionFactory.createSet(ItemPlantilla.class);
	
	
	@Secure ({})
	@Description ("Acceder a ABMPlantillas")
	@Transactional
	@Override
	public void initUseCase(UseCaseContext context) {		
		super.initUseCase(context);	
		if(getMode().equals(new CreateMode())){
			plantilla.setFase((Fase)context.getParameter("FASE_PLANTILLA"));
		}	
	}
	
	protected void processModelObject(UseCaseContext context, ModelObject modelObject) {		
		plantilla = ((PlantillaCorreccion) modelObject);	
		context.refreshUseCase();
	}	
	
	public void cargarItem(UseCaseContext context){
		Map parameters = CollectionFactory.createMap();				
		parameters.put("FASE_PLANTILLA", this.getPlantilla().getFase());		
		context.goToChildUseCase(ABMItemUseCase.class,new CreateMode(), parameters);
	}
	
	public void viewItem(UseCaseContext context){
		goToABMItemUseCase(context, new ViewMode());
	}
	
	public void editItem(UseCaseContext context){	
		goToABMItemUseCase(context, new EditMode());
	}
	
	public void removeItem(UseCaseContext context){
		goToABMItemUseCase(context, new RemoveMode());
	}
	
	private void goToABMItemUseCase(UseCaseContext useCaseContext, UseCaseModelMode mode) {
		Map parameters = CollectionFactory.createMap();		
		parameters.put(ABMBaseUseCaseModel.ENTITY_KEY, selectItem(useCaseContext));		
		parameters.put("FASE_PLANTILLA", this.getPlantilla().getFase());
		useCaseContext.goToChildUseCase(ABMItemUseCase.class, mode, parameters);		
	}

	private ItemPlantilla selectItem(UseCaseContext context){
		if(context.getRequestParameter("useCaseModel.index") == null) 
			return null;
		else{
			try{				
				return (ItemPlantilla) plantilla.getItems().toArray()[Integer.parseInt(index)];
			}
			catch(IndexOutOfBoundsException excep) {
				throw ExceptionFactory.createBusinessException("Ha intentado seleccionar un elemento inexistente.");
			}
		}
	}	
	
	@Description ("Efectuar la creación")
	@Transactional
	public void createAccept(UseCaseContext context) {
		if (! validacionOK(new UseCaseValidationUtils(context)))
			return;
		saveOrUpdate(context);
		
	}
	
	@Description ("Efectuar la edición")
	@Transactional
	public void editAccept(UseCaseContext context) {
		if (plantilla.isUsed())
		{
			context.addErrorMessage("La Plantilla " + plantilla.getNombre() +  " no puede modificarse porque tiene asociadas evaluaciones");
			context.acceptUseCase();
		}	
		else
		{		
			if (! validacionOK(new UseCaseValidationUtils(context)))
				return;
			saveOrUpdate(context);
		}				
	}
	
	@Description ("Efectuar la eliminación")
	@Transactional
	public void removeAccept(UseCaseContext context) {
		if (plantilla.isUsed())
		{
			context.addErrorMessage("La Plantilla " + plantilla.getNombre() + " no puede eliminarse porque tiene asociadas evaluaciones");
		}	
		else
		{
			plantilla.remove();		
			context.addMessage("Plantilla eliminada exitosamente.");
		}
		context.acceptUseCase();
	
	}
	
	private boolean validacionOK(UseCaseValidationUtils validationUtils){
		Boolean repetido = false;
		validationUtils.assertNotEmpty(plantilla.getNombre(), "Debe ingresar un nombre para la plantilla");
		validationUtils.assertNotNull(plantilla.getPeso(), "Debe ingresar un peso válido para la plantilla");
		if(plantilla.getPeso() != null){
			validationUtils.assertTrue(plantilla.getPeso() > 0 && plantilla.getPeso() < 1000, "El peso de la plantilla debe ser mayor que cero y menor a 1000");	
		}
		validationUtils.assertTrue( plantilla.getItems().size() > 0, "Debe ingresar los ítems de la plantilla");
		
		//Debe haber al menos un ítem obligatorio
		Boolean itemObligatorio = false;
		
		Iterator it = plantilla.getItems().iterator(); it.hasNext();
		
		while(it.hasNext() && !itemObligatorio){
			itemObligatorio = ((ItemPlantilla)it.next()).getObligatorio();
		}
		validationUtils.assertTrue(itemObligatorio, "La plantilla debe tener al menos un item obligatorio");
			
		
		return !validationUtils.hasErrors();
	}
	
	private void saveOrUpdate(UseCaseContext context){
		
		for(Iterator it = plantilla.getItems().iterator(); it.hasNext() ;){ 

			ItemPlantilla item = (ItemPlantilla)it.next();
			item.saveOrUpdate();
		}
		plantilla.saveOrUpdate();		
						
		//Borro los items que no apuntan a ninguna plantilla
		for(Iterator it = this.itemsEliminados.iterator(); it.hasNext();){
			ItemPlantilla itemEliminable = (ItemPlantilla)it.next(); 
			if( ! itemsCreados.contains(itemEliminable)) // Solo hago Remove si ya está creada en la BD
				itemEliminable.remove();
		}
					
		context.addMessage("Operación exitosa. Los items de la plantilla suman en total un peso de " + calcularSumaDeItems());		
		context.acceptUseCase();
	}
	
	@Override
	public void returnFromChildDefaultMethod(UseCaseContext context) {
		super.returnFromChildDefaultMethod(context);
		
//		Setea la parte recién agregada/modificada a la colección
		if(context.getReturnedModel() != null)   { //Por si fue un Cancel
			ItemPlantilla item = ((ABMItemUseCaseModel)context.getReturnedModel()).getItem();		 
			
			if(context.getReturnedModel().getMode().equals(new CreateMode())){				
				plantilla.getItems().add(item);								
				this.itemsCreados.add(item);
			}
			if(context.getReturnedModel().getMode().equals(new EditMode())){
				//Busco la consigna, y la reemplazo por la nueva
				Boolean encontroItem = false;
				Iterator it = plantilla.getItems().iterator();
				while(it.hasNext() && !encontroItem ){
					ItemPlantilla itemAux = (ItemPlantilla)it.next();
					if(itemAux.getId() == item.getId()){
						plantilla.getItems().remove(itemAux);
						plantilla.getItems().add(item);
						encontroItem = true;						
					}	
				}				
			}			
			if(context.getReturnedModel().getMode().equals(new RemoveMode())){
				plantilla.getItems().remove(item);
				this.itemsEliminados.add(item);
			}
		}
	}	
	
	private Integer calcularSumaDeItems(){
		Integer sumatoria = 0;
		for(Iterator it = plantilla.getItems().iterator(); it.hasNext();){
			ItemPlantilla item = (ItemPlantilla)it.next();
			sumatoria += item.getPeso();
		}
		return sumatoria;
	}
	
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public PlantillaCorreccion getPlantilla() {
		return plantilla;
	}
	public void setPlantilla(PlantillaCorreccion plantilla) {
		this.plantilla = plantilla;
	}
}
