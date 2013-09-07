package ar.com.grupo306.sisopadmin.domain.validation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;

import ar.com.grupo306.commons.exceptions.core.ProgramException;
import ar.com.grupo306.commons.exceptions.core.ValidationException;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.configuration.DomainConfiguration;
import ar.com.grupo306.sisopadmin.domain.configuration.TagElement;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.validation.validadores.NullsValidator;
import ar.com.grupo306.sisopadmin.domain.validation.validadores.Validable;

/**
 * 
 * @author Rafa
 *
 */

public class ValidationManager {
	public static String ALTA_RESERVA = "altaReserva";
	private static Map mapValidadoresDefinidos;
	
	/**
	 * ejecuta los validadores asociados al objeto del modelo
	 * @param object
	 * @param method
	 */
	
	
	public static void validate(ModelObject object , String method){
		Map validadores;
		try{
			Document doc = DomainConfiguration.getValidadoresForObjectAndMethod(object , method);
			validadores = getMapValidadores(doc,method);
		}catch(Exception e){
			throw new ProgramException("Error en la obtención de la configuracion de los validadores");
		}
		execute(validadores,object);		
		return ;
	}
	/**
	 * recorre los validadores del objeto
	 * TODO - validar los objetos hijos(que componen a ese objeto)
	 * @param map
	 * @param objetoModelo
	 */
	private static void execute(Map map,ModelObject objetoModelo){
		
		if (map.get(objetoModelo.toString()) == null) return ;
		Collection validadores = (Collection) map.get(objetoModelo.toString());
		Iterator validadoresIterator = validadores.iterator();
		while(validadoresIterator.hasNext()){
			Validable validador = (Validable) validadoresIterator.next();
			if(!validador.validate(objetoModelo))
				throw new ValidationException("Error al validar el objeto" + objetoModelo.toString() + " en el validador" + validador.toString());
		}
		//validarComposicion(map , objetoModelo.getChildren());
		
	}
	
	private static Map getMapValidadores(Document doc , String method ){
		Element element = doc.getRootElement();
		HashMap map = (HashMap) CollectionFactory.createMap();
		populateMapValidadores( method,element , map);
		return map;   
	}
	
	private static boolean populateMapValidadores(String method , Element element , Map map){
		Iterator tagsIterator = element.getChildren().iterator();
		TagElement tag= new TagElement();
	    while(tagsIterator.hasNext() ){
	    	tag=TagElement.getInstance((Element)tagsIterator.next());
	    	if(!tag.checkMethod(method)) break;
	    	tag.populateValidadores(method ,map);	
		    populateMapValidadores( method ,  tag.getElement() ,  map);
	   	    	}
	    return true;
	}
	
	/**
	 * Se definen los validadores de la aplicacion
	 * @return
	 */
	
	
	public static Map getMapValidadoresDefinidos(){
		if (mapValidadoresDefinidos != null) return mapValidadoresDefinidos;
		Map map = CollectionFactory.createMap();
		
		
		
		map.put(ValidationIdConstants.NULLSVALIDATOR,				new NullsValidator());
		
		
		
		
		return map;
	}
	
	
}
