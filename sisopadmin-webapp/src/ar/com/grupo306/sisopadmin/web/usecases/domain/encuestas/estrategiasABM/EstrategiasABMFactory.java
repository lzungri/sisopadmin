package ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.estrategiasABM;


public class EstrategiasABMFactory {
	
	static String ALTA = "ALTA";
	static String ELIMINAR = "ELIMINAR";
	static String MODIFICAR = "MODIFICAR";
	static String LLENAR = "LLENAR";
	static EstrategiasABMFactory factory;
	
	/**
	 * Singleton del factory
	 */
	
	public static EstrategiasABMFactory getABMEncuestasInstance(){
		if(factory == null)
			factory = new EstrategiasABMFactory();
		return factory;
			
	}
	
	public EstrategiaAceptacionABM getEstrategiaEncuesta(Object operacion){
		if(operacion == null)
			return new EstrategiaAceptacionABM();
		if(ALTA.equalsIgnoreCase(operacion.toString()))
			return new EstrategiaGeneracionEncuesta();
		if(ELIMINAR.equalsIgnoreCase(operacion.toString()))
			return new EstrategiaEliminacionEncuesta();
		if(MODIFICAR.equalsIgnoreCase(operacion.toString()))
			return new EstrategiaModificacionEncuesta();
		if(LLENAR.equalsIgnoreCase(operacion.toString()))
			return new EstrategiaLlenadoEncuesta();
		
		return new EstrategiaAceptacionABM();
	}
}
