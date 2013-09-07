package ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.estrategiasABM;

import java.util.Date;

import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.ABMEncuestaUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.UseCaseModel;

/**
 * 
 * @author Rafa
 *
 */
public class EstrategiaAceptacionABM {
	
	/**
	 * Metodo polimórfico sobreescrito en las distintas estrategias.
	 * @param context
	 */
	public void ejecutar(ABMContext context){};
	
	public boolean initializeEdicionPunto(){
		return true;
	}
	
	public Boolean validarAbmEncuesta(UseCaseContext context,UseCaseModel model){
		ABMEncuestaUseCaseModel useCaseModel = (ABMEncuestaUseCaseModel)model;
		Boolean valida = true;
		if("".compareToIgnoreCase(useCaseModel.getNombrePlantilla()) == 0){
			context.addErrorMessage("Debe ingresar el nombre de la plantilla");
			valida = false;
		}
		
		if(new PlantillaEncuesta().findMeByName(useCaseModel.getNombrePlantilla())!= null){
			context.addErrorMessage("El nombre de la plantilla ya está registrado. Por favor cambielo");
			valida = false;
		}
			
		if(useCaseModel.getPuntos().isEmpty()){
			context.addErrorMessage("Debe ingresar puntos en la encuesta");
			valida = false;
		}
		if(useCaseModel.getFechaInicio()==null){
			context.addErrorMessage("Debe ingresar la fecha de inicio");
			valida = false;
		}
		if(useCaseModel.getFechaFin() == null){
			context.addErrorMessage("Debe ingresar la fecha de finalización");
			valida = false;
		}
		if(useCaseModel.getFechaInicio() != null && useCaseModel.getFechaFin() != null && useCaseModel.getFechaInicio().compareTo(useCaseModel.getFechaFin())>0){
			context.addErrorMessage("La fecha de finalización debe ser menor a la de origen");
			valida = false;
		}
		
		if(useCaseModel.getFechaFin() != null && useCaseModel.getFechaInicio() != null && !DateUtils.isGreaterThanRange(useCaseModel.getFechaFin(), new Date())){
			context.addErrorMessage("La fecha de finalización debe ser mayor a la actual");
			valida = false;
		}
		
		if(useCaseModel.getFechaInicio() != null && useCaseModel.getFechaFin() != null &&!DateUtils.isGreaterThanRange( useCaseModel.getFechaInicio(),new Date()) && !DateUtils.isSameDayMonthYear(useCaseModel.getFechaInicio(), new Date())){
			context.addErrorMessage("La fecha de inicio no puede ser menor que la actual");
			valida = false;
		}

		return valida;
	}
	
	
}
