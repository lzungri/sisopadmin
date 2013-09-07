package ar.com.grupo306.commons.filters;


/**
 * Define los servicios de un filtro de búsqueda.
 *
 * @author Leandro
 */
public interface Filter {
	
	/**
	 * @author Rafa
	 * @param object - el execute debería estar definido en cada filtro...
	 * @return
	 */
	public abstract Object execute(Object object);
	
	
}
