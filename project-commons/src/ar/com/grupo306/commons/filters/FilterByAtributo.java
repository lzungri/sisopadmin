package ar.com.grupo306.commons.filters;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.log4j.Logger;



/**
 * 
 * @author Rafa
 *
 */

public class FilterByAtributo implements Filter{
	
	private static final Logger logger = Logger.getLogger(FilterByAtributo.class);
	private HashMap atributos;
	
	/**
	 * @param map
	 *   El par ordenado es (nombre de atributo del objeto, valor del atributo a filtrar)
	 */
	
	public FilterByAtributo(HashMap map){
		atributos = map;
	}
	
	/**
	 * 
	 * @param object - actualmente definido como una lista de objetos a filtrar x las condiciones definidas en el map
	 * TODO - se prevee en un futuro poder recibir otro tipo de objetos y modificar la firma	
	 * @return
	 */
	
	public Object execute(Object object) {
		List lista = (List)object;
		CollectionUtils.filter(lista,new FiltraPorAtributosPredicate(atributos));
		return lista;
	}

	public HashMap getAtributos() {
		return atributos;
	}

	public void setAtributos(HashMap atributos) {
		this.atributos = atributos;
	}
	/**
	 * 
	 * @author Rafa
	 * Predicado para evaluar mediante JXPath cualquier atributo declarado en el hashmap
	 * y que contenga un objeto
	 *
	 */
	
	static class FiltraPorAtributosPredicate implements Predicate{
		HashMap map ;
		
		FiltraPorAtributosPredicate(HashMap map){
			this.map = map;
		}

			
		
		public boolean evaluate(Object objeto) {
			Iterator iteratorAtributos = map.keySet().iterator();
			while (iteratorAtributos.hasNext()){
				Object atributo = iteratorAtributos.next();
				try{
					Object atributoValor = JXPathContext.newContext(objeto).getValue((String) atributo, Object.class);
					Object valorAIgualar = map.get(atributo);
					return atributoValor.equals(valorAIgualar);
				}catch(Exception e){
					logger.debug("No existe el atributo" + atributo.toString() + "en el objeto" + objeto.toString());
				}	
					
			}		
				return false;
		}
	}
}
