package ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.estrategiasABM;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PuntoAEncuestar;
import ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.ABMEncuestaUseCaseModel;
/**
 * Popula la encuesta con la Plantilla Encuesta enviada desde la administración.
 * @author Rafa
 *
 */
public class EstrategiaProcesamientoPlantillaEncuesta extends EstrategiaProcesamientoModelObject implements EstrategiaProcesamiento{

	public void procesarModelObject(ABMContext context) {
		ABMEncuestaUseCaseModel useCaseModel = (ABMEncuestaUseCaseModel)context.getModel();
		
		String AYUDANTE = "AYUDANTE";
		String GRUPO = "GRUPO";
		PlantillaEncuesta encuesta = (PlantillaEncuesta)context.getModelObject();
		useCaseModel.setFechaFin(encuesta.getFechaFin());
		useCaseModel.setFechaInicio(encuesta.getFechaAlta());
		//Que feo esto... 
		useCaseModel.setDestinatario(AYUDANTE.equalsIgnoreCase(encuesta.getDestinatario()) ? AYUDANTE:GRUPO);
		if(AYUDANTE.equalsIgnoreCase(useCaseModel.getDestinatario())){
			ArrayList destinatarios = new ArrayList();
			useCaseModel.setDestinatarios(destinatarios);
			useCaseModel.getDestinatarios().add(AYUDANTE);
			useCaseModel.getDestinatarios().add(GRUPO);
			
		}else{
			ArrayList destinatarios = new ArrayList();
			useCaseModel.setDestinatarios(destinatarios);
			useCaseModel.getDestinatarios().add(GRUPO);
			useCaseModel.getDestinatarios().add(AYUDANTE);
			
		}
		
		encuesta.update();
		Collection puntosAEncuestar = encuesta.getPuntosAEncuestar();
		Iterator iPuntosAEncuestar = puntosAEncuestar.iterator();
		while(iPuntosAEncuestar.hasNext()){
			PuntoAEncuestar puntoAEncuestar = (PuntoAEncuestar)iPuntosAEncuestar.next();
			useCaseModel.getPuntaje()[puntoAEncuestar.getNumero().intValue()] = "1";
		}
		Collection puntosOrdenados = new TreeSet(new NumeroPuntoComparator()); 
		puntosOrdenados.addAll(encuesta.getPuntosAEncuestar());
		useCaseModel.setPuntos(puntosOrdenados);

		
		useCaseModel.setNombrePlantilla(encuesta.getNombre());
		try{	
			useCaseModel.setObligatoria(new Long(1).equals(encuesta.getObligatoriedad())?true:false);}
		catch(Exception e){
			//Obligatoriedad no implementada")
		}
		useCaseModel.setEncuesta(encuesta);
		
	}
	
	private static class NumeroPuntoComparator implements Comparator{

		public int compare(Object o1, Object o2) {
			PuntoAEncuestar punto = (PuntoAEncuestar)o1;
			PuntoAEncuestar punto2 = (PuntoAEncuestar)o2;
			return punto.getNumero().compareTo(punto2.getNumero());
		}
		
	}

}
