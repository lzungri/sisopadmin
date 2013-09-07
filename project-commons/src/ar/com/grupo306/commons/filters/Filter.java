package ar.com.grupo306.commons.filters;


/**
 * Define los servicios de un filtro de b�squeda.
 *
 * @author Leandro
 */
public interface Filter {
	
	/**
	 * @author Rafa
	 * @param object - el execute deber�a estar definido en cada filtro...
	 * @return
	 */
	public abstract Object execute(Object object);
	
	
}
