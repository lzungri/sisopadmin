package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.EvaluacionFasePorPlantilla;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.ItemEvaluacion;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.estados.Estado;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseValidationUtils;

/**
 * Modelo del CU de ABM de evaluaciones de fase.
 *
 * @author Leandro
 */
public class ABMEvaluacionFasePorPlantillaUseCaseModel extends ABMBaseUseCaseModel {
	private EvaluacionFasePorPlantilla evaluacion;

	@Secure ({})
	@Description ("Inicio de caso de uso")
	@Transactional
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);
	}

	protected void processModelObject(UseCaseContext context, ModelObject modelObject) {
		evaluacion = (EvaluacionFasePorPlantilla) modelObject;
		
		if(getMode().equals(new CreateMode())) {
			evaluacion.crearItemsEvaluacionDesdePlantilla();
		}
	}
	
	/**
	 * Graba la evaluación.
	 * 
	 * @param context
	 */
	@Transactional
	public void aceptarEvaluacion(UseCaseContext context) {
		if(validacionCamposOK(new UseCaseValidationUtils(context), context)) {
			for(ItemEvaluacion itemEvaluacion : evaluacion.getItemsEvaluacion()) {
				String porcentajeCumplimiento = getPorcentajeCumplimientoOfItem(itemEvaluacion, context);
				String observaciones = getObservacionesOfItem(itemEvaluacion, context);
				new UseCaseValidationUtils().assertValidTextArea(observaciones, "Debe ingresar una observación válida.");
				
				itemEvaluacion.setObservaciones(observaciones);
				if(porcentajeCumplimiento == null || porcentajeCumplimiento.trim().equals("")) {
					itemEvaluacion.setPorcentajeCumplimiento(null);
					itemEvaluacion.setEstado(Estado.findMeByDomainCode(ItemEvaluacion.ESTADO_CODE_NOCORREGIDO));
				}
				else {
					itemEvaluacion.setPorcentajeCumplimiento(new Integer(porcentajeCumplimiento));
					itemEvaluacion.setEstado(Estado.findMeByDomainCode(ItemEvaluacion.ESTADO_CODE_CORREGIDO));
				}
			}
			evaluacion.actualizarEstado();
			
			evaluacion.saveOrUpdate();
			context.addMessage("Se ha grabado exitosamente la evaluación.");
			context.acceptUseCase();
		}
	}
	
	@Transactional
	public void eliminarEvaluacion(UseCaseContext context) {
		evaluacion.remove();
		context.addMessage("Se ha eliminado exitosamente la evaluación.");
		context.acceptUseCase();
	}
	
	private boolean validacionCamposOK(UseCaseValidationUtils validationUtils, UseCaseContext context) {
		for(ItemEvaluacion itemEvaluacion : evaluacion.getItemsEvaluacion()) {
			String porcentaje = getPorcentajeCumplimientoOfItem(itemEvaluacion, context);
			validationUtils.assertValidNulleablePorcentual(porcentaje, "Debe ingresar un porcentaje válido para el ítem de plantilla '" + itemEvaluacion.getItemPlantilla().getNombre() + "'");
		}
		
		return !validationUtils.hasErrors();
	}
	
	private String getPorcentajeCumplimientoOfItem(ItemEvaluacion itemEvaluacion, UseCaseContext context) {
		return context.getRequestParameter("porcentajeCumplimiento_" + itemEvaluacion.getItemPlantilla().getId());
	}
	
	private String getObservacionesOfItem(ItemEvaluacion itemEvaluacion, UseCaseContext context) {
		return context.getRequestParameter("observacionesParticulares_" + itemEvaluacion.getItemPlantilla().getId());
	}

	public EvaluacionFasePorPlantilla getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(EvaluacionFasePorPlantilla evaluacion) {
		this.evaluacion = evaluacion;
	}
	
}