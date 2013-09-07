/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.tps;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
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
import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.ViewMode;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseValidationUtils;

/**
 * @author Sole
 *
 */
public class ABMFaseUseCaseModel extends ABMBaseUseCaseModel {
	private Fase fase = new Fase();
	private String index;
	private Set<Consigna> consignasCreadas = CollectionFactory.createSet(Consigna.class);
	private Set<Consigna> consignasEliminadas = CollectionFactory.createSet(Consigna.class);
	
	
	@Secure ({})
	@Description ("Acceder a ABMFase")
	@Transactional
	@Override
	public void initUseCase(UseCaseContext context) {		
		super.initUseCase(context);		
	}
	
	protected void processModelObject(UseCaseContext context, ModelObject modelObject) {		
		fase = ((Fase) modelObject);	
		fase.getConsignas();
		context.refreshUseCase();
	}	

	public void cargarConsigna(UseCaseContext context){
		context.goToChildUseCase(ABMConsignaUseCase.class);
	}
	
	public void viewConsigna(UseCaseContext context){
		goToABMConsignaUseCase(context, new ViewMode());
	}
	
	@Transactional
	public void editConsigna(UseCaseContext context){
		
		// Si la consigna tienen items de evaluación asociadas no se puede modificar
		Consigna consigna = selectConsigna(context);
		if(ItemPlantilla.findByConsigna(consigna.getId()).size() > 0)
			context.addErrorMessage("No se puede modificar la consigna porque tiene items de plantilla de corrección asociados");
		else
		goToABMConsignaUseCase(context, new EditMode());
	}
	
	@Transactional
	public void removeConsigna(UseCaseContext context){
		// Si la consigna tienen items de evaluación asociadas no se puede modificar
		Consigna consigna = selectConsigna(context);
		if(ItemPlantilla.findByConsigna(consigna.getId()).size() > 0)
			context.addErrorMessage("No se puede eliminar la consigna porque tiene items de plantilla de corrección asociados");
		else
			goToABMConsignaUseCase(context, new RemoveMode());
	}
	
	private void goToABMConsignaUseCase(UseCaseContext useCaseContext, UseCaseModelMode mode) {
		Map parameters = CollectionFactory.createMap();		
		parameters.put(ABMBaseUseCaseModel.ENTITY_KEY, selectConsigna(useCaseContext));			
		useCaseContext.goToChildUseCase(ABMConsignaUseCase.class, mode, parameters);		
	}

	private Consigna selectConsigna(UseCaseContext context){
		if(context.getRequestParameter("useCaseModel.index") == null) 
			return null;
		else{
			try{				
				return (Consigna) fase.getConsignas().toArray()[Integer.parseInt(index)];
			}
			catch(IndexOutOfBoundsException excep) {
				throw ExceptionFactory.createBusinessException("Ha intentado seleccionar un elemento inexistente.");
			}
		}
	}	
	
	@Secure ({CreateMode.class, EditMode.class, ViewMode.class, RemoveMode.class})
	@Description ("Efectuar la creación de Fases")
	public void createAccept(UseCaseContext context){
		if(validacionOK(new UseCaseValidationUtils(context)))
			context.acceptUseCase();
	}
	
	@Secure ({CreateMode.class, EditMode.class, ViewMode.class, RemoveMode.class})
	@Description ("Efectuar la edición de Fases")
	public void editAccept(UseCaseContext context){
		if(validacionOK(new UseCaseValidationUtils(context)))
			context.acceptUseCase();
	}
	
	@Secure ({CreateMode.class, EditMode.class, ViewMode.class, RemoveMode.class})
	@Description ("Efectuar la eliminación de Fases")
	public void removeAccept(UseCaseContext context){		
		context.acceptUseCase();
	}

	private boolean validacionOK(UseCaseValidationUtils validationUtils){
		Boolean repetido = false;
		validationUtils.assertNotNull(fase.getNumero(), "Debe ingresar un número de fase válido.");
		validationUtils.assertNotNegativeNumber(fase.getNumero(), "Debe ingresar un número de fase positivo o cero");
		validationUtils.assertNotEmpty(getFase().getNombre(), "Debe ingresar el nombre de la fase");
		validationUtils.assertNotNull(fase.getFechaInicio(), "Debe ingresar una fecha de inicio");
		validationUtils.assertNotNull(fase.getFechaFin(), "Debe ingresar una fecha de finalización");
		if(fase.getFechaInicio() != null && fase.getFechaFin() != null)
			validationUtils.assertFechaDesdeHasta(getFase().getFechaInicio(), getFase().getFechaFin(), "La fecha de inicio debe ser menor que la fecha de finalización");		
		
		//Validar que no se repita el número de consigna
		Object[] consignasArray = fase.getConsignas().toArray();  
		for(int i = 0; i < (consignasArray.length - 1); i++ ){
			for(int j = i+1; j < consignasArray.length; j++ ){
				if ( ((Consigna)consignasArray[i]).getNumero().equals(((Consigna)consignasArray[j]).getNumero())){
					repetido = true;
				}					
			}
		}
		validationUtils.assertFalse(repetido, "Los números de consigna no pueden estar repetidos");		
		
		return !validationUtils.hasErrors();
	}
	
	@Override
	public void returnFromChildDefaultMethod(UseCaseContext context) {
		
		//Setea la parte recién agregada/modificada a la colección
		if(context.getReturnedModel() != null)   { //Por si fue un Cancel
			Consigna consigna = ((ABMConsignaUseCaseModel)context.getReturnedModel()).getConsigna();			 
			
			if(context.getReturnedModel().getMode().equals(new CreateMode())){				
				fase.getConsignas().add(consigna);				
				this.consignasCreadas.add(consigna);
			}
			if(context.getReturnedModel().getMode().equals(new EditMode())){
				//Busco la consigna, y la reemplazo por la nueva
				Boolean encontroConsigna = false;
				Iterator it = fase.getConsignas().iterator();
				while(it.hasNext() && !encontroConsigna ){
					Consigna consignaAux = (Consigna)it.next();
					if(consignaAux.getId() == consigna.getId()){
						fase.getConsignas().remove(consignaAux);
						fase.getConsignas().add(consigna);
						encontroConsigna = true;
					}	
				}				
			}			
			if(context.getReturnedModel().getMode().equals(new RemoveMode())){				
				fase.getConsignas().remove(consigna);	
				this.consignasEliminadas.add(consigna);
			}
		}
		super.returnFromChildDefaultMethod(context);
	}	

	public Set<Consigna> getConsignasCreadas() {
		return consignasCreadas;
	}	
	public void setConsignasCreadas(Set<Consigna> consignasCreadas) {
		this.consignasCreadas = consignasCreadas;
	}
	public Set<Consigna> getConsignasEliminadas() {
		return consignasEliminadas;
	}
	public void setConsignasEliminadas(Set<Consigna> consignasEliminadas) {
		this.consignasEliminadas = consignasEliminadas;
	}
	public String getIndex() {
		return index;
	}		
	public Fase getFase() {
		return fase;
	}
	public void setFase(Fase fase) {
		this.fase = fase;
	}
	public void setIndex(String index) {
		this.index = index;
	}	
}
