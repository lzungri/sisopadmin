package ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.estrategiasABM;

public class EstrategiaProcesamientoFactory {
	
	static EstrategiaProcesamientoFactory factory = null;
	
	public static EstrategiaProcesamientoFactory getInstance(){
		if(factory == null)
			factory = new EstrategiaProcesamientoFactory();
		return factory;
	}
	/**
	 * Proporciona distintas estrategias para que pueda popular cualquier tipo de objeto en el modelo. 
	 * Hasta ahora el comportamiento es el mismo para todas.
	 * @param operacion
	 * @return
	 */
	
	public EstrategiaProcesamientoModelObject getEstrategy(String operacion){
		String OPERACION_LLENAR = "LLENAR";
		String OPERACION_VISUALIZAR_RESULTADO = "VISUALIZAR_RESULTADO";
		if(OPERACION_LLENAR.equalsIgnoreCase(operacion))
			return new EstrategiaProcesamientoPlantillaEncuesta();
		if(OPERACION_VISUALIZAR_RESULTADO.equalsIgnoreCase(operacion))
			return new EstrategiaResultadoPlantillaEncuesta();
		return new EstrategiaProcesamientoPlantillaEncuesta();
	}
	
}
