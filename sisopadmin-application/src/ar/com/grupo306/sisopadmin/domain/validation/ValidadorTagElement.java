package ar.com.grupo306.sisopadmin.domain.validation;

import java.util.Collection;
import java.util.Map;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.configuration.TagElement;
import ar.com.grupo306.sisopadmin.domain.validation.validadores.Validable;

/**
 * 
 * @author Rafa
 *
 */

public class ValidadorTagElement extends TagElement {

	public void populateValidadores(String method , Map map){
		
		String classString 		  = this.getAttributeValue(TagValidatorsIdConstants.CLASS);
		String validadorString    = this.getAttributeValue(TagValidatorsIdConstants.VALIDADOR);
		Map validadoresDefinidos = ValidationManager.getMapValidadoresDefinidos();
		Collection validadores = addValidador(map.get(classString),(Validable)validadoresDefinidos.get(validadorString));
		map.put(classString,validadores);
		
	}
	
	public Collection addValidador(Object object, Validable validador){
		Collection coleccionValidadores;
		if(object == null) coleccionValidadores = CollectionFactory.createCollection();
		else coleccionValidadores = (Collection)object;
		coleccionValidadores.add(validador); 
		return coleccionValidadores;
		
	}
	
	
	
}
