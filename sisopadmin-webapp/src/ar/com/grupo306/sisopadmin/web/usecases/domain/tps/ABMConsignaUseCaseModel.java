/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.tps;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Consigna;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.RemoveMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.ViewMode;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseValidationUtils;

/**
 * @author Sole
 *
 */
public class ABMConsignaUseCaseModel extends ABMBaseUseCaseModel {
	Consigna consigna = new Consigna();	
	
	@Secure ({})
	@Description ("Acceder a ABMConsigna")
	@Transactional
	@Override
	public void initUseCase(UseCaseContext context) {		
		super.initUseCase(context);
	}
	
	protected void processModelObject(UseCaseContext context, ModelObject modelObject) {			
		this.setConsigna((Consigna) modelObject);		
	}	
	
	@Secure ({CreateMode.class, EditMode.class, ViewMode.class, RemoveMode.class})
	@Description ("Efectuar la creación de Consignas")
	public void createAccept(UseCaseContext context){
		if(validacionOK(new UseCaseValidationUtils(context)))
			context.acceptUseCase();
	}
	
	@Secure ({CreateMode.class, EditMode.class, ViewMode.class, RemoveMode.class})
	@Description ("Efectuar la edición de Consignas")
	public void editAccept(UseCaseContext context){
		if(validacionOK(new UseCaseValidationUtils(context)))
			context.acceptUseCase();
	}
	
	@Secure ({CreateMode.class, EditMode.class, ViewMode.class, RemoveMode.class})
	@Description ("Efectuar la eliminación de Consignas")
	public void removeAccept(UseCaseContext context){		
		context.acceptUseCase();
	}
	
	private boolean validacionOK(UseCaseValidationUtils validationUtils){
		validationUtils.assertNotNull(getConsigna().getNumero(), "Debe ingresar un número de consigna válido");
		validationUtils.assertNotNegativeNumber(getConsigna().getNumero(), "Debe ingresar un número de consigna positivo o cero");
		validationUtils.assertNotEmpty(getConsigna().getDescripcion(), "Debe ingresar una descripción");
		validationUtils.assertValidTextArea(getConsigna().getDescripcion(), "Debe ingresar una descripción válida");
		
		return !validationUtils.hasErrors();
	}	
	
	public Consigna getConsigna() {
		return consigna;
	}
	public void setConsigna(Consigna consigna) {
		this.consigna = consigna;
	}
}
