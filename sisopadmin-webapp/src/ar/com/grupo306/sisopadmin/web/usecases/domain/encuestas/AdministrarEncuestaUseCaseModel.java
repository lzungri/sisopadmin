package ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas;


import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.sisopadmin.constants.SisopAdminConstants;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.BaseUseCaseModel;

public class AdministrarEncuestaUseCaseModel extends BaseUseCaseModel {
	
	// Búsqueda de la encuesta
	private String nombrePlantillaBusqueda = "";
	private String fechaInicioBusqueda = "";
	private String fechaFinBusqueda = "";
	private Collection encuestas = null;
	//****************
	private String index;
	
	
	

	@Secure ({})
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);

	}
	/**
	 * Busca las encuestas del sistema ubicadas en el rango indicado y con el nombre seleccionado.
	 * @param context
	 */
	@Transactional
	public void buscarEncuesta(UseCaseContext context){
		Date fechaInicio= null;
		Date fechaFin = null; 
		try{
			if (fechaInicioBusqueda != null)
				fechaInicio= DateUtils.getDateFromString(fechaInicioBusqueda,SisopAdminConstants.DATE_FORMAT_PATTERN);
			if (fechaFinBusqueda != null)
				fechaFin=DateUtils.getDateFromString(fechaFinBusqueda,SisopAdminConstants.DATE_FORMAT_PATTERN);
		}catch(Exception e){}
		encuestas = PlantillaEncuesta.findUs(nombrePlantillaBusqueda,fechaInicio,fechaFin);
		if(encuestas== null || encuestas.isEmpty())
			context.addErrorMessage("No existen encuestas con los parámetros seleccionados");
		
	}
	
	public void cargarEncuesta(UseCaseContext context){
		context.goToChildUseCase(ABMEncuestaUseCase.class);
	}
	
	public void cancelar(UseCaseContext context){
		context.acceptUseCase();
	}
	
	/**GETTERS - SETTERS*/
	
	public String getFechaFinBusqueda() {
		return fechaFinBusqueda;
	}
	public void setFechaFinBusqueda(String fechaFinBusqueda) {
		this.fechaFinBusqueda = fechaFinBusqueda;
	}
	public String getFechaInicioBusqueda() {
		return fechaInicioBusqueda;
	}
	public void setFechaInicioBusqueda(String fechaInicioBusqueda) {
		this.fechaInicioBusqueda = fechaInicioBusqueda;
	}
	public String getNombrePlantillaBusqueda() {
		return nombrePlantillaBusqueda;
	}
	public void setNombrePlantillaBusqueda(String nombrePlantillaBusqueda) {
		this.nombrePlantillaBusqueda = nombrePlantillaBusqueda;
	}


	public Collection getEncuestas() {
		return encuestas;
	}


	public void setEncuestas(Collection encuestas) {
		this.encuestas = encuestas;
	}
	
	public String getIndex() {
		return index;
	}


	public void setIndex(String index) {
		this.index = index;
	}
}